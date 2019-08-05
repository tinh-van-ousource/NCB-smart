package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProviderRequest {
    private String providerId;
	
	private String providerCode;
	
	private String providerName;
	
	private String serviceCode;

	private String providerPartner;
	
	private String providerStatus;
}
