package com.tvo.service;

import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dto.MbProvisionDto;
import com.tvo.request.CreateMbProvisionRequest;
import com.tvo.request.UpdateMbProvisionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
public interface MbProvisionService {
	public List<MbProvisionDto> findAll();

	public MbProvisionDto findById(Long id);

	public Page<MbProvisionDto> searchMbProvision(SearchMbProvisionModel searchModel, Pageable pageable);

	public MbProvisionDto update(UpdateMbProvisionRequest request);

	public MbProvisionDto create(CreateMbProvisionRequest request);

	public Boolean delete(Long id);
}
