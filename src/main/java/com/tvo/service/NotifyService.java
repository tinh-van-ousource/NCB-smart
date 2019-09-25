package com.tvo.service;

import com.tvo.controllerDto.CreateNotifyDto;
import com.tvo.controllerDto.SearchNotify;
import com.tvo.dto.NotifyDto;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.UpdateNotifyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface NotifyService {
	public Page<NotifyDto> search(SearchNotify searchNotify, Pageable pageable);
	
	public CreateNotifyDto create(CreateNotifyRequest request);
	
	public NotifyDto update(UpdateNotifyRequest request);
	
	public Boolean delete(String msg_Code);
	
	public NotifyDto detail(String msg_Code);
}
