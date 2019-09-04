package com.baifc.licenseservice.events.handler;

import com.alibaba.fastjson.JSONObject;
import com.baifc.licenseservice.events.model.OrganizationChangeModel;
import com.baifc.licenseservice.repository.redis.OrganizationRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.events.handler
 * Created: 2019/9/3.
 * Auther: baifc
 * Description:
 */
@Slf4j
@EnableBinding(Sink.class)
public class OrgChangeHandler {

    @Autowired
    private OrganizationRedisRepository organizationRedisRepository;

    @StreamListener(Sink.INPUT)
    public void logSink(OrganizationChangeModel model) {
        log.debug("model = " + JSONObject.toJSONString(model));

        organizationRedisRepository.deleteOrganization(model.getOrganizationId());
        log.warn("组织[" + model.getOrganizationId() + "]已变更，删除缓存中的信息");
    }

}
