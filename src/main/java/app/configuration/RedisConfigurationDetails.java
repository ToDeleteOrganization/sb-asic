package app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/props/redis.properties")
public class RedisConfigurationDetails {

    @Value("${redis.server.host:localhost}")
    private String redisServerHost;

    @Value("${redis.server.port:6379}")
    private Integer redisServerPort;

    @Value("${redis.server.username:}")
    private String username;

    @Value("${redis.server.password:}")
    private String password;

    public String getRedisServerHost() {
        return redisServerHost;
    }

    public Integer getRedisServerPort() {
        return redisServerPort;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
