package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchReferralCodePartnerDto;
import com.tvo.dto.ReferralCodePartnerDto;
import com.tvo.request.CreateOrUpdateReferralCodePartnerRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.ReferralCodePartnerService;
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
@RequestMapping(value = "/referral-code-partners")
public class ReferralCodePartnerController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReferralCodePartnerService referralCodePartnerService;

    @GetMapping(value = "")
    public ResponeData<Page<ReferralCodePartnerDto>> search(@ModelAttribute SearchReferralCodePartnerDto search,
                                                            @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return referralCodePartnerService.search(search, pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PostMapping(value = "")
    public ResponeData<ReferralCodePartnerDto> create(@RequestBody CreateOrUpdateReferralCodePartnerRequest request) {
        try {
            return referralCodePartnerService.create(request);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<ReferralCodePartnerDto> update(@PathVariable Long id,
                                                      @RequestBody CreateOrUpdateReferralCodePartnerRequest request) {
        try {
            return referralCodePartnerService.update(id, request);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponeData<ReferralCodePartnerDto> detail(@PathVariable Long id) {
        try {
            return referralCodePartnerService.detail(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/partner-code/{partnerCode}")
    public ResponeData<ReferralCodePartnerDto> detail(@PathVariable String partnerCode) {
        try {
            return referralCodePartnerService.findByReferralCode(partnerCode);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return referralCodePartnerService.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }
}
