package com.tvo.service;

import com.tvo.controllerDto.SearchConsumerModel;
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.dto.DatUserProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Jul 31, 2019
 */
public interface DatUserProfileService {
	List<DatUserProfileDto> findAll();

	Page<DatUserProfileDto> searchDatUserProfile(SearchDatUserProfileModel searchModel, Pageable pageable);

	Page<DatUserProfileDto> searchConsumer(SearchConsumerModel searchModel, Pageable pageable);

}
