package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMbProvisionRequest {
    private String provisionName;
    
    private String provisionCode;

    private String provisionLink;

}
