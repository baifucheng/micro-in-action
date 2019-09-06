package com.baifc.licenseservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.config
 * Created: 2019/9/3.
 * Auther: baifc
 * Description:
 */
@Configuration
public class RedisConfig {

    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        // 这里需要使用定义序列化，默认的序列化存储到redis中，并不是原来指定的kv
        GenericJackson2JsonRedisSerializer Jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        redisTemplate.setDefaultSerializer(Jackson2JsonRedisSerializer);

        return redisTemplate;
    }
}
