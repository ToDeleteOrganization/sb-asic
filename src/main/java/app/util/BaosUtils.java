package app.util;

import java.io.ByteArrayOutputStream;

public final class BaosUtils {

    public static ByteArrayOutputStream toBaos(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Please provide some bytes");
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);
        baos.writeBytes(data);
        return baos;
    }

    private BaosUtils() {}
}
