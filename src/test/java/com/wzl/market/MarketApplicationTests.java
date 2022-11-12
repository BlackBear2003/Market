package com.wzl.market;

import com.wzl.market.pojo.Cart;
import com.wzl.market.utils.FastJsonRedisSerializer;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@SpringBootTest
class MarketApplicationTests {
@Autowired
RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        System.out.println(new Date());



    }

}
