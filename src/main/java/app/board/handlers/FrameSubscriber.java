package app.board.handlers;

public interface FrameSubscriber {

    /**
     *
     * @param frame
     */
    HandlerStatus handleFrame(byte[] frame);
}
