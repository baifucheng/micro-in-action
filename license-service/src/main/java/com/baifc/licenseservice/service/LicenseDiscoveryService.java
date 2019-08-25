package com.baifc.licenseservice.service;

import com.baifc.licenseservice.model.License;
import com.baifc.licenseservice.model.Organization;
import com.baifc.licenseservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
public class LicenseDiscoveryService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LicenseRepository licenseRepository;

//    @Value("${example.property}")
//    private String exampleProperty;

    public License getLicense(String organizationId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        // 这里直接new了一个restTemplate，没有通过@Autowired注入
        // 如果将restTemplate交于spring管理，那么restTemplate将会启用一个Ribbon拦截器，这个拦截器会改变RestTemplate的创建url的行为
        RestTemplate restTemplate = new RestTemplate();

        // 获取组织服务的所有实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");

        if (instances.size() == 0) {
            return null;
        }

        String uri = instances.get(0).getUri().toString();
        System.out.println("uri = " + uri);     // uri = http://192.168.199.121:8081
        String serviceUri = String.format("%s/v1/organizations/%s", uri, organizationId);
        ResponseEntity<Organization> responseEntity = restTemplate
                .exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);

        Organization organization = responseEntity.getBody();
        if (organization == null) {
            return null;
        }
        return license
                .withOrganizationName(organization.getName())
                .withContactName(organization.getContactName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone());
//                .withComment(exampleProperty);
    }
}
