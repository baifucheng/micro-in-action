package com.baifc.licenseservice.service;

import com.baifc.licenseservice.client.OrganizationFeignClient;
import com.baifc.licenseservice.model.License;
import com.baifc.licenseservice.model.Organization;
import com.baifc.licenseservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.licenseservice.service
 * Created: 2019-08-19.
 * Auther: baifc
 * Description:
 */
@Service
public class LicenseFeignService {

    @Value("${example.property}")
    private String exampleProperty;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private LicenseRepository licenseRepository;

    public License getLicense(String organizationId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization organization = organizationService.getOrganization(organizationId);
        if (organization == null) {
            System.out.println("organization is null");
            return null;
        }
        return license
                .withOrganizationName(organization.getName())
                .withContactName(organization.getContactName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone())
                .withComment(exampleProperty);
    }
}
