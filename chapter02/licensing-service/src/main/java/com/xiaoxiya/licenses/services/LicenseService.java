package com.xiaoxiya.licenses.services;

import com.xiaoxiya.licenses.model.License;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author xiaoxiya
 * @date 2019/12/25 17:52
 * @describe
 */
@Service
public class LicenseService {

    public License getLicense(String licenseId) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(UUID.randomUUID().toString())
                .withProductName("Test product name")
                .withLicenseType("perSeat");
    }

    public void saveLicense(License license){

    }

    public void updateLicense(License license){

    }

    public void deleteLicense(License license){

    }

}
