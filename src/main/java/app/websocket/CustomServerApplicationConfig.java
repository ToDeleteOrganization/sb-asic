package app.websocket;

import org.springframework.context.annotation.Configuration;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;
import java.util.stream.Collectors;


//@Configuration
@Deprecated // pana ce adaugam fara spring
public class CustomServerApplicationConfig implements ServerApplicationConfig {

    public CustomServerApplicationConfig() {
        System.out.println("custom server  app");
    }

    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
        return set.stream()
                .map(clz -> ServerEndpointConfig.Builder.create(clz, "/prog").build())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
        return set;
    }
}
