package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRegisterGetDetailResDto {
    private Long id;

    private String customerName;

    private String idCard;

    private String phone;

    private String service;

    private String compName;

    private String status;
    
    private String compCode;

    private List<ServiceRegisterLogResDto> serviceRegisterLogResDtoList;

}
