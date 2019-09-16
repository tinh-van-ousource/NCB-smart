package com.tvo.dao;

import com.tvo.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, String>, CompanyRepoCustom {

    CompanyEntity findByCompCode(String compCode);
}
