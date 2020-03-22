package app.configuration;

import app.redis.StringByteArrayCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;

@Configuration
public class RedisConfigurationBeans {

    @Autowired
    private RedisConfigurationDetails configurationDetails;

    @Bean(name = "syncRedisCommand")
    public RedisCommands<String, ByteArrayOutputStream> createSyncRedisCommand() {
        final StatefulRedisConnection<String, ByteArrayOutputStream> redisConnection = createRedisConnection();
        return redisConnection.sync();
    }

    @Bean(name = "asyncRedisCommand")
    public RedisAsyncCommands<String, ByteArrayOutputStream> createAsyncRedisCommand() {
        final StatefulRedisConnection<String, ByteArrayOutputStream> redisConnection = createRedisConnection();
        return redisConnection.async();
    }

    @Bean
    public StatefulRedisConnection<String, ByteArrayOutputStream> createRedisConnection() {
        final StringByteArrayCodec codec = StringByteArrayCodec.getCodecInstance();
        return RedisClient.create().connect(codec, createRedisURI());
    }

    private RedisURI createRedisURI() {
        final RedisURI.Builder builder = createBasicRedisBuilder();
        if (StringUtils.isNotBlank(configurationDetails.getPassword())) {
            builder.withPassword(configurationDetails.getPassword());
        }
        if (StringUtils.isNotBlank(configurationDetails.getUsername())) {
            builder.withClientName(configurationDetails.getUsername());
        }
        return builder.build();
    }

    private RedisURI.Builder createBasicRedisBuilder() {
        final String redisServerHost = configurationDetails.getRedisServerHost();
        final Integer redisServerPort = configurationDetails.getRedisServerPort();

        if ((redisServerHost == null) || (redisServerPort == null)) {
            throw new IllegalStateException("Please provide redis server/port");
        }
        return RedisURI.Builder
                .redis(redisServerHost, redisServerPort)
                .withDatabase(1);
    }
}
