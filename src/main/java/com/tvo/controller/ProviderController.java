package com.tvo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.searchCity;
import com.tvo.controllerDto.searchProvider;
import com.tvo.dto.CityDto;
import com.tvo.dto.ProviderDto;
import com.tvo.request.CreateCityRequest;
import com.tvo.request.CreateProviderRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.ProviderServiceImpl;

@RestController
@RequestMapping(value = "/provider")
public class ProviderController {
	@Autowired
	ProviderServiceImpl providerServiceImpl;
	@GetMapping(value = "/getAll")
	public ResponeData<List<ProviderDto>>  getAll(){
		return new ResponeData<List<ProviderDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, providerServiceImpl.findAll());
	};
	@GetMapping(value = "/searchProvider")
	public ResponeData<Page<ProviderDto>> searchCity(@ModelAttribute searchProvider searchProvider, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<ProviderDto> ProviderDtos = providerServiceImpl.searchProvider(searchProvider, pageable);
		return new ResponeData<Page<ProviderDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ProviderDtos) ;
	}
	@PostMapping(value="/createProvider")
	public ResponeData<ProviderDto> createProvider(@ModelAttribute CreateProviderRequest request) {
		ProviderDto dto = providerServiceImpl.createProvider(request);
		if(dto == null) {
			return new ResponeData<ProviderDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<ProviderDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
}
