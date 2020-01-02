package com.xiaoxiya.organizationservice.controllers;

import com.xiaoxiya.organizationservice.model.Organization;
import com.xiaoxiya.organizationservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author xiaoxiya
 * @date 2019/12/31 14:34
 * @describe
 */
@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public Optional<Organization> getOrganization(@PathVariable("organizationId") String organizationId) {
        return service.getOrg(organizationId);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {
         service.updateOrg(organization);
    }
    @RequestMapping(value = "/{organizationId}", method = RequestMethod.POST)
    public void saveOrganization( @RequestBody Organization organization) {
         service.saveOrg(organization);
    }
    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable("organizationId") String orgId, @RequestBody Organization organization) {
         service.deleteOrg(organization);
    }

}
