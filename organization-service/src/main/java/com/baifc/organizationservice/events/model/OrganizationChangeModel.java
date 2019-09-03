package com.baifc.organizationservice.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.organizationservice.events.model
 * Created: 2019/9/3.
 * Auther: baifc
 * Description: 消息结构体
 */
@Data
@AllArgsConstructor
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
}
