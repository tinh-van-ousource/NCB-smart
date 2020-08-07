package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchQrCouponDto;
import com.tvo.dto.QrCouponDto;
import com.tvo.request.CreateQrCouponRequest;
import com.tvo.request.UpdateQrCouponRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.QrCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/qr-coupons")
public class QrCouponController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QrCouponService qrCouponService;

    @GetMapping(value = "")
    public ResponeData<Page<QrCouponDto>> search(SearchQrCouponDto searchQrCouponDto,
                                                 @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return qrCouponService.search(searchQrCouponDto, pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PostMapping(value = "")
    public ResponeData<QrCouponDto> create(@RequestBody CreateQrCouponRequest createQrCouponRequest) {
        try {
            return qrCouponService.create(createQrCouponRequest);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<QrCouponDto> update(@PathVariable Long id,
                                           @RequestBody UpdateQrCouponRequest updateQrCouponRequest) {
        try {
            return qrCouponService.update(id, updateQrCouponRequest);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponeData<QrCouponDto> detail(@PathVariable Long id) {
        try {
            return qrCouponService.detail(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return qrCouponService.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }

}
