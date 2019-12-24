package com.tvo.service;

import com.tvo.controllerDto.ParConfigUpdateCreditCardNumberReqDto;
import com.tvo.controllerDto.ParConfigUpdateOtherParamReqDto;
import com.tvo.controllerDto.ParConfigUpdateReissueCardReasonReqDto;
import com.tvo.dto.ParConfigResDto;

import java.util.List;

public interface ParConfigService {

	ParConfigResDto createCreditCardNumber(ParConfigUpdateCreditCardNumberReqDto req);

    Boolean checkDuplicateCreditCardNumber(String value);

    List<ParConfigResDto> getCreditCardNumber();

    ParConfigResDto updateCreditCardNumber(String oldValue, String newValue);

    Boolean deleteCreditCardNumber(String value);

    ParConfigResDto saveOrUpdateReissueCardReason(ParConfigUpdateReissueCardReasonReqDto req);

    List<ParConfigResDto> getReissueCardReason();

    Boolean checkDuplicateReissueCardReason(String code);

    Boolean deleteReissueCardReason(String code);


    ParConfigResDto updateOtherParam(ParConfigUpdateOtherParamReqDto req);

    List<ParConfigResDto> getOtherParam();

    Boolean checkDuplicateOtherParam(String param, String code);

    Boolean deleteOtherParam(String param, String code);
}
