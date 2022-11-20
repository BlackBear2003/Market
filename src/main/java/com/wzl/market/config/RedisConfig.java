package com.wzl.market.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.util.AnnotationUtils;
import com.wzl.market.utils.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ReflectionUtils;

import java.time.Duration;
import java.util.*;

@Configuration
@EnableCaching
public class RedisConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }

    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        // 使用FastJsonRedisSerializer来序列化和反序列化redis的value值
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer(Object.class)))
                .entryTtl(Duration.ofSeconds(2626520))
                .disableCachingNullValues();
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("cartCache");
        return  RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).initialCacheNames(cacheNames).build();
    }



}
