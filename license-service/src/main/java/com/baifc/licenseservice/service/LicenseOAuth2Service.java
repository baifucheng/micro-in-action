package com.baifc.licenseservice.service;

import com.baifc.licenseservice.model.License;
import com.baifc.licenseservice.model.Organization;
import com.baifc.licenseservice.repository.db.LicenseRepository;
import com.baifc.licenseservice.repository.redis.OrganizationRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.service
 * Created: 2019/8/30.
 * Auther: baifc
 * Description:
 */
@Slf4j
@Service
public class LicenseOAuth2Service {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private OAuth2RestTemplate restTemplate;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationRedisRepository organizationRedisRepository;

    public License getLicense(String organizationId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        System.out.println("license = " + license);

        Organization organization = null;
        organization = organizationRedisRepository.findOrganization(organizationId);

        if (organization == null) {
            // 使用支持OAuth2的resttemplat，构建方式和普通的restTemplate完全相同
            ResponseEntity<Organization> responseEntity = restTemplate
                    .exchange("http://localhost:8081/v1/organizations/{organizationId}",
                            HttpMethod.GET, null, Organization.class, organizationId);

            organization = responseEntity.getBody();
            if (organization == null) {
                System.out.println("organization is null");
                return null;
            }
            organizationRedisRepository.saveOrganization(organization);
            log.info("已成功缓存" + organization.getName() + "的数据");
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
