package com.tvo.dao;

import com.tvo.model.CompanyEntity;
import com.tvo.model.ProviderEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, String>, CompanyRepoCustom {

    CompanyEntity findByCompCode(String compCode);
    CompanyEntity findByCompName(String compName);
    CompanyEntity findByDao(String dao);
    
    CompanyEntity findByCompCodeAndMcnAndMp(String compCode, String mcn, String mp);
    
}