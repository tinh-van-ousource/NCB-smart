package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderResDto {
	private String id;
	
	private String providerCode;
	
	private String providerName;
	
	private String serviceCode;

	private String partner;
	
	private String status;
}
