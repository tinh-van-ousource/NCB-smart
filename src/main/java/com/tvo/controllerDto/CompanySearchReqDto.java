package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CompanySearchReqDto {

    private String compCode;

    private String compName;
    
    private String mcn;
    
    private String mp;

}
