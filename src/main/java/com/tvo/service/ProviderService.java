package com.tvo.service;

import com.tvo.controllerDto.searchProvider;
import com.tvo.dto.ProviderDto;
import com.tvo.request.CreateProviderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProviderService {
	public List<ProviderDto> findAll();
	public Page<ProviderDto> searchProvider(searchProvider searchProvider, Pageable pageable);
	public ProviderDto createProvider(CreateProviderRequest request) ;
}
