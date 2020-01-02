package com.xiaoxiya.organizationservice.service;

import com.xiaoxiya.organizationservice.model.Organization;
import com.xiaoxiya.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author xiaoxiya
 * @date 2019/12/31 14:29
 * @describe
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    public Optional<Organization> getOrg(String organizationId) {
        return repository.findById(organizationId);
    }

    public void saveOrg(Organization org){
        org.setId(UUID.randomUUID().toString());
        System.out.println(org.getId());
        repository.save(org);
    }

    public void updateOrg(Organization organization) {
        repository.save(organization);
    }

    public void deleteOrg(Organization org) {
        repository.delete(org);
    }
}
