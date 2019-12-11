package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ProviderUpdateReqDto {
  
    private Long id;

    private String providerCode;

    private String providerName;

    private String serviceCode;

    private String partner;

    private String status;
}
