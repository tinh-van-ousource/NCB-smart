package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.dto.QrMerchantDto;
import com.tvo.request.QrMerchantRequest;
import com.tvo.request.QrMerchantCreateRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.QrMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author thanglt on 8/31/2020
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/qr-merchants")
public class QrMerchantController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QrMerchantService qrMerchantService;

    @GetMapping(value = "")
    public ResponeData<Page<QrMerchantDto>> search(@RequestParam(required = false) String search,
                                                   @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return qrMerchantService.search(search, pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PostMapping(value = "")
    public ResponeData<Boolean> create(@RequestBody QrMerchantCreateRequest qrMerchantCreateRequest) {
        try {
            return qrMerchantService.create(qrMerchantCreateRequest);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<QrMerchantDto> update(@PathVariable Long id,
                                             @RequestBody QrMerchantRequest qrMerchantRequest) {
        try {
            return qrMerchantService.update(id, qrMerchantRequest);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponeData<QrMerchantDto> detail(@PathVariable Long id) {
        try {
            return qrMerchantService.detail(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return qrMerchantService.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }
}
