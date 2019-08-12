package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchParamManagerModel;
import com.tvo.dto.ParamManagerDto;
import com.tvo.model.ParamManager;
import com.tvo.request.CreateParamManagerRequest;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
public interface ParamManagerService {
	public List<ParamManagerDto> findAll();

	public ParamManager findByParamNo(String paramNo);

	public Page<ParamManagerDto> searchParamManager(SearchParamManagerModel searchModel, Pageable pageable);

	public ParamManager update(CreateParamManagerRequest request);

	public ParamManager create(CreateParamManagerRequest request);

	public Boolean delete(String paramNo);
}
