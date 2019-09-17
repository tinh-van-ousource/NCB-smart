package com.tvo.dao;

import com.tvo.model.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderDAO extends JpaRepository<ProviderEntity, Long>{
	ProviderEntity findByProviderCode(String providerCode);
}
