package com.tvo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dto.MbProvisionDto;
import com.tvo.model.MbProvision;
import com.tvo.request.CreateMbProvisionRequest;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
public interface MbProvisionService {
	public List<MbProvisionDto> findAll();

	public MbProvision findById(String id);

	public Page<MbProvisionDto> searchMbProvision(SearchMbProvisionModel searchModel, Pageable pageable);

	public MbProvision update(CreateMbProvisionRequest request);

	public MbProvision create(CreateMbProvisionRequest request);

	public String delete(String id);
}
