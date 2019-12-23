package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CompanyUpdateReqDto {

    @NotNull
    private String compCode;

    private String compName;

//    private String address;

    private String dao;

//    private String mcn;

    private String mp;
}
