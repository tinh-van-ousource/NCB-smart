package com.tvo.controllerDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tvo.dto.CompanyKeyResDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyUpdateReqDto {

    private String compCode;

    private String mcn;

    private String mp;
	
    private String compName;

     private String address;

    private String dao;
}
