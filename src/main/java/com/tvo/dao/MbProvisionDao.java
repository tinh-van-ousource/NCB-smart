package com.tvo.dao;

import com.tvo.model.MbProvision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@Repository
public interface MbProvisionDao extends JpaRepository<MbProvision, Long> {
	MbProvision findByProvisionCode(String provisionCode);
}
