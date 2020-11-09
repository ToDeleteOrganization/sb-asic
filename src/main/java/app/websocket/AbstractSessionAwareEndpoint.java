package app.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

// TODO: add this without spring
@Deprecated // pana adaugam fara spring
@ServerEndpoint(value = "/ws")
public class AbstractSessionAwareEndpoint {

    private Session session;

    public AbstractSessionAwareEndpoint() {
        System.out.println("AbstractSessionAwareEndpoint instance");
    }

    @OnOpen
    public void onOpen(final Session session, final EndpointConfig var2) {
        this.session = session;
        // add to general webSocket session container
        onOpenSocket();
    }

    @OnMessage
    public String onMessage(final String message, Session session) {
        System.out.println("received message: " + message);
        return "received [" + message + "]";
    }

    @OnClose
    public void onClose(final Session session, final CloseReason closeReason) {
        // remove from webSocket session container
        onCloseSocket();
    }

    protected void onOpenSocket() {}

    protected void onCloseSocket() {}

    protected Session getSession() {
        return session;
    }
}

