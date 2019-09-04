package com.baifc.licenseservice.repository.redis;

import com.baifc.licenseservice.model.Organization;
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

    public static final String HASH_NAME = "organization";

    @Autowired
    private RedisTemplate redisTemplate;

    private HashOperations hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations =  redisTemplate.opsForHash();
    }

    public void saveOrganization(Organization organization) {
        hashOperations.put(HASH_NAME, organization.getId(), organization);
    }

    public void deleteOrganization(String organizationId) {
        hashOperations.delete(HASH_NAME, organizationId);
    }

    public Organization findOrganization(String orgId) {
        return (Organization) hashOperations.get(HASH_NAME, orgId);
    }


}
