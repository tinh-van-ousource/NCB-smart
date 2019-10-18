package com.tvo.dao;

import com.tvo.model.ProductFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFeeDAO extends JpaRepository<ProductFeeEntity, Long> {

    ProductFeeEntity findByGrprdId(String grprdId);

    @Query("SELECT pfe FROM ProductFeeEntity pfe WHERE pfe.grprdId = :grprdId")
    List<ProductFeeEntity> findListByGrprdId(String grprdId);
}
