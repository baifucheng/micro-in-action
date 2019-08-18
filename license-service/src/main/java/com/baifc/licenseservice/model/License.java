package com.baifc.licenseservice.model;


public class License{

  private String licenseId;
  private String organizationId;
  private String organizationName;
  private String contactName;
  private String contactPhone;
  private String contactEmail;
  private String productName;
  private String licenseType;
  private Integer licenseMax;
  private Integer licenseAllocated;
  private String comment;


  public Integer getLicenseMax() {
    return licenseMax;
  }

  public void setLicenseMax(Integer licenseMax) {
    this.licenseMax = licenseMax;
  }

  public Integer getLicenseAllocated() {
    return licenseAllocated;
  }

  public void setLicenseAllocated(Integer licenseAllocated) {
    this.licenseAllocated = licenseAllocated;
  }


  public String getLicenseId() {
    return licenseId;
  }

  public void setLicenseId(String licenseId) {
    this.licenseId = licenseId;
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getLicenseType() {
    return licenseType;
  }

  public void setLicenseType(String licenseType) {
    this.licenseType = licenseType;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public License withId(String id){
    this.setLicenseId(id);
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

  public License withLicenseMax(Integer licenseMax){
    this.setLicenseMax(licenseMax);
    return this;
  }

  public License withLicenseAllocated(Integer licenseAllocated){
    this.setLicenseAllocated(licenseAllocated);
    return this;
  }

  public License withComment(String comment){
    this.setComment(comment);
    return this;
  }

  public License withOrganizationName(String organizationName){
    this.setOrganizationName(organizationName);
    return this;
  }

  public License withContactName(String contactName){
    this.setContactName(contactName);
    return this;
  }

  public License withContactPhone(String contactPhone){
    this.setContactPhone(contactPhone);
    return this;
  }

  public License withContactEmail(String contactEmail){
    this.setContactEmail(contactEmail);
    return this;
  }




}
