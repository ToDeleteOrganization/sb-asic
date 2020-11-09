package app.board.request;

import app.board.Board;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

// TODO: merge this with ArduinoMessage
public class MessageFromBoard<T> {

    private BoardRequestType boardRequestType;

    private final Board board;

    private T message;

    public MessageFromBoard(final Board board, final T entity) {
        this.board = board;
        this.message = entity;
    }

    public Board getBoard() {
        return board;
    }

    public T getMessage() {
        return message;
    }

    public BoardRequestType getBoardRequestType() {
        return boardRequestType;
    }

    public void setBoardRequestType(BoardRequestType boardRequestType) {
        this.boardRequestType = boardRequestType;
    }
}
