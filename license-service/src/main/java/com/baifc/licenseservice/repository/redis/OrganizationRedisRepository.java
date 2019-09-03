package com.baifc.licenseservice.repository.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.repository.redis
 * Created: 2019/9/3.
 * Auther: baifc
 * Description:
 */
@Repository
public class OrganizationRedisRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    private HashOperations hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations =  redisTemplate.opsForHash();
    }


}
