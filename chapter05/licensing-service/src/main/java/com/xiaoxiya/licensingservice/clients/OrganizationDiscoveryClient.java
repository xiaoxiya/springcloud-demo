package com.xiaoxiya.licensingservice.clients;

import com.xiaoxiya.licensingservice.model.Organization;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xiaoxiya
 * @date 2019/12/31 16:13
 * @describe 使用discoveryClient查找信息
 */
@Component
public class OrganizationDiscoveryClient {

    /**
     * DiscoveryClient 自动注入类
     * 使用这个类与 Ribbon 交互
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        //检索已在 Eureka 注册的组细服务的所有实例，获取组织服务的所有实例的列表
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        if (instances.size() == 0) return null;
        //检索我们需要的服务端点
        String serviceUri = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toASCIIString(), organizationId);
        //使 用 一 个 标 准 的 Spring RESTTemplate 类调用服务
        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}
