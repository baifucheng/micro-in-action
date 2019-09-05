package com.baifc.organizationservice.events.source;

import com.baifc.organizationservice.events.model.OrganizationChangeModel;
import com.baifc.organizationservice.utils.UserContext;
import com.baifc.organizationservice.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
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
// 告诉spring cloud stream将应用程序绑定到消息代理，该服务将通过source类定义的一组消息通道和消息代理进行通信
@EnableBinding(Source.class)
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
                UserContextHolder.getUserContext().getCorrelationId()
        );

        // 当要发布消息时，使用source类中定义的通道的send方法
        Message<OrganizationChangeModel> message = MessageBuilder.withPayload(model).build();
        source.output().send(message);
    }

}
