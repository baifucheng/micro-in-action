package com.baifc.licenseservice.utils;

import lombok.Data;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.utils
 * Created: 2019/8/26.
 * Auther: baifc
 * Description: 上下文pojo类
 */
@Data
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORG_ID = "tmx-org-id";

    private String correlationId;
    private String authToken;
    private String userId;
    private String orgId;

    public UserContext() {
        this.correlationId = "";
        this.authToken = "";
        this.userId = "";
        this.orgId = "";
    }
}
