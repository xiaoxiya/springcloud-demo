package com.xiaoxiya.licensingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @ EnableDiscoveryClient,激活spring discoveryclient，使其可用，提供了对ribbon及其内部注册的服务最低访问级别
 * @ EnableFeignClients，使用feignClient需要这个注解
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LicensingserviceApplication {

    /**
     *  @LoadBalanced 注 解 告 诉Spring Cloud 创建一个支持Ribbon 的 RestTemplate 类
     *  在 Spring Cloud 的早期版本， RestTemplate 类自劢支持 Ribbon
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(LicensingserviceApplication.class, args);
    }

}
