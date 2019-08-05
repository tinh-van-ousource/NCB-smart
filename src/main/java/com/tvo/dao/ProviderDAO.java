package com.tvo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tvo.model.BankTransfer;
import com.tvo.model.City;
import com.tvo.model.Provider;

public interface ProviderDAO extends JpaRepository<Provider, Long>{
	Provider findByProviderName(String providerName);
}
