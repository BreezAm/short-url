package com.breez.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        /**
         * 将Redis 键（key）序列化为字符串
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /**
         * 将Redis 值（value）序列化为json格式
         */
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        /**
         * 将Redis Hash 键序列化为字符串格式
         */
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        /**
         * 键Redis Hash 值序列化为json格式
         */
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        /**
         * 设置Redis的连接工厂为lettuce
         */
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
