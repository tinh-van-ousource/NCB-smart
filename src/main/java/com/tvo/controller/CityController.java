package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchCity;
import com.tvo.dto.CityDto;
import com.tvo.dto.CreateCityDto;
import com.tvo.request.CreateCityRequest;
import com.tvo.request.DeleteCityRequest;
import com.tvo.request.UpdateCityRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.CityServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/city")
public class CityController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	CityServiceImpl cityService; 
	@GetMapping(value = "/getAll")
	public ResponeData<List<CityDto>>  getAll(){
		logger.info("Lấy danh sách tất cả Tỉnh/Thành phố");
		return new ResponeData<List<CityDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, cityService.findAll());
	};
	
	@PostMapping(value="/createCity")
	public ResponeData<CreateCityDto> createUser(@RequestBody CreateCityRequest request) {
		CreateCityDto dto = cityService.createCity(request);
		if(dto == null) {
			return new ResponeData<CreateCityDto>(AppConstant.CITY_CREATE_DUPLICATE_ERROR_CODE, AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE, null);
		}
		logger.info("Tạo mới Tỉnh/Thành phố");
		return new ResponeData<CreateCityDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
	@GetMapping(value = "/searchCity")
	public ResponeData<Page<CityDto>> searchCity(@ModelAttribute SearchCity searchCity, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<CityDto> CityDtos = cityService.searchCity(searchCity, pageable);
		 logger.info("Tìm kiếm Tỉnh/Thành phố");
		return new ResponeData<Page<CityDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, CityDtos) ;
	}
	@PutMapping(value = "update")
	public ResponeData<CityDto> update(@RequestBody UpdateCityRequest request) {
		CityDto cityDto = cityService.update(request);
		if (cityDto == null) {
			return new ResponeData<CityDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}	
		logger.info("Cập nhật thông tin Tỉnh/Thành phố");
		return new ResponeData<CityDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, cityDto);
	}
	@GetMapping(value = "/detail")

	    public ResponeData<CityDto> detail(@RequestParam String proId) {
		CityDto dto = cityService.detail(proId);
	        if (dto == null) {
	            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
	        }
	        logger.info("Chi tiết Tỉnh/Thành phố");
	        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	    }

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String proId) {
		boolean deleteFlag = cityService.delete(proId);
        if (deleteFlag) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        logger.info("Xóa Tỉnh/Thành phố");
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
 	