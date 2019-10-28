package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.CreatePromotionsDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdatePromotionRequest;

public interface PromotionsService {

	Page<PromotionsDto> searchPromotion(SearchPromotion searchPromotion, Pageable pageable);

	CreatePromotionsDto create(CreatePromotionsRequest request);

	PromotionsDto update(UpdatePromotionRequest request);

	Boolean delete(String prdPromotionId);

	PromotionsDto detail(String proCode);

}
