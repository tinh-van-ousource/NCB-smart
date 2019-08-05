/**
 * 
 */
package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.searchCity;
import com.tvo.dto.CityDto;
import com.tvo.request.CreateCityRequest;


/**
 * @author Ace
 *
 */

public interface CityService {
	public List<CityDto> findAll();
	public CityDto createCity(CreateCityRequest request) ;
	public Page<CityDto> searchCity(searchCity searchCity, Pageable pageable);
}
