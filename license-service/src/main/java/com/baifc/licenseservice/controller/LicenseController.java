package com.baifc.licenseservice.controller;

import com.baifc.licenseservice.model.License;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.licenseservice.controller
 * Created: 2019-08-31.
 * Auther: baifc
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/license")
public class LicenseController {

    @RequestMapping(value = "/{licenseId}")
    public License getLicense(@PathVariable("licenseId") String licenseId) {
        License license1 = new License();
        license1.setLicenseId(licenseId);
        license1.setOrganizationId("organizationId-111");
        license1.setProductName("apple1");
        return license1;
    }
}
