package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ParConfigUpdateCreditCardNumberReqDto;
import com.tvo.controllerDto.ParConfigUpdateOtherParamReqDto;
import com.tvo.controllerDto.ParConfigUpdateReissueCardReasonReqDto;
import com.tvo.dto.ParConfigResDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ParConfigService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.net.InetAddress;
import java.util.List;

@RestController
@RequestMapping(value = "par-config")
public class ParConfigController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
    private final ParConfigService parConfigService;

    public ParConfigController(ParConfigService parConfigService) {
        this.parConfigService = parConfigService;
    }

    /* credit-card-number START */
    @PostMapping(value = "create-credit-card-number")
    public ResponeData<ParConfigResDto> createCreditCardNumber(@RequestBody ParConfigUpdateCreditCardNumberReqDto req) {
        ParConfigResDto result = parConfigService.createCreditCardNumber(req);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @GetMapping(value = "get-credit-card-number")
    public ResponeData<List<ParConfigResDto>> getCreditCardNumber() {
        List<ParConfigResDto> result = parConfigService.getCreditCardNumber();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @GetMapping(value = "check-duplicate-credit-card-number")
    public ResponeData<Boolean> checkDuplicateCreditCardNumber(@NotBlank @RequestParam("value") String value) {
        Boolean result = parConfigService.checkDuplicateCreditCardNumber(value);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @PatchMapping(value = "update-credit-card-number")
    public ResponeData<ParConfigResDto> updateCreditCardNumber(@NotBlank @RequestParam("code") String code,
                                                               @NotBlank @RequestParam("newValue") String newValue) {
        ParConfigResDto result = parConfigService.updateCreditCardNumber(code, newValue);
        if (result != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @DeleteMapping(value = "delete-credit-card-number")
    public ResponeData<Boolean> deleteCreditCardNumber(@NotBlank @RequestParam("value") String value) {
        Boolean result = parConfigService.deleteCreditCardNumber(value);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
    /* credit-card-number END */

    /* reissue-card-reason START */
    @GetMapping(value = "get-reissue-card-reason")
    public ResponeData<List<ParConfigResDto>> getReissueCardReason() {
        List<ParConfigResDto> result = parConfigService.getReissueCardReason();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @GetMapping(value = "check-duplicate-reissue-card-reason")
    public ResponeData<Boolean> checkDuplicateReissueCardReason(@NotBlank @RequestParam("code") String code) {
        Boolean result = parConfigService.checkDuplicateReissueCardReason(code);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @PostMapping(value = "modify-reissue-card-reason")
    public ResponeData<ParConfigResDto> saveOrUpdateReissueCardReason(@RequestBody ParConfigUpdateReissueCardReasonReqDto req) {
        ParConfigResDto result = parConfigService.saveOrUpdateReissueCardReason(req);
        if (result == null) {
        	return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @DeleteMapping(value = "delete-reissue-card-reason")
    public ResponeData<Boolean> deleteReissueCardReason(@NotBlank @RequestParam("code") String code) {
        Boolean result = parConfigService.deleteReissueCardReason(code);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
    /* reissue-card-reason END */

    /* other-param START */
    @PostMapping(value = "modify-other-param-config")
    public ResponeData<ParConfigResDto> updateOtherParam(@Valid @RequestBody ParConfigUpdateOtherParamReqDto req) {
        ParConfigResDto result = parConfigService.updateOtherParam(req);
        if (result == null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @GetMapping(value = "get-other-param-config")
    public ResponeData<List<ParConfigResDto>> getOtherParam() {
        List<ParConfigResDto> result = parConfigService.getOtherParam();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @GetMapping(value = "check-duplicate-other-param-config")
    public ResponeData<Boolean> checkDuplicateOtherParam(@NotBlank @RequestParam("param") String param, @NotBlank @RequestParam("code") String code) {
        Boolean result = parConfigService.checkDuplicateOtherParam(param, code);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @DeleteMapping(value = "delete-other-param-config")
    public ResponeData<Boolean> deleteOtherParam(@NotBlank @RequestParam("param") String param, @NotBlank @RequestParam("code") String code) {
        Boolean result = parConfigService.deleteOtherParam(param, code);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
    /* other-param END */

}
