package com.tvo.dao;

import com.tvo.model.ServiceRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRegisterRepo extends JpaRepository<ServiceRegisterEntity, Long>, ServiceRegisterRepoCustom {

}
