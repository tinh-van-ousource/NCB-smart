package com.tvo.service;

import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.NotifyDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdateNotifyRequest;
import com.tvo.request.UpdatePromotionRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionsService {
	public Page<PromotionsDto> searchPromotion(SearchPromotion searchPromotion, Pageable pageable);
	public PromotionsDto create(CreatePromotionsRequest request) ;
	public PromotionsDto update(UpdatePromotionRequest request);
	public Boolean delete(Long id);
	public PromotionsDto detail(Long id);
}
