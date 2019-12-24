package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ParConfigUpdateReissueCardReasonReqDto {
	
    private String value;

    @NotBlank
    private String code;
}
