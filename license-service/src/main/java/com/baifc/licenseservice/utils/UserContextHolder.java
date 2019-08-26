package com.baifc.licenseservice.utils;

import org.springframework.util.Assert;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.utils
 * Created: 2019/8/26.
 * Auther: baifc
 * Description:
 */
public class UserContextHolder {

    private static final ThreadLocal<UserContext> threadContext = new ThreadLocal<>();

    public static UserContext getUserContext() {
        UserContext userContext = threadContext.get();
        if (userContext == null) {
            threadContext.set(new UserContext());
        }
        return threadContext.get();
    }

    public static void setUserContext(UserContext userContext) {
        Assert.notNull(userContext, "userContext cant null!");
        threadContext.set(userContext);
    }
}
