package com.baifc.licenseservice.controller;

import com.baifc.licenseservice.model.License;
import com.baifc.licenseservice.service.LicenseDiscoveryService;
import com.baifc.licenseservice.service.LicenseFeignService;
import com.baifc.licenseservice.service.LicenseRestService;
import com.baifc.licenseservice.service.LicenseHystrixService;
import com.baifc.licenseservice.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * projectName: license-service
 * packageName: com.baifc.licenseservice.controller
 * Created: 2019-08-12.
 * Auther: baifc
 * Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseController {

//    @Value("${example.property}")
//    private String exampleProperty;

    @Autowired
    private LicenseDiscoveryService licenseDiscoveryService;

    @Autowired
    private LicenseRestService licenseRestService;

    @Autowired
    private LicenseFeignService licenseFeignService;

    @Autowired
    private LicenseHystrixService licenseHystrixService;

//    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
//    public License getLicense(
//            @PathVariable("organizationId") String organizationId,
//            @PathVariable("licenseId")String licenseId) {
//
//        System.out.println("exampleProperty = " + exampleProperty);
//        return  new License()
//                .withId(licenseId)
//                .withOrganizationId(organizationId)
//                .withProductName("Teleco")
//                .withLicenseType("Seat")
//                .withOrganizationId("TestOrg");
//    }

    @RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
    public License getLicensesWithClient( @PathVariable("organizationId") String organizationId,
                                          @PathVariable("licenseId") String licenseId) {

        return licenseFeignService.getLicense(organizationId,licenseId);
    }

    @GetMapping(value = "/")
    public List<License> getLicenseByOrganizationId(@PathVariable("organizationId") String organizationId) {
        log.info("LicenseController getLicenseByOrganizationId--CorrelationId: {} ", UserContextHolder.getUserContext().getCorrelationId());
        return licenseHystrixService.getLicensesByOrganizationId(organizationId);
    }

}
