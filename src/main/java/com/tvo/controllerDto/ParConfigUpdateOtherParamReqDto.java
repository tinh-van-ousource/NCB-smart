package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ParConfigUpdateOtherParamReqDto {

    @NotBlank
    private String param;

    @NotBlank
    private String code;

    @NotBlank
    private String value;

    private String note;
}
