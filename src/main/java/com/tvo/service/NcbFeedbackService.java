package com.tvo.service;

import com.tvo.controllerDto.SearchNcbFeedbackModel;
import com.tvo.dto.NcbFeedbackDto;
import com.tvo.request.CreateNcbFeedbackRequest;
import com.tvo.request.UpdateNcbFeedbackRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NcbFeedbackService {
	public List<NcbFeedbackDto> findAll();

	public NcbFeedbackDto findById(Long id);

	public Page<NcbFeedbackDto> searchNcbFeedback(SearchNcbFeedbackModel searchModel, Pageable pageable);

	public NcbFeedbackDto create(CreateNcbFeedbackRequest request);

	public NcbFeedbackDto update(UpdateNcbFeedbackRequest request);

	public Boolean delete(Long id);
}
