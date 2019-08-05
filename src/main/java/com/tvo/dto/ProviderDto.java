package com.tvo.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ace
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDto {
	private String providerId;
	
	private String providerCode;
	
	private String providerName;
	
	private String serviceCode;

	private String providerPartner;
	
	private String providerStatus;
}
