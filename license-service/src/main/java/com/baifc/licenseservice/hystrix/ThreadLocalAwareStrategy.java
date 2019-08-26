package com.baifc.licenseservice.hystrix;

import com.baifc.licenseservice.utils.UserContextHolder;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.hystrix
 * Created: 2019/8/26.
 * Auther: baifc
 * Description: 这是一个自定义的并发策略，它将包装Hystrix线程的调用，可以使父线程的上下文信息注入到由Hystrix管理的线程中
 *  Hystrix只允许为应用程序定义一个HystrixConcurrencyStrategy
 *  SpringCloud已经定义了一个HystrixConcurrencyStrategy，用于处理spring安全的传播
 *  SpringCloud可以自定义自己的Hystrix的并发策略，使其插入到默认的hystrix并发策略之中
 */
public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    /**
     * Spring Cloud 已经定义了一个并发类。将已存在的并发策略传入自定义的HystrixConcurrencyStrategy 的类构造器中
     * @param existingConcurrencyStrategy 已存在的并发策略
     */
    public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingConcurrencyStrategy) {
        this.existingConcurrencyStrategy = existingConcurrencyStrategy;
    }

    /**
     * 重写方法，调用existingConcurrencyStrategy的方法实现，或调用父类的实现
     */
    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                            HystrixProperty<Integer> corePoolSize,
                                            HystrixProperty<Integer> maximumPoolSize,
                                            HystrixProperty<Integer> keepAliveTime,
                                            TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue) {
        return existingConcurrencyStrategy != null ?
                existingConcurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
                : super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return existingConcurrencyStrategy != null ? existingConcurrencyStrategy.getBlockingQueue(maxQueueSize)
                : super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return existingConcurrencyStrategy != null ? existingConcurrencyStrategy.getRequestVariable(rv) : super.getRequestVariable(rv);
    }

    /**
     * 这个方法传递了DelegatingUserContextCallable的实现
     * 将UserContext从执行Rest服务的父线程，设置为保护正在进行工作的Hystrix命令线程
     * @param callable
     * @param <T>
     * @return
     */
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return existingConcurrencyStrategy != null ? existingConcurrencyStrategy.wrapCallable(new DelegatingUserContextCallable<>(callable, UserContextHolder.getUserContext()))
                : super.wrapCallable(new DelegatingUserContextCallable<>(callable, UserContextHolder.getUserContext()));
    }


}
