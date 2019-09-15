package br.com.example.redisspring.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@Data
@EnableConfigurationProperties({RedisClientConfigProperties.class, RedisClientOptionsProperties.class, PoolProperties.class})
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisGeneralProperties {
    private String host;
    private Integer port;
    private String password;
    private boolean enabled;
    private RedisClientOptionsProperties clientOptions;
    private RedisClientConfigProperties clientConfig;
    private PoolProperties poolConfig;

    @PostConstruct
    public void init(){
        System.out.println("Teste Generalllllllllllllllllllllllllllllllll");
        System.out.println(poolConfig);
        System.out.println(clientConfig);
    }
}
