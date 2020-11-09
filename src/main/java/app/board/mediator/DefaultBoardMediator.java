package app.board.mediator;

import app.board.Board;
import app.board.BoardSubscribersHolder;
import app.board.command.BoardCommand;
import app.board.command.Command;
import app.board.command.CommandRepository;
import app.board.handlers.FrameSubscriber;
import app.board.handlers.HandlerStatus;
import app.board.request.MessageFromBoard;
import app.board.request.MessageToBoard;
import app.exception.BoardCommandException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Component
public class DefaultBoardMediator implements BoardMediator {

    @Resource(name = "registrationRegistry")
    private RegistrationRegistry registrationRegistry;

    @Autowired
    private BoardSubscribersHolder boardSubscribersHolder;

    @Autowired
    private CommandRepository commandRepository;

    @Override
    // TODO: refactor this, use frameSubscriber iterator
    public void receiveFrame(final MessageFromBoard<ByteBuffer> messageFromBoard) {
        Iterator<FrameSubscriber> iterator = registrationRegistry.getFrameSubscribersIterator();
        while (iterator.hasNext()) {
            final FrameSubscriber frameSubscriber = iterator.next();

            final ByteBuffer payload = messageFromBoard.getMessage();
            final HandlerStatus handlerStatus = frameSubscriber.handleFrame(payload.array());
            if (HandlerStatus.UNSUBSCRIBE.equals(handlerStatus)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void handleSubscriptionEvent(final String boardId) {
        // TODO: maybe unsubscribe the user from other cameras?, trebuie si in 'simpClient'
        final Collection<String> boardSubscribers = this.boardSubscribersHolder.getBoardSubscribers(boardId);
        if (CollectionUtils.isNotEmpty(boardSubscribers)) {
            handleEvent(boardId, "startStream");
        }
    }

    @Override
    public void handleUnsubscriptionEvent(final String boardId) {
        final Collection<String> boardSubscribers = this.boardSubscribersHolder.getBoardSubscribers(boardId);
        if (CollectionUtils.isEmpty(boardSubscribers)) {
            handleEvent(boardId, "stopStream");
        }
    }

    @Override
    public void sendCommandToBoard(final String boardId, final String command) {
        final Collection<String> boardSubscribers = this.boardSubscribersHolder.getBoardSubscribers(boardId);
        if (CollectionUtils.isNotEmpty(boardSubscribers)) {
            final Optional<Board> boardOpt = this.boardSubscribersHolder.getBoardById(boardId);
            final String startStream = this.commandRepository.transformCommand(command);
            this.sendMessageToBoard(new MessageToBoard(boardOpt.get(), startStream));
        }
    }

    @Override
    public void sendMessageToBoard(final MessageToBoard mtb) {
        try {
            final BoardCommand boardCommand = new BoardCommand(mtb.getBoard());
            boardCommand.setCommand(mtb.getMessage());
            executeCommand(boardCommand);
        } catch (BoardCommandException e) {
            e.printStackTrace();
        }
    }

    private void handleEvent(final String boardId, final String command) {
        final Optional<Board> boardOpt = this.boardSubscribersHolder.getBoardById(boardId);
        if (boardOpt.isPresent()) {
            final String startStream = this.commandRepository.transformCommand(command);
            this.sendMessageToBoard(new MessageToBoard(boardOpt.get(), startStream));
        }
    }

    private <T> void executeCommand(final Command commandObject) throws BoardCommandException {
        if (commandObject != null) {
            commandObject.executeCommand();
        }
    }

}
