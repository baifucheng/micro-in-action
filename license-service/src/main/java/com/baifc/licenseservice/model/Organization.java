package com.baifc.licenseservice.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class Organization implements Serializable {
    private static final long serialVersionUID = -751835776916427725L;
    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}
