package app.service.impl;

import app.service.RedisService;
import app.util.BaosUtils;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class BasicAsyncRedisService implements RedisService {

    @Autowired
    private RedisAsyncCommands<String, ByteArrayOutputStream> redisAsyncCommands;

    @Override
    public Future<String> save(String key, byte[] data) {
        return redisAsyncCommands.set(key, BaosUtils.toBaos(data));
    }

    @Override
    public Future<ByteArrayOutputStream> retrieve(String key) throws ExecutionException, InterruptedException {
        return redisAsyncCommands.get(key);
    }

    @Override
    public Future<List<String>> findAllKeys() throws ExecutionException, InterruptedException {
        // TODO: use scan with a strategy
        return redisAsyncCommands.keys("*");
    }

}
