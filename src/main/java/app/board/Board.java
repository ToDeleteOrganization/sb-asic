package app.board;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.NativeWebSocketSession;

import javax.websocket.Session;

public class Board {

    private String boardId;

    private WebSocketSession webSocketSession;

    public Session getNativeSession() {
        Session nativeSession = null;
        if (webSocketSession instanceof NativeWebSocketSession) {
            final NativeWebSocketSession nativeBoardsession = (NativeWebSocketSession) webSocketSession;
            nativeSession = nativeBoardsession.getNativeSession(Session.class);
        }
        return nativeSession;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
