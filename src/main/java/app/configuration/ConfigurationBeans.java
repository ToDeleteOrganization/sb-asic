package app.configuration;

import arduino.ArduinoManager;
import arduino.board.ArduinoBoard;
import arduino.board.ArduinoBoardCreator;
import arduino.manager.BoardService;
import arduino.manager.impl.DefaultArduinoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
public class ConfigurationBeans {

    public static final String NO_PORT_NAME = "0";

    private static final String PORT_NAME = "COM3";

    @Bean
//    @Profile("!dev")
    public BoardService createArduinoBoard() {
        final ArduinoBoard boardForPort = ArduinoBoardCreator.getBoardForPort(NO_PORT_NAME);
        return new DefaultArduinoService(boardForPort, new ArduinoManager());
    }

//    @Bean
//    @Profile("dev")
//    public BoardService createMockArduinoBoard() {
//        final ArduinoBoard boardForPort = ArduinoBoardCreator.getBoardForPort(PORT_NAME);
//        return new DefaultArduinoService(boardForPort, new ArduinoManager());
//    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(65536); // increase binary buffer
        return container;
    }
}
