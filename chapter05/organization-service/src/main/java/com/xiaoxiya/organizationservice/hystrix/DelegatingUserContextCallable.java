package com.xiaoxiya.organizationservice.hystrix;



import com.xiaoxiya.organizationservice.utils.UserContext;
import com.xiaoxiya.organizationservice.utils.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * @author xiaoxiya
 * @date 2020/1/2 16:30
 * @describe
 */
public final class DelegatingUserContextCallable<v> implements Callable<v> {

    private final Callable<v> delegate;

    private UserContext originalUserContext;

    /**
     * 自定义回调类将通过原始的回调类传递，
     * 该类将调用你的Hystrix受保护的代码，UserContext来自父线程
     */
    public DelegatingUserContextCallable(Callable<v> delegate, UserContext originalUserContext) {
        this.delegate = delegate;
        this.originalUserContext = originalUserContext;
    }

    /**
     * 通过HystrixCommand注解保护方法之前Call() 方法被调用
     */
    @Override
    public v call() throws Exception {
        //设置userContext.ThreadLocal变量存储UserContext，
        //它与正在运行线程的Hystrix保护方法关联
        UserContextHolder.setContext(originalUserContext);
        try {
            //在 Hystrix 保护方法上，一旦 UserContext 被
            //设 置 调 用 call() 方 法 ； 例 如 ， 你 的
            //LicenseServer.getLicenseByOrg()方法
            return delegate.call();
        }
        finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate,
                                         UserContext userContext) {
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}
