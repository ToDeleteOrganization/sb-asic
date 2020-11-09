package app.board.request;

import app.board.Board;

public class MessageToBoard {

    private final Board board;

    private final String message;

    public MessageToBoard(Board board, String message) {
        this.board = board;
        this.message = message;
    }

    public Board getBoard() {
        return board;
    }

    public String getMessage() {
        return message;
    }
}
