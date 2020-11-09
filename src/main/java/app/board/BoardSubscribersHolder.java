package app.board;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
// TODO: treat multipel thread cases
public class BoardSubscribersHolder {

    private Map<Board, Collection<String>> boardSubscribers = MapUtils.synchronizedMap(new HashMap<>());

    public BoardSubscribersHolder() {
        addBoard("esp32_mock", null);
    }

    public void addBoard(String boardId, WebSocketSession session) {
        Optional<Board> boardById = getBoardById(boardId);
        if (boardById.isPresent()) {
            removeBoard(boardId);
        }
        final Board board = new Board();
        board.setBoardId(boardId);
        board.setWebSocketSession(session);
        this.boardSubscribers.put(board, new HashSet<>());
    }

    public boolean addSubscriberToBoard(final String boardId, final String user) {
        return treatSubscription(boardId, (board) -> this.boardSubscribers.get(board).add(user));
    }

    public boolean unsubscribeUserFromBoard(final String boardId, final String user) {
        return treatSubscription(boardId, (board) -> this.boardSubscribers.get(board).remove(user));
    }

    public List<String> disconnectUserFromBoards(final String user) {
        return this.boardSubscribers.entrySet().stream().map((entry) -> entry.getValue().remove(user) ? entry.getKey().getBoardId() : null)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    private boolean treatSubscription(final String boardId, Function<Board, Boolean> function) {
        boolean res = false;
        synchronized (boardSubscribers) {
            final Optional<Board> boardOpt = this.getBoardById(boardId);
            if (boardOpt.isPresent()) {
                res = function.apply(boardOpt.get());
            } else {
                //LOG.warn("no board found with ID");
            }
        }
        return res;
    }

    public boolean removeBoard(String boardId) {
        final Optional<Board> boardOpt = this.getBoardById(boardId);
        return boardOpt.isPresent() && (this.boardSubscribers.remove(boardOpt.get()) != null);
    }

    public Optional<Board> getBoardById(String boardId) {
        return this.getBoards().stream()
                .filter(brd -> StringUtils.equals(brd.getBoardId(), boardId))
                .findFirst();
    }

    public List<String> getBoardIds() {
        return this.getBoards().stream().map(Board::getBoardId).collect(Collectors.toList());
    }

    public Collection<String> getBoardSubscribers(final String boardId) {
        final Optional<Board> boardById = this.getBoardById(boardId);
        return boardById.isPresent() ? this.boardSubscribers.get(boardById.get()) : CollectionUtils.emptyCollection();
    }

    public int getBoardCount() {
        return this.getBoards().size();
    }

    public Collection<Board> getBoards() {
        return this.boardSubscribers.keySet();
    }
}

