package com.baifc.organizationservice.services;

import com.baifc.organizationservice.model.Organization;
import com.baifc.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository orgRepository;

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId);
    }

    public void saveOrg(Organization org){
        org.setId(UUID.randomUUID().toString());
        orgRepository.save(org);
    }

    public void updateOrg(Organization org) {
        // TODO 省去验证逻辑
        orgRepository.update(org);
    }

    public void deleteOrg(String orgId) {
        orgRepository.delete(orgId);
    }
}
