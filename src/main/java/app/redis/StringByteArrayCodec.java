package app.redis;

import app.util.BaosUtils;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class StringByteArrayCodec implements RedisCodec<String, ByteArrayOutputStream> {

    private final ByteArrayCodec byteArrayCodec;

    public static StringByteArrayCodec getCodecInstance() {
        return new StringByteArrayCodec();
    }

    private StringByteArrayCodec() {
        byteArrayCodec = new ByteArrayCodec();
    }

    @Override
    public String decodeKey(final ByteBuffer byteBuffer) {
        return new String(byteArrayCodec.decodeKey(byteBuffer));
    }

    @Override
    public ByteArrayOutputStream decodeValue(final ByteBuffer byteBuffer) {
        byte[] bytes = byteArrayCodec.decodeValue(byteBuffer);
        return BaosUtils.toBaos(bytes);
    }

    @Override
    public ByteBuffer encodeKey(final String key) {
        return byteArrayCodec.encodeKey(key.getBytes());
    }

    @Override
    public ByteBuffer encodeValue(ByteArrayOutputStream baos) {
        return ByteBuffer.wrap(baos.toByteArray());
    }
}
