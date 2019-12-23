package com.tvo.dao;

import com.tvo.model.ProductFeeMbApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFeeDAO extends JpaRepository<ProductFeeMbApp, Long> {

    ProductFeeMbApp findByGrprdId(String grprdId);
    
    @Query("SELECT pfe FROM ProductFeeMbApp pfe WHERE pfe.grprdId = :grprdId AND pfe.prdName = :prdName AND pfe.prdCode = :prdCode AND pfe.feeType = :feeType")
    ProductFeeMbApp findByGrprdIdAndPrdNameAndPrdCodeAndFeeType(String grprdId, String prdName, String prdCode, String feeType);

    @Query("SELECT pfe FROM ProductFeeMbApp pfe WHERE pfe.grprdId = :grprdId")
    List<ProductFeeMbApp> findListByGrprdId(String grprdId);
}
