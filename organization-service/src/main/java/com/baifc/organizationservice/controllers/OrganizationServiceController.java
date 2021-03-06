package com.baifc.organizationservice.controllers;


import com.baifc.organizationservice.model.Organization;
import com.baifc.organizationservice.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/organizations")
public class OrganizationServiceController {
    @Autowired
    private OrganizationService orgService;


    @GetMapping(value="/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        System.out.println("organizationId = " + organizationId);
        Organization org = orgService.getOrg(organizationId);
        System.out.println(org);
        return org;
    }

    @PostMapping(value="/")
    public void saveOrganization(@RequestBody Organization org) {
       orgService.saveOrg( org );
    }

    @DeleteMapping(value="/{organizationId}")
    public Map<String, String> deleteOrganization(@PathVariable("organizationId") String organizationId) {
        System.out.println("deleteOrganization = " + organizationId);
        orgService.deleteOrg(organizationId);
        Map<String, String> map = new HashMap<>();
        map.put("200", "成功");
        return map;
    }

    @PutMapping(value = "/")
    public void updateOrganization(@RequestBody Organization org) {
        orgService.updateOrg(org);
    }

}
