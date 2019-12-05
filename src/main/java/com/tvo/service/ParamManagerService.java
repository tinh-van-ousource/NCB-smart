package com.tvo.service;

import com.tvo.controllerDto.SearchParamManagerModel;
import com.tvo.dto.ParamManagerDto;
import com.tvo.model.ParamManager;
import com.tvo.request.CreateParamManagerRequest;
import com.tvo.request.UpdateParamManagerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
public interface ParamManagerService {
	List<ParamManagerDto> findAll();

	ParamManager findByCode(String code);

	Page<ParamManagerDto> searchParamManager(SearchParamManagerModel searchModel, Pageable pageable);

	ParamManager update(UpdateParamManagerRequest request);

	ParamManager create(CreateParamManagerRequest request);

	Boolean delete(String code);

	List<ParamManager> saveAll(List<ParamManagerDto> paramManagerDtos);
}