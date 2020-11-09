package app.websocket;

import app.board.mediator.BoardMediator;
import app.data.CommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private BoardMediator boardMediator;

    // only when subscribes
    @MessageMapping("/camera/video")
    public void hello(CommandMessage message) {
        boardMediator.sendCommandToBoard(message.getBoardId(), message.getCommand());
    }

}
