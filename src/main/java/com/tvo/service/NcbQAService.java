package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchNcbQAModel;
import com.tvo.dto.NcbQADto;
import com.tvo.request.CreateNcbQARequest;
import com.tvo.request.UpdateNcbQARequest;

public interface NcbQAService {
	public List<NcbQADto> findAll();

	public NcbQADto findById(Long id);

	public Page<NcbQADto> searchNcbQA(SearchNcbQAModel searchModel, Pageable pageable);

	public NcbQADto create(CreateNcbQARequest request);
	
	public NcbQADto update(UpdateNcbQARequest request);
	
	public String delete(Long id);
}
