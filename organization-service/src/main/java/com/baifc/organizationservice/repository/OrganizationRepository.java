package com.baifc.organizationservice.repository;

import com.baifc.organizationservice.model.Organization;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRepository  {
    public Organization findById(String organizationId) {
        Organization organization = new Organization();
        organization.setId(organizationId);
        organization.setContactPhone("12345678");
        organization.setContactName("baifc");
        organization.setContactEmail("123456@qq.com");
        return organization;
    }

    public void save(Organization org) {

    }
}
