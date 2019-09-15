package br.com.example.redisspring.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis.client-config")
public class RedisClientConfigProperties {
    private String clientName;
    private Integer commandTimeout;
    private Integer shutdownTimeout;

    public Duration getCommandTimeout(){
        return Duration.ofSeconds(this.commandTimeout);
    }

    public Duration getShutdownTimeout(){
        return Duration.ofSeconds(this.shutdownTimeout);
    }
}
