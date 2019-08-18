package com.baifc.licenseservice.service;

import com.baifc.licenseservice.model.License;
import com.baifc.licenseservice.model.Organization;
import com.baifc.licenseservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.licenseservice.service
 * Created: 2019-08-18.
 * Auther: baifc
 * Description:
 */
@Service
public class LicenseRestService {

    @Value("${example.property}")
    private String exampleProperty;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LicenseRepository licenseRepository;

    public License getLicense(String organizationId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        // 这里和使用普通的restTemplate有一些差异，
        // 在使用支持Ribbon的RestTemplate时，使用Eureka服务ID来构建目标URL，这里的服务ID是organization-service
        //
        ResponseEntity<Organization> responseEntity = restTemplate
                .exchange("http://organization-service/v1/organizations/{organizationId}", HttpMethod.GET, null, Organization.class, organizationId);

        Organization organization = responseEntity.getBody();
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
