package com.baifc.organizationservice.events.source;

import com.baifc.organizationservice.events.model.OrganizationChangeModel;
import com.baifc.organizationservice.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.organizationservice.events.source
 * Created: 2019/9/3.
 * Auther: baifc
 * Description:
 */
@Component
@Slf4j
public class SimpleSourceBean {

    /**
     * spring cloud stream提供注入一个Source接口
     */
    @Autowired
    private Source source;

    public void publishOrgChange(String action, String orgId) {
        log.debug("Sending Kafka message {} for Organization Id: {}", action, orgId);

        // 要发布的消息是一个java pojo结构体
        OrganizationChangeModel model = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                UserContext.CORRELATION_ID
        );

        // 当要发布消息时，使用source类中定义的通道的send方法
        source.output().send(MessageBuilder.withPayload(model).build());
    }

}
