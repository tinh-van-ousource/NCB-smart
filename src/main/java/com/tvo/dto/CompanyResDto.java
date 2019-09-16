package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResDto {

    private String compCode;

    private String compName;

    private String address;

    private String dao;

    private String mcn;

    private String mp;

}
