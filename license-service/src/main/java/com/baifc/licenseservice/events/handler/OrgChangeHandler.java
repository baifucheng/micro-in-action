package com.baifc.licenseservice.events.handler;

import com.alibaba.fastjson.JSONObject;
import com.baifc.licenseservice.events.model.OrganizationChangeModel;
import lombok.extern.slf4j.Slf4j;
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

    @StreamListener(Sink.INPUT)
    public void logSink(OrganizationChangeModel model) {
        log.debug("model = " + JSONObject.toJSONString(model));
    }

}
