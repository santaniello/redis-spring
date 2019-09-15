package br.com.example.redisspring.redis.configs;


import br.com.example.redisspring.redis.RedisCacheErrorHandler;
import br.com.example.redisspring.redis.properties.RedisClientOptionsProperties;
import br.com.example.redisspring.redis.properties.RedisGeneralProperties;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@ConditionalOnProperty(name = "spring.cache.redis.enabled", havingValue = "true")
@EnableConfigurationProperties(RedisGeneralProperties.class)
@EnableCaching
public class RedisGeneralConfig extends CachingConfigurerSupport implements CachingConfigurer {

   @Autowired
   private RedisGeneralProperties redisConfigProperties;

    @Bean(destroyMethod = "shutdown")
    @Qualifier("redisResources")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public ClientOptions clientOptions(){
        RedisClientOptionsProperties clientOptions = redisConfigProperties.getClientOptions();
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .pingBeforeActivateConnection(clientOptions.isPingBeforeActivateConnection())
                .cancelCommandsOnReconnectFailure(clientOptions.isCancelCommandsOnReconnectFailure())
                .autoReconnect(clientOptions.isAutoReconnect())
                .suspendReconnectOnProtocolFailure(clientOptions.isSuspendReconnectOnProtocolFailure())
                .build();
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisConfigProperties.getHost(), redisConfigProperties.getPort());
        redisStandaloneConfiguration.setPassword(redisConfigProperties.getPassword());
        return redisStandaloneConfiguration;
    }

    @Bean
    public LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, @Qualifier("redisResources") ClientResources dcr){
        GenericObjectPoolConfig poolConfig = redisConfigProperties.getPoolConfig().getGenericObjectPoolConfig();
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .clientOptions(options)
                .clientResources(dcr)
                .build();
    }

    @Bean
    public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
                                                    LettucePoolingClientConfiguration lettucePoolConfig) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }
}