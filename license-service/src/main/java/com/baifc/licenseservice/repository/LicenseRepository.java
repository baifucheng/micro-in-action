package com.baifc.licenseservice.repository;

import com.baifc.licenseservice.model.License;
import org.springframework.stereotype.Repository;

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
}
