package com.fuzamei.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lingjun.jlj
 * @create 2017-11-28
 **/
@Configuration
@PropertySource(value = "classpath:redis/redis.properties")
@EnableCaching // 启用缓存，这个注解很重要
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.timeout}")
    private long maxWaitTimeout;

    @Value("${spring.redis.pool.max-active}")
    private int maxTotal;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setFairness(false);
        jedisPoolConfig.setMaxWaitMillis(maxWaitTimeout);
        jedisPoolConfig.setMaxTotal(maxTotal);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(){
        return new JedisPool(jedisPoolConfig(), host, port, (int)maxWaitTimeout, password, database);
    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder
                = builder.usePooling().poolConfig(jedisPoolConfig());
        JedisClientConfiguration jedisClientConfiguration = jedisPoolingClientConfigurationBuilder.build();

        return jedisClientConfiguration;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory(JedisClientConfiguration jedisClientConfiguration) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setPort(port);
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration);
        return factory;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        // 对于普通K-V操作时，key采取的序列化策略
        template.setKeySerializer(new StringRedisSerializer());
        // value采取的序列化策略
        template.setValueSerializer(serializer);
        // 在hash数据结构中，hash-key的序列化策略
        template.setHashKeySerializer(serializer);
        // 在hash数据结构中，hash-key的序列化策略
        template.setHashValueSerializer(serializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }
}
