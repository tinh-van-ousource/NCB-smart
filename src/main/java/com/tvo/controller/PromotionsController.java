package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.PromotionsDto;
import com.tvo.response.ResponeData;
import com.tvo.service.PromotionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/promotions")
public class PromotionsController {
	@Autowired 	
	PromotionsServiceImpl promotionsService; 
	@GetMapping(value = "/searchPromotions")
	public ResponeData<Page<PromotionsDto>> searchFunction(@ModelAttribute SearchPromotion searchPromotion, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<PromotionsDto> PromotionsDtos = promotionsService.searchPromotion(searchPromotion, pageable);
		return new ResponeData<Page<PromotionsDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, PromotionsDtos) ;
	}
}
