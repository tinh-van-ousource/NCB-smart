package com.tvo.dao;

import com.tvo.model.ServiceRegisterEntity;
import com.tvo.model.ServiceRegisterLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRegisterRepo extends JpaRepository<ServiceRegisterEntity, Long>, ServiceRegisterRepoCustom {

    @Query("SELECT srl FROM ServiceRegisterLogEntity srl WHERE srl.serviceRegisterId = :id ORDER BY srl.datetime DESC")
    List<ServiceRegisterLogEntity> retrieveServiceRegisterLogByServiceRegisterId(@Param("id") Long id);

    @Query("SELECT DISTINCT sr.service FROM ServiceRegisterEntity sr ORDER BY sr.service ASC")
    List<String> retrieveAllService();
}
