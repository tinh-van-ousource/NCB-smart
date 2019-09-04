package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tvo.controllerDto.SearchNcbQAModel;
import com.tvo.controllerDto.SearchNotify;
import com.tvo.dto.NcbQADto;
import com.tvo.dto.NotifyDto;
import com.tvo.request.CreateNcbQARequest;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.UpdateNcbQARequest;
import com.tvo.request.UpdateNotifyRequest;

@Service
public interface NotifyService {
	public Page<NotifyDto> search(SearchNotify searchNotify, Pageable pageable);
	
	public NotifyDto create(CreateNotifyRequest request);
	
	public NotifyDto update(UpdateNotifyRequest request);
	
	public Boolean delete(Long id);
}
