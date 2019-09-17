package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.PromotionsDto;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdatePromotionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.PromotionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/promotions")
public class PromotionsController {

    @Autowired
    PromotionsServiceImpl promotionsService;

    @GetMapping(value = "/search")
    public ResponeData<Page<PromotionsDto>> search(@ModelAttribute SearchPromotion searchPromotion, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<PromotionsDto> PromotionsDtos = promotionsService.searchPromotion(searchPromotion, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, PromotionsDtos);
    }

    @PostMapping(value = "/create")
    public ResponeData<List<PromotionsDto>> create(@RequestBody CreatePromotionsRequest request) {
        List<PromotionsDto> promotionDtoList = promotionsService.create(request);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, promotionDtoList);
    }

    @PutMapping(value = "/update")
    public ResponeData<PromotionsDto> update(@RequestBody UpdatePromotionRequest request) {
        PromotionsDto promotionDto = promotionsService.update(request);
        if (promotionDto != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, promotionDto);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @DeleteMapping(value = "/delete")
    public ResponeData<Boolean> delete(@RequestParam Long id) {
        boolean deleteFlag = promotionsService.delete(id);
        if (deleteFlag) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
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
