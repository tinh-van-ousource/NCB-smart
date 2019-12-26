package com.tvo.controllerDto;

import com.tvo.dto.CompanyKeyResDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyUpdateReqDto {

	private CompanyKeyResDto key;
	
    private String compName;

    // private String address;

    private String dao;
}
