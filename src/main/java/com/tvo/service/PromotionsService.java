package com.tvo.service;

import com.tvo.controllerDto.searchPromotion;
import com.tvo.dto.PromotionsDto;
import com.tvo.request.CreatePromotionsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionsService {
	public Page<PromotionsDto> searchPromotion(searchPromotion searchPromotion, Pageable pageable);
	public PromotionsDto createPromotions(CreatePromotionsRequest request) ;
	public PromotionsDto update(PromotionsDto promotionsDto);
}
