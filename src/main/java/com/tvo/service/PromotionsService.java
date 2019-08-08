package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.searchPromotion;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.dto.UserDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.CreatePromotionsRequest;

public interface PromotionsService {
	public Page<PromotionsDto> searchPromotion(searchPromotion searchPromotion, Pageable pageable);
	public PromotionsDto createPromotions(CreatePromotionsRequest request) ;
	public PromotionsDto update(PromotionsDto promotionsDto);
}
