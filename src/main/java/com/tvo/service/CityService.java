/**
 * 
 */
package com.tvo.service;

import com.tvo.controllerDto.SearchCity;
import com.tvo.dto.CityDto;
import com.tvo.dto.CreateCityDto;
import com.tvo.request.CreateCityRequest;
import com.tvo.request.DeleteCityRequest;
import com.tvo.request.UpdateCityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author Ace
 *
 */

public interface CityService {
	public List<CityDto> findAll();
	public CreateCityDto createCity(CreateCityRequest request) ;
	public Page<CityDto> searchCity(SearchCity searchCity, Pageable pageable);
	public CityDto update(UpdateCityRequest request);
	
	public Boolean delete(String proId);
	
	public CityDto detail(String proId);
}
