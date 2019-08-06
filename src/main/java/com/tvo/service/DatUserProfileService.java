package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchConsumerModel;
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.dto.DatUserProfileDto;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Jul 31, 2019
 */
public interface DatUserProfileService {
	public List<DatUserProfileDto> findAll();

	public Page<DatUserProfileDto> searchDatUserProfile(SearchDatUserProfileModel searchModel, String filter,
			Pageable pageable);

	Page<DatUserProfileDto> searchConsumer(SearchConsumerModel searchModel, String filter, Pageable pageable);

//	public Page<DatUserProfileDto> filterUser(List<SearchCriteria> params,Pageable pageable);
//	
//	public Page<DatUserProfileDto> search(List<SearchCriteria> params,Pageable pageable);

//	public Page<DatUserProfileDto> findAll(Specification<DatUserProfile> spec, Pageable pageable);
}
