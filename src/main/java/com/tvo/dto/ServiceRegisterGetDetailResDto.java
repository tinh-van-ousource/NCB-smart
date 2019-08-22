package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("id_card")
    private String idCard;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("service")
    private String service;

    @JsonProperty("comp_name")
    private String compName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("log")
    private List<ServiceRegisterLogResDto> serviceRegisterLogResDtoList;

}