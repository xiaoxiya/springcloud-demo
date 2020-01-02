package com.xiaoxiya.licensingservice.repository;

import com.xiaoxiya.licensingservice.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaoxiya
 * @date 2019/12/31 16:08
 * @describe
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    public List<License> findByOrganizationId(String organizationId);
    public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

}
