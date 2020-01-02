package com.xiaoxiya.licensingservice.clients;

import com.xiaoxiya.licensingservice.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xiaoxiya
 * @date 2019/12/31 16:33
 * @describe OrganizationFeignClient
 * FeignClient注解确定你的服务是哟个Feign
 */
@FeignClient("organizationservice")
public interface OrganizationFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/v1/organizations/{organizationId}", consumes = "application/json")
    Organization getorganization(@PathVariable("organizationId") String organizationId);
}
