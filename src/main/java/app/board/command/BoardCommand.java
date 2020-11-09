package app.board.command;

import app.board.Board;
import app.exception.BoardCommandException;

import javax.websocket.Session;
import java.io.IOException;

public class BoardCommand implements Command {

    private final Board board;
    private String boardCommand;

    public BoardCommand(Board board) {
        this.board = board;
    }

    public void executeCommand() throws BoardCommandException {
//        final WebSocketSession webSocketSession = board.getWebSocketSession();
//        webSocketSession.sendMessage(); TODO: try this
        try {
            final Session nativeSession = board.getNativeSession();
            if (nativeSession != null) {
                nativeSession.getBasicRemote().sendText(boardCommand);
            }
        } catch (IOException e) {
            final String errorMessage = String.format("Error sending command %s to board %s", boardCommand, board.getBoardId());
            throw new BoardCommandException(errorMessage, e);
        }
    }

    public void setCommand(String command) {
        this.boardCommand = command;
    }
}
