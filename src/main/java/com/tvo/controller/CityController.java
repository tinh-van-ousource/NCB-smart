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
import com.tvo.dto.CityDto;
import com.tvo.request.CreateCityRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.CityServiceImpl;


@RestController
@RequestMapping(value = "/city")
public class CityController {
	@Autowired
	CityServiceImpl cityService; 
	@GetMapping(value = "/getAll")
	public ResponeData<List<CityDto>>  getAll(){
		return new ResponeData<List<CityDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, cityService.findAll());
	};
	
	@PostMapping(value="/createCity")
	public ResponeData<CityDto> createUser(@ModelAttribute CreateCityRequest request) {
		CityDto dto = cityService.createCity(request);
		if(dto == null) {
			return new ResponeData<CityDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<CityDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
	@GetMapping(value = "/searchCity")
	public ResponeData<Page<CityDto>> searchCity(@ModelAttribute searchCity searchCity, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<CityDto> CityDtos = cityService.searchCity(searchCity, pageable);
		return new ResponeData<Page<CityDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, CityDtos) ;
	}
}
 	