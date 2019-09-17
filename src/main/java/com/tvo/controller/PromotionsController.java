package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.NotifyDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdateNotifyRequest;
import com.tvo.request.UpdatePromotionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.PromotionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/promotions")
public class PromotionsController {
	@Autowired 	
	PromotionsServiceImpl promotionsService; 
	
	@GetMapping(value = "/search")
	public ResponeData<Page<PromotionsDto>> search(@ModelAttribute SearchPromotion searchPromotion, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<PromotionsDto> PromotionsDtos = promotionsService.searchPromotion(searchPromotion, pageable);
		return new ResponeData<Page<PromotionsDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, PromotionsDtos) ;
	}
	@PostMapping(value = "create")
	public ResponeData<PromotionsDto> create(@RequestBody CreatePromotionsRequest request) {
		
		PromotionsDto promotionDto = promotionsService.create(request);
		if (promotionDto == null) {
			return new ResponeData<PromotionsDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<PromotionsDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, promotionDto);
	}

	@PutMapping(value = "update")
	public ResponeData<PromotionsDto> update(@RequestBody UpdatePromotionRequest request) {
		PromotionsDto promotionDto = promotionsService.update(request);
		if (promotionDto == null) {
			return new ResponeData<PromotionsDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<PromotionsDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, promotionDto);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = promotionsService.delete(id);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
	@GetMapping(value = "/detail")
    public ResponeData<PromotionsDto> detail(@RequestParam Long id) {
		PromotionsDto dto = promotionsService.detail(id);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }
}
