package com.baifc.licenseservice.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.hystrix
 * Created: 2019/8/26.
 * Auther: baifc
 * Description:
 */
@Configuration
public class ThreadLocalConfiguration {

    @Autowired(required = false)
    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    /**
     * Hystrix只允许一个HystrixConcurrencyStrategy
     * Spring 将尝试自动装配在现有的任何HystrixConcurrencyStrategy（如果它存在）中
     * 最后，完成所有的工作之后，我们使用Hystrix插件把在init()方法开头获取的原始Hystrix以组件重新注册回来
     */
    @PostConstruct
    public void init() {
        // 因为要注册一个新的并发策略，所以要获取所有其他的Hystrix组件，然后重新设置Hystrix插件
        HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance()
                .getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance()
                .getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance()
                .getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance()
                .getCommandExecutionHook();

        HystrixPlugins.reset();

        // 使用Hystrix 插件注册自定义的Hystrix并发策略(ThreadConcurrency Strategy)
        HystrixPlugins.getInstance().registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
        // 然后重新注册Hystrix插件中使用的所有Hystrix组件
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
    }
}
