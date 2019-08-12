package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchNcbFeedbackModel;
import com.tvo.dto.NcbFeedbackDto;
import com.tvo.request.CreateNcbFeedbackRequest;
import com.tvo.request.UpdateNcbFeedbackRequest;

public interface NcbFeedbackService {
	public List<NcbFeedbackDto> findAll();

	public NcbFeedbackDto findById(Long id);

	public Page<NcbFeedbackDto> searchNcbFeedback(SearchNcbFeedbackModel searchModel, Pageable pageable);

	public NcbFeedbackDto create(CreateNcbFeedbackRequest request);

	public NcbFeedbackDto update(UpdateNcbFeedbackRequest request);

	public String delete(Long id);
}
