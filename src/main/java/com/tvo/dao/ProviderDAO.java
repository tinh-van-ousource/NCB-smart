package com.tvo.dao;

import com.tvo.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderDAO extends JpaRepository<Provider, Long>{
	Provider findByProviderName(String providerName);
}
