package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProviderCreateReqDto {
    private String providerCode;

    private String providerName;

    private String serviceCode;

    private String partner;
}
