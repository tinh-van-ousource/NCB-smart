package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchNcbGuidelineModel;
import com.tvo.dto.NcbGuidelineDto;
import com.tvo.request.CreateNcbGuidelineRequest;
import com.tvo.request.UpdateNcbGuidelineRequest;

public interface NcbGuidelineService {
	public List<NcbGuidelineDto> findAll();

	public NcbGuidelineDto findById(Long id);

	public Page<NcbGuidelineDto> searchNcbGuideline(SearchNcbGuidelineModel searchModel, Pageable pageable);

	public NcbGuidelineDto update(UpdateNcbGuidelineRequest request);

	public NcbGuidelineDto create(CreateNcbGuidelineRequest request);

	public String delete(Long id);
}
