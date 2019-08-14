package com.baifc.licenseservice.model;

import lombok.Data;

@Data
public class License {
  private String id;
  private String organizationId;
  private String productName;
  private String licenseType;


  public License withId(String id){
    this.setId( id );
    return this;
  }

  public License withOrganizationId(String organizationId){
    this.setOrganizationId(organizationId);
    return this;
  }

  public License withProductName(String productName){
    this.setProductName(productName);
    return this;
  }

  public License withLicenseType(String licenseType){
    this.setLicenseType(licenseType);
    return this;
  }



}
