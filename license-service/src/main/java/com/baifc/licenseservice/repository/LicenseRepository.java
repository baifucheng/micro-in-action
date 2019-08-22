package com.baifc.licenseservice.repository;

import com.baifc.licenseservice.model.License;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.licenseservice.repository
 * Created: 2019-08-18.
 * Auther: baifc
 * Description:
 */
@Repository
public class LicenseRepository {

    public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId) {

        License license = new License();
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setProductName("apple");
        return license;
    }

    public List<License> findByOrganization(String organizationId) {

        License license1 = new License();
        license1.setLicenseId("licenseId1");
        license1.setOrganizationId(organizationId);
        license1.setProductName("apple1");

        License license2 = new License();
        license2.setLicenseId("licenseId2");
        license2.setOrganizationId(organizationId);
        license2.setProductName("apple2");

        License license3 = new License();
        license3.setLicenseId("licenseId2");
        license3.setOrganizationId(organizationId);
        license3.setProductName("apple3");

        return Arrays.asList(license1, license2, license3);
    }
}
