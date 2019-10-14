package com.tvo.dao;

import com.tvo.model.ProductFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFeeDAO extends JpaRepository<ProductFeeEntity, Long> {

    ProductFeeEntity findByGrprdId(String grprdId);
}
