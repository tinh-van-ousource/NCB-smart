package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author NgocDC
 */
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

    private List<ServiceRegisterLogResDto> serviceRegisterLogResDtoList;

}
