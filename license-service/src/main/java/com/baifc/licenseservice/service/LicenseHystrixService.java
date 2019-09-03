package com.baifc.licenseservice.service;

import com.baifc.licenseservice.model.License;
import com.baifc.licenseservice.repository.db.LicenseRepository;
import com.baifc.licenseservice.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.service
 * Created: 2019/8/22.
 * Auther: baifc
 * Description:
 */
@Slf4j
@Service
public class LicenseHystrixService {

    @Autowired
    private LicenseRepository licenseRepository;

    /**
     * 每当Hystrix 命令遇到服务错误时，它将开始一个默认10s的计时器，用于检查服务调用失败的频率
     * 在10s窗口内达到最少的远程资源调用次数时，Hystrix将开始查看整体故障的百分比
     * 如果超过错误阔值的百分比， Hystrix 将“跳闸”断路器，防止更多的调用访问远程资源
     * 如果远程调用失败的百分比未达到要求的阔值，并且10 s 窗口已过去， Hystrix 将重置断路器的统计信息
     *
     * Hystrix在一个远程调用上“跳闸”断路器时，它将尝试启动一个新的活动窗口。每隔5s（这个值是可配置的）
     * Hystrix会让一个调用到达这个苦苦挣扎的服务。如果调用成功
     * Hystrix将重置断路器并重新开始让调用通过
     * 如果调用失败，Hystrix 将保持断路器断开，并在另一个5s里再次尝试上述步骤。
     * @param organizationId
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "fallBackLicenses",
            threadPoolKey = "getLicensesByOrganizationId",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
            },
            commandProperties = {
                    // 控制Hystrix跳闸之前，10s内发生必须发生的调用数量
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    // 在超过circuitBreaker . requestVolumeThreshold 值之后在断路器跳闸之前必须达到的调用失败（由于超时、抛出异常或返回HTTP 500 ）百分比
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    // 断路器跳闸之后， Hystrix 允许另一个调用通过以便查看服务是否恢复健康之前Hystrix 的休眠时间
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    // 控制Hystrix 用来监视服务调用问题的窗口大小，其默认值为10 000 ms
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                    /*
                        控制在定义的滚动窗口中收集统计信息的次数
                        在这个窗口中， Hystrix 在桶（ bucket ）中收集度量数据，并检查这些桶中的统计信息
                        以确定远程资源、调用是否失败。
                        给metrics.rollingStats.timeInMilliseconds设置的值必须能被定义的桶的数量值整除
                        检查的统计窗口越小且在窗口中保留的桶的数量越多，就越会加剧高请求服务的CPU利用率和内存利用率。
                     */
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")
            }
    )
    public List<License> getLicensesByOrganizationId(String organizationId) {
        /*
            这里由于被@HystrixCommand注解修饰，该方法会使用一个新的线程池处理，Hystrix不会将父线程的上下文传播到子线程
            这里如果不做处理，那么correlationId将无法传播到该方法
            使用HystrixConcurrencyStrategy机制，则可以将父线程的上下文传播到由Hystrix所管理的线程池中
         */
        log.info("LicenseHystrixService getLicenseByOrganizationId--CorrelationId: {} ", UserContextHolder.getUserContext().getCorrelationId());
        // 模拟阻塞
        randomlyRunLong();
        // 模拟sql调用
        return licenseRepository.findByOrganization(organizationId);
    }

    private void randomlyRunLong(){
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum==3) sleep();
    }

    private void sleep(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private List<License> fallBackLicenses(String organizationId) {
        License license1 = new License();
        license1.setLicenseId("fallBack1");
        license1.setOrganizationId(organizationId);
        license1.setProductName("apple1");

        License license2 = new License();
        license2.setLicenseId("fallBack2");
        license2.setOrganizationId(organizationId);
        license2.setProductName("apple2");

        return Arrays.asList(license1, license2);
    }
}
