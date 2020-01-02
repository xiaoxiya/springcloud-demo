package com.xiaoxiya.licensingservice.utils;

import org.springframework.util.Assert;

/**
 * @author xiaoxiya
 * @date 2020/1/2 15:47
 * @describe
 */
public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static final UserContext getContext() {
        UserContext context = userContext.get();

        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);
        }
        return userContext.get();
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    private static final UserContext createEmptyContext() {
        return new UserContext();
    }
}
