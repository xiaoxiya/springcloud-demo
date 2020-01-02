package com.xiaoxiya.licensingservice.clients;

import com.xiaoxiya.licensingservice.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaoxiya
 * @date 2019/12/31 16:28
 * @describe 使用支持Ribbon的RestTemplate调用服务
 */
@Component
public class OrganizationRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        //当 使 用 支 持 Ribbon 的RestTemplate 时，你创建使用Eureka 的服务 ID 的目标 URL
        ResponseEntity<Organization> restExchange = restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}
