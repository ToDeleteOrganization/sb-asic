package app.board.handlers;

import org.springframework.stereotype.Component;

@Component("savePictureSubscriber")
public class SavePictureSubscriber implements FrameSubscriber {

    @Override
    public HandlerStatus handleFrame(byte[] frame) {
        return HandlerStatus.UNSUBSCRIBE;
    }
}
