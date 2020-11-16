package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchPromotionDetailsDto;
import com.tvo.dto.PromotionDetailsDto;
import com.tvo.request.CreateOrUpdatePromotionDetailsRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.PromotionDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author thanglt on 11/16/2020
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/promotion-details")
public class PromotionDetailsController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PromotionDetailsService promotionDetailsService;

    @GetMapping(value = "")
    public ResponeData<Page<PromotionDetailsDto>> search(@ModelAttribute SearchPromotionDetailsDto search,
                                                         @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return promotionDetailsService.search(search, pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PostMapping(value = "")
    public ResponeData<PromotionDetailsDto> create(@RequestBody CreateOrUpdatePromotionDetailsRequest request) {
        try {
            return promotionDetailsService.create(request);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<PromotionDetailsDto> update(@PathVariable Long id,
                                                   @RequestBody CreateOrUpdatePromotionDetailsRequest request) {
        try {
            return promotionDetailsService.update(id, request);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponeData<PromotionDetailsDto> detail(@PathVariable Long id) {
        try {
            return promotionDetailsService.detail(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/promotion-code/{promotionCode}")
    public ResponeData<PromotionDetailsDto> detailByPromotionCode(@PathVariable String promotionCode) {
        try {
            return promotionDetailsService.findByPromotionCode(promotionCode);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return promotionDetailsService.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }
}
