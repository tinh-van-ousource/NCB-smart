package com.tvo.dao;

import com.tvo.model.ConfigMbApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigMbAppDAO extends JpaRepository<ConfigMbApp, Long> {

    @Query("SELECT DISTINCT c.type FROM ConfigMbApp c WHERE c.code = :code AND c.type IS NOT NULL ORDER BY c.type ASC")
    List<String> getTypeByCode(String code);

    @Query("SELECT c.value, c.name FROM ConfigMbApp c WHERE c.type = :type AND c.code = :code")
    List<Object> findByTypeAndAndCode(String type, String code);
}
