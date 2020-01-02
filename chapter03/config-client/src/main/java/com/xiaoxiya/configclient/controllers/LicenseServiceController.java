package com.xiaoxiya.configclient.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxiya
 * @date 2019/12/27 9:58
 * @describe
 */
@RestController
public class LicenseServiceController {

    @Value("${example.property}")
    private String property;

    @GetMapping(value = "/getProperty")
    public String getProperty() {
        return property;
    }

}
