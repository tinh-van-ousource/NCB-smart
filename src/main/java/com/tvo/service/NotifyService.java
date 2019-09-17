package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tvo.controllerDto.CreateNotifyDto;
import com.tvo.controllerDto.SearchNotify;
import com.tvo.dto.NotifyDto;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.UpdateNotifyRequest;

@Service
public interface NotifyService {
	public Page<NotifyDto> search(SearchNotify searchNotify, Pageable pageable);
	
	public CreateNotifyDto create(CreateNotifyRequest request);
	
	public NotifyDto update(UpdateNotifyRequest request);
	
	public Boolean delete(String type);
	
	public NotifyDto detail(String bankCode);
}
