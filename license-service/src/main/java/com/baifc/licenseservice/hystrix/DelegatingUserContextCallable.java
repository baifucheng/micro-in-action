package com.baifc.licenseservice.hystrix;

import com.baifc.licenseservice.utils.UserContext;
import com.baifc.licenseservice.utils.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.hystrix
 * Created: 2019/8/26.
 * Auther: baifc
 * Description: 定义一个Callable类，将UserContext上下文注入到Hystrix命令中
 *  当调用Hystrix保护的方法的时候，Hystrix和springCloud会初始化一个DelegatingUserContextCallable实例
 *  传入一个通常有Hystrix线程池管理的线程调用的Callable类，这里是名称为delegate属性
 *  除了委托的Callable类之外，springCloud也会将UserContext对象，从发起调用的父线程传递出去
 *  这两个值在创建DelegatingUserContextCallable设置，具体操作在call()方法中
 */
public class DelegatingUserContextCallable<V> implements Callable<V> {
    private final Callable<V> delegate;
    private UserContext userContext;

    public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext) {
        this.delegate = delegate;
        this.userContext = userContext;
    }

    /**
     * call()方法在@HystrixCommand修饰的方法执行之前调用
     *
     *  setUserContext()方法将UserContext 对象存储在ThreadLocal变量中，这个ThreadLocal变量特定于当前的线程中
     *  设置了UserContext之后，就会调用委托的Callable类的call()方法
     *  delegate.call()方法会调用由@HystrixCommand修饰的方法
     */
    @Override
    public V call() throws Exception {
        UserContextHolder.setUserContext(userContext);
        try {
            return delegate.call();
        } finally {
            // TODO ? 为什么要置空
            userContext = null;
        }
    }
}
