package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.searchCity;
import com.tvo.controllerDto.searchProvider;
import com.tvo.dto.CityDto;
import com.tvo.dto.ProviderDto;
import com.tvo.request.CreateCityRequest;
import com.tvo.request.CreateProviderRequest;

public interface ProviderService {
	public List<ProviderDto> findAll();
	public Page<ProviderDto> searchProvider(searchProvider searchProvider, Pageable pageable);
	public ProviderDto createProvider(CreateProviderRequest request) ;
}
