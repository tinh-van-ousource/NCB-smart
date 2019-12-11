package com.tvo.dao;

import com.tvo.model.ProviderEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface ProviderDAO extends JpaRepository<ProviderEntity, Long>{

	ProviderEntity findByProviderCode(String providerCode);

	ProviderEntity findByProviderCodeAndServiceCodeAndPartner(String providerCode, String serviceCode, String partner);
	
	@Query("SELECT DISTINCT c.serviceCode, c.serviceName from ServiceMbapp c ")
	List<Object> getListServiceCode();
}
