/**
 * 
 */
package com.tvo.service;

import com.tvo.controllerDto.SearchCity;
import com.tvo.dto.CityDto;
import com.tvo.request.CreateCityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author Ace
 *
 */

public interface CityService {
	public List<CityDto> findAll();
	public CityDto createCity(CreateCityRequest request) ;
	public Page<CityDto> searchCity(SearchCity searchCity, Pageable pageable);
}
