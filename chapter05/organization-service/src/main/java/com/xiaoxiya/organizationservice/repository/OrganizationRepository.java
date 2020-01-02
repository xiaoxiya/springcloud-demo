package com.xiaoxiya.organizationservice.repository;

import com.xiaoxiya.organizationservice.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * @author xiaoxiya
 * @date 2019/12/31 14:17
 * @describe
 * CrudRepository 基类包含有基本的 CRUD 方法
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String>  {
    public Optional<Organization> findById(String organizationId);
}