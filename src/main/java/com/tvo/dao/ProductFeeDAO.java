package com.tvo.dao;

import com.tvo.model.ProductFeeMbApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFeeDAO extends JpaRepository<ProductFeeMbApp, Long> {

    ProductFeeMbApp findByGrprdId(String grprdId);

    @Query("SELECT pfe FROM ProductFeeMbApp pfe WHERE pfe.grprdId = :grprdId")
    List<ProductFeeMbApp> findListByGrprdId(String grprdId);
}
