package com.tvo.dao;

import com.tvo.model.CompanyEntity;
import com.tvo.model.ProviderEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, String>, CompanyRepoCustom {

	@Query("SELECT c FROM CompanyEntity c WHERE c.key.compCode = :compCode")
    CompanyEntity findByCompCode(String compCode);
    CompanyEntity findByCompName(String compName);
    CompanyEntity findByDao(String dao);
    
    @Query("SELECT c FROM CompanyEntity c WHERE c.key.compCode = :compCode AND c.key.mcn = :mcn AND c.key.mp = :mp")
    CompanyEntity findByCompCodeAndMcnAndMp(String compCode, String mcn, String mp);
    
    List<CompanyEntity> findCompanyEntities(String compCode, String compName, String mcn, String mp);
    
}