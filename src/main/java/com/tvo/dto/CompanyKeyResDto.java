package com.tvo.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyKeyResDto {
	
	@NotNull
	private String compCode;

    private String mcn;

    private String mp;
	
}
