/**
 * 
 */
package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchCity;
import com.tvo.dto.CityDto;
import com.tvo.dto.CreateCityDto;
import com.tvo.request.CreateCityRequest;
import com.tvo.request.DeleteCityRequest;
import com.tvo.request.UpdateCityRequest;


/**
 * @author Ace
 *
 */

public interface CityService {
	public List<CityDto> findAll();
	public CreateCityDto createCity(CreateCityRequest request) ;
	public Page<CityDto> searchCity(SearchCity searchCity, Pageable pageable);
	public CityDto update(UpdateCityRequest request);
	
	public CityDto delete(DeleteCityRequest request);
	
	public CityDto detail(Long cityId);
}
