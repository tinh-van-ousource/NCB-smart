package com.tvo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvo.model.ParConfigMultiIdEntity;

@Repository
public interface ParConfigMultiIdRepo extends JpaRepository<ParConfigMultiIdEntity, Long> {

    @Query("SELECT pce FROM ParConfigMultiIdEntity pce WHERE pce.param = :param ORDER BY pce.value")
    List<ParConfigMultiIdEntity> findByParam(@Param("param") String param);

    @Query("SELECT pce FROM ParConfigMultiIdEntity pce WHERE pce.param = :param AND pce.code = :code ")
    ParConfigMultiIdEntity findByParamAndCode(@Param("param") String param, @Param("code") String code);

    @Query("SELECT pce FROM ParConfigMultiIdEntity pce WHERE pce.param NOT IN :param ORDER BY pce.value")
    List<ParConfigMultiIdEntity> findOtherParam(@Param("param") List<String> param);
    
    @Query("SELECT pce FROM ParConfigMultiIdEntity pce WHERE pce.code = :code")
    ParConfigMultiIdEntity findByCode(@Param("code") String code);
}
