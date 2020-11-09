package app.board.handlers;

import arduino.data.ArduinoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component("videoSubscriber")
public class VideoSubscriber implements FrameSubscriber {

    private static final String STOMP_DESTINATION = "/camera/video";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public HandlerStatus handleFrame(byte[] frame) {
        final ArduinoMessage<Object> boardMessage = new ArduinoMessage<>();
        boardMessage.setContent(frame);
        simpMessagingTemplate.convertAndSend(STOMP_DESTINATION, boardMessage);

        return HandlerStatus.NONE; // return unsubscribe when no '/camera/video' subscriber present
    }
}
