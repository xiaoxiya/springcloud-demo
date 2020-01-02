package com.xiaoxiya.licensingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxiya
 * @date 2019/12/31 16:39
 * @describe
 */
@Component
public class ServiceCofig {
    @Value("${example.property}")
    private String exampleProperty = "";

    public String getExampleProperty() {
        return exampleProperty;
    }
}
