package app.websocket.listener;

import app.board.BoardSubscribersHolder;
import app.board.mediator.BoardMediator;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
public class WebSocketSessionApplicationListener {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketSessionApplicationListener.class);

    @Autowired
    private BoardSubscribersHolder boardSubscribersHolder;

    @Autowired
    private BoardMediator boardMediator;

    // TODO: refactor
    private String findBoardId(final Message<byte[]> message) {
        final Map<String, List<Object>> m = (Map)message.getHeaders().get("nativeHeaders");
        if (m == null) {
            return null;
        }
        final List<Object> boardId = m.get("boardId");
        return CollectionUtils.isNotEmpty(boardId) ? String.valueOf(boardId.get(0)) : null;
    }

    private String findUserName(final Message<byte[]> message) {
        // TODO: use session principal
        return String.valueOf(message.getHeaders().get("simpSessionId"));
    }

    private void treatEvent(final AbstractSubProtocolEvent event, final BiConsumer<String, String> biConsumer) {
        final Message<byte[]> message = event.getMessage();

        final String boardId = findBoardId(message);
        final String userName = findUserName(message);// sce.getUser();  TODO: add user authentification
        biConsumer.accept(boardId, userName);
    }

    //
    @Component
    class SessionSubscribe implements SmartApplicationListener {
        @Override
        public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
            return SessionSubscribeEvent.class.equals(aClass);
        }

        @Override
        public void onApplicationEvent(ApplicationEvent applicationEvent) {
            final SessionSubscribeEvent sce = (SessionSubscribeEvent)applicationEvent;

            final Message<byte[]> message = sce.getMessage();
            LOG.info(String.format("Subscription for board %s, user %s: ", findBoardId(message), findUserName(message)));

            WebSocketSessionApplicationListener.this.treatEvent(sce, (boardId, userName) -> {
                boardSubscribersHolder.addSubscriberToBoard(boardId, userName);
                boardMediator.handleSubscriptionEvent(boardId);
            });
        }
    }

    @Component
    class SessionUnsubscribe implements SmartApplicationListener {
        @Override
        public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
            return SessionUnsubscribeEvent.class.equals(aClass);
        }

        @Override
        public void onApplicationEvent(ApplicationEvent applicationEvent) {
            final SessionUnsubscribeEvent sce = (SessionUnsubscribeEvent)applicationEvent;

            final Message<byte[]> message = sce.getMessage();
            LOG.info(String.format("Unsubscribe from board %s, user %s: ", findBoardId(message), findUserName(message)));

            WebSocketSessionApplicationListener.this.treatEvent(sce, (boardId, userName) -> {
                if (boardSubscribersHolder.unsubscribeUserFromBoard(boardId, userName)) {
                    boardMediator.handleUnsubscriptionEvent(boardId);
                }
            });
        }
    }

    @Component
    class SessionDisconnect implements SmartApplicationListener {
        @Override
        public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
            return SessionDisconnectEvent.class.equals(aClass);
        }

        @Override
        public void onApplicationEvent(ApplicationEvent applicationEvent) {
            final SessionDisconnectEvent sce = (SessionDisconnectEvent)applicationEvent;

            final Message<byte[]> message = sce.getMessage();
            LOG.info(String.format("Disconnect user %s: ", findUserName(message)));

            WebSocketSessionApplicationListener.this.treatEvent(sce, (boardId, userName) -> {
                final List<String> unsubscribedFromBoards = boardSubscribersHolder.disconnectUserFromBoards(userName);
                CollectionUtils.emptyIfNull(unsubscribedFromBoards).forEach(unsubscribedFromBoardId -> {
                    boardMediator.handleUnsubscriptionEvent(unsubscribedFromBoardId);
                });
            });
        }
    }

}
