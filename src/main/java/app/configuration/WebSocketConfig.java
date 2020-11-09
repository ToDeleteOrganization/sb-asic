package app.configuration;

import app.websocket.handler.CameraPictureHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket

@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/camera");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
//        .setHandshakeHandler(null)  TODO: try this, and customize
    }

    // Board handlers
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // TODO: add proper 'origin' value
        webSocketHandlerRegistry.addHandler(this.applicationContext.getBean(CameraPictureHandler.class), "/board/camera").setAllowedOrigins("*");
//        webSocketHandlerRegistry.addHandler(this.applicationContext.getBean(BoardHandler.class), "/board/command").setAllowedOrigins("*");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}