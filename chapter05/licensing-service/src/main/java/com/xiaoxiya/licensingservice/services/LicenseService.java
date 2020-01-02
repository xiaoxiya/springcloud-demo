package com.xiaoxiya.licensingservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xiaoxiya.licensingservice.clients.OrganizationDiscoveryClient;
import com.xiaoxiya.licensingservice.clients.OrganizationFeignClient;
import com.xiaoxiya.licensingservice.clients.OrganizationRestTemplateClient;
import com.xiaoxiya.licensingservice.config.ServiceCofig;
import com.xiaoxiya.licensingservice.model.License;
import com.xiaoxiya.licensingservice.model.Organization;
import com.xiaoxiya.licensingservice.repository.LicenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author xiaoxiya
 * @date 2019/12/31 16:45
 * @describe
 */
@Service
public class LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(License.class);

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceCofig config;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;


    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getorganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
            default:
                organization = organizationRestTemplateClient.getOrganization(organizationId);
        }
        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license.withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    /**
     * HystrixCommand注解被用于使用Hystrix断路器包装getLicenseByOrg方法
     * commandProperties 属性让你提供额外的属性来自定义 Hystrix
     * execution.isolation.thread.timeoutInMilliseconds 用于设置断路器的超时时间长度（以毫秒为单位）
     * threadPoolKey属性定义了线程池的唯一名称
     * threadPoolProperties属性允许定义和定制的线程池的行为
     * coreSize定义线程池中线程的最大数量
     * maxQueueSize让你在线程池前面定义一个队列，他能使进入的请求排队
     */
    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")},
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")}
    )
    public List<License> getLicenseByOrg(String organizationId) {
        logger.debug("LicenseService.getLicensesByOrg Correlation id: {} ", "");
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    /**
     * 随机超时调用licensing service数据库
     * 三分之一的机会数据库调用长期运行
     * 模拟getLicensesByOrg()方法通过调用运行一个缓慢的数据库查询
     */
    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回退方法，终端调用时调用，
     * 与@HystrixCommand保护的原始方法具有相同的
     * 方法参数定义，并且在同一个类中
     */
    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License().withId("0000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName("Sorry no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
    }

    @HystrixCommand
    private Organization getOrganization(String organizationId) {
        return organizationRestTemplateClient.getOrganization(organizationId);
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());
        System.out.println(license.getLicenseId());
        licenseRepository.save(license);
    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license){
        licenseRepository.deleteById(license.getLicenseId());
    }
}
