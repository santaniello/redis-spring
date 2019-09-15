package br.com.example.redisspring.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis.client-options")
public class RedisClientOptionsProperties {
    private boolean pingBeforeActivateConnection;
    private boolean cancelCommandsOnReconnectFailure;
    private boolean autoReconnect;
    private boolean suspendReconnectOnProtocolFailure;
}
