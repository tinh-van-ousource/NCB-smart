package com.tvo.service;

import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.NotifyDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdateNotifyRequest;
import com.tvo.request.UpdatePromotionRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionsService {

	Page<PromotionsDto> searchPromotion(SearchPromotion searchPromotion, Pageable pageable);

	List<PromotionsDto> create(CreatePromotionsRequest request) ;

	PromotionsDto update(UpdatePromotionRequest request);

	Boolean delete(Long id);

	PromotionsDto detail(Long id);

}
