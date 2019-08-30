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


    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        System.out.println("organizationId = " + organizationId);
        Organization org = orgService.getOrg(organizationId);
        System.out.println(org);
        return org;
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
       orgService.saveOrg( org );
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    public Map<String, String> deleteOrganization(@PathVariable("organizationId") String organizationId) {
        System.out.println("deleteOrganization = " + organizationId);
        Map<String, String> map = new HashMap<>();
        map.put("200", "成功");
        return map;
    }

}
