package com.baifc.licenseservice.service;

import com.baifc.licenseservice.model.License;
import com.baifc.licenseservice.model.Organization;
import com.baifc.licenseservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.service
 * Created: 2019/8/30.
 * Auther: baifc
 * Description:
 */
@Service
public class LicenseOAuth2Service {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private OAuth2RestTemplate restTemplate;

    @Autowired
    private LicenseRepository licenseRepository;

    public License getLicense(String organizationId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        System.out.println("license = " + license);

        // 使用支持OAuth2的resttemplat，构建方式和普通的restTemplate完全相同
        ResponseEntity<Organization> responseEntity = restTemplate
                .exchange("http://localhost:8081/v1/organizations/{organizationId}",
                        HttpMethod.GET, null, Organization.class, organizationId);

        Organization organization = responseEntity.getBody();
        if (organization == null) {
            System.out.println("organization is null");
            return null;
        }
        System.out.println(organization);
        return license
                .withOrganizationName(organization.getName())
                .withContactName(organization.getContactName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone());
//                .withComment(exampleProperty);
    }

}
