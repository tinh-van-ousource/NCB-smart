package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchPrdPromotion;
import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dto.*;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdatePromotionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.PrdPromotionService;
import com.tvo.service.PromotionsService;
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
    private PromotionsService promotionsService;

    @Autowired
    private PrdPromotionService prdPromotionService;

    @GetMapping(value = "/search")
    public ResponeData<Page<PromotionsDto>> search(@ModelAttribute SearchPromotion searchPromotion, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<PromotionsDto> promotionsDtos = promotionsService.searchPromotion(searchPromotion, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, promotionsDtos);
    }
    
    @PostMapping(value = "/create")
    public ResponeData<CreatePromotionsDto> create(@RequestBody CreatePromotionsRequest request) {
        CreatePromotionsDto dto = promotionsService.create(request);
        if(dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
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
    public ResponeData<Boolean> delete(@RequestParam String proCode) {
        Boolean isDetele = promotionsService.delete(proCode);
        if (isDetele == false) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
    }

    @GetMapping(value = "/detail")
    public ResponeData<PromotionsDto> detail(@RequestParam String proCode) {
        PromotionsDto dto = promotionsService.detail(proCode);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @GetMapping(value = "/fee/search")
    public ResponeData<Page<PrdPromotionDto>> searchFee(@ModelAttribute SearchPrdPromotion searchPrdPromotion, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<PrdPromotionDto> prdPromotionDtos = prdPromotionService.search(searchPrdPromotion, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, prdPromotionDtos);
    }

    @PostMapping(value = "/fee/create")
    public ResponeData<PrdPromotionDto> createFee(@RequestBody CreatePrdPromotionRqDto request) {
        PrdPromotionDto dto = prdPromotionService.create(request);
        if(dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @PutMapping(value = "/fee/update")
    public ResponeData<PrdPromotionDto> updateFee(@RequestBody PrdPromotionRq request) {
        PrdPromotionDto promotionDto = prdPromotionService.update(request);
        if (promotionDto != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, promotionDto);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @DeleteMapping(value = "/fee/delete")
    public ResponeData<Boolean> deleteFee(@RequestParam Long prdPromotionId) {
        Boolean isDetele = prdPromotionService.delete(prdPromotionId);
        if (isDetele == false) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
    }

    @GetMapping(value = "/fee/detail")
    public ResponeData<PrdPromotionDto> detailFee(@RequestParam Long prdPromotionId) {
        PrdPromotionDto dto = prdPromotionService.detail(prdPromotionId);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @GetMapping(value = "/add-function/getAllProCode")
    public ResponeData<List<String>> getProCode() {
        List<String> proCodes = promotionsService.getDistinctByProCode() ;
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, proCodes);
    }
}
