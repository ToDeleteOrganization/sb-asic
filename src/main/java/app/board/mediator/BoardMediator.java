package app.board.mediator;

import app.board.request.MessageFromBoard;
import app.board.request.MessageToBoard;

import java.nio.ByteBuffer;

public interface BoardMediator {

    void receiveFrame(MessageFromBoard<ByteBuffer> messageFromBoard);

    void sendMessageToBoard(MessageToBoard mtb);

    void sendCommandToBoard(final String boardId, final String command);


    void handleUnsubscriptionEvent(String boardId);

    void handleSubscriptionEvent(String boardId);
}
