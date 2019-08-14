package com.tvo.dao;

import com.tvo.model.ServiceRegisterLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author NgocDC
 */
@Repository
public interface ServiceRegisterLogRepo extends JpaRepository<ServiceRegisterLogEntity, Long> {

}
