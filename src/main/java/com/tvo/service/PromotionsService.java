package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.CreatePromotionsDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.DeletePromotionsRequest;
import com.tvo.request.UpdatePromotionRequest;

public interface PromotionsService {

	Page<PromotionsDto> searchPromotion(SearchPromotion searchPromotion, Pageable pageable);

	CreatePromotionsDto create(CreatePromotionsRequest request);

	public PromotionsDto update(UpdatePromotionRequest request);

	public PromotionsDto delete(DeletePromotionsRequest deletePromotionRequest);

	public PromotionsDto detail(String proCode);

}
