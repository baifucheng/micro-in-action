package com.baifc.licenseservice.controller;

import com.baifc.licenseservice.model.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: license-service
 * packageName: com.baifc.licenseservice.controller
 * Created: 2019-08-12.
 * Auther: baifc
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseController {

    @Value("${example.property}")
    private String exampleProperty;

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId")String licenseId) {

        System.out.println("exampleProperty = " + exampleProperty);
        return  new License()
                .withId(licenseId)
                .withOrganizationId(organizationId)
                .withProductName("Teleco")
                .withLicenseType("Seat")
                .withOrganizationId("TestOrg");
    }

}
