package com.baifc.licenseservice.service;

import com.baifc.licenseservice.client.OrganizationFeignClient;
import com.baifc.licenseservice.model.Organization;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.service
 * Created: 2019/8/22.
 * Auther: baifc
 * Description:
 */
@Slf4j
@Service
public class OrganizationService {

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    /**
     * Hystrix将使用线程池来委派所有对远程服务的请求，默认情况下，所有的Hystrix命令，将使用一个线程池来处理请求，默认将有10个线程来处理这些请求
     *      远程请求可以是任何远程资源，比如rest资源，或数据库等...
     * @param organizationId
     * @return
     */
    @HystrixCommand(
            /*
                commandProperties可以定制断路器的行为
                commandProperties接收一个HystrixProperty对象数组，它可以传入自定义属性来配置Hystrix断路器
             */
//            commandProperties = {
//                // timeoutInMilliseconds默认为1s
//                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//            },
            //fallbackMethod 属性定义了类中的一个方法，如果来自Hystrix 的调用失败，那么就会调用该方法
            fallbackMethod = "fallBackOrganization",
            // 定义线程池的唯一名称，可以和其他的HystrixCommand的线程池进行隔离
            threadPoolKey = "getOrganizationPool",
            // threadPoolProperties定义当前threadPool的行为
            threadPoolProperties = {
                    // 定义线程池中线程的最大数量
                    @HystrixProperty(name = "coreSize", value = "30"),
                    // maxQueueSize 用于定义一个位于线程池前的队列，它可以对传入的请求进行排队
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
                    // queueSizeRejectionThreshold参数可以动态调整排队的线程池的大小，只有在maxQueueSize设置大于0时，才能设置
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "10")
            }

    )
    public Organization getOrganization(String organizationId) {
        return organizationFeignClient.getOrganization(organizationId);
    }

    /**
     * 该方法相当于一个后备策略方法
     *      该方法必须和被保护的方法处于同一个类中，且必须具有和原始方法完全相同的方法签名
     * @param organizationId
     * @return
     */
    private Organization fallBackOrganization(String organizationId) {
        Organization organization = new Organization();
        organization.setId("fallback data");
        organization.setContactPhone("12345678");
        organization.setContactName("baifc");
        organization.setContactEmail("123456@qq.com");
        return organization;
    }


}
