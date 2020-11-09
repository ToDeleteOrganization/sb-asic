package app.websocket;


import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// TODO: add this without spring
@Deprecated // pana adaugam fara spring
@ServerEndpoint(value = "/fa")
public class CommandEndpoint extends Endpoint {

    public CommandEndpoint() {
        System.out.println("CommandEndpoint instance");
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println(session.getId());
    }

    //    @Override
    protected void onOpenSocket() {
//        System.out.println("Opened socket for: " + getSession().getId());
    }

//    @Override
    protected void onCloseSocket() {
//        System.out.println("Clsoed socket for: " + getSession().getId());
    }
}
