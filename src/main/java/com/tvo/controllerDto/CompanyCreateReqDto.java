package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import com.tvo.dto.CompanyKeyResDto;

@Setter
@Getter
public class CompanyCreateReqDto {

	private CompanyKeyResDto key;
	
    private String compName;

    private String address;

    private String dao;
}
