package app.websocket.handler;

import app.board.Board;
import app.board.BoardSubscribersHolder;
import app.board.mediator.BoardMediator;
import app.board.request.MessageFromBoard;
import app.board.request.BoardRequestType;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

// /board/camera
@Component
public class CameraPictureHandler extends AbstractWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CameraPictureHandler.class);

    @Autowired
    private BoardSubscribersHolder boardSubscribersHolder;

    @Autowired
    private BoardMediator boardMediator;

    // TODO: convert in spring - event based
    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        LOG.info("Connection established for " + findBoardId(session));
        boardSubscribersHolder.addBoard(findBoardId(session), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        final String boardId = findBoardId(session);
        LOG.info(String.format("Board %s has disconnected, unregistering it.", boardId));
        boardSubscribersHolder.removeBoard(findBoardId(session));
    }

    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Picture from %s, load = %s ", findBoardId(session), message.getPayloadLength()));
        }

        final String boardId = findBoardId(session);
        final Optional<Board> boardOpt = boardSubscribersHolder.getBoardById(boardId);
        if (boardOpt.isPresent()) {
            final MessageFromBoard<ByteBuffer> messageFromBoard = new MessageFromBoard<>(boardOpt.get(), message.getPayload());
            messageFromBoard.setBoardRequestType(BoardRequestType.MESSAGE);
            boardMediator.receiveFrame(messageFromBoard);

        } else {
            LOG.warn(String.format("Received message from no-existing board %s"), boardId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        LOG.info(String.format("Received text from %s, content = %s ", findBoardId(session), message.getPayload()));
    }

    private String findBoardId(final WebSocketSession session) {
        final List<String> idHeaderValues = session.getHandshakeHeaders().get("boardid");
        return CollectionUtils.isNotEmpty(idHeaderValues)
                ? idHeaderValues.get(0)
                : String.format("n/a_%s_%s", boardSubscribersHolder.getBoardCount(), System.currentTimeMillis());
    }
}
