package app.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface RedisService {

    Future<String> save(String key, byte[] data);

    Future<ByteArrayOutputStream> retrieve(String key) throws ExecutionException, InterruptedException;

    Future<List<String>> findAllKeys() throws ExecutionException, InterruptedException;

}
