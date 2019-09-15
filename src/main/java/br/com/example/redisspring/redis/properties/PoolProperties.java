package br.com.example.redisspring.redis.properties;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis.pool-config")
public class PoolProperties {
   private Integer maxIdle;
   private Integer maxTotal;
   private Integer minIdle;

   public GenericObjectPoolConfig getGenericObjectPoolConfig(){
      GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
      poolConfig.setMinIdle(minIdle);
      poolConfig.setMaxTotal(maxTotal);
      poolConfig.setMaxIdle(maxIdle);
      return poolConfig;
   }

}
