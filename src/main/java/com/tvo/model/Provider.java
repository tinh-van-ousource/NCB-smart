package com.tvo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROVIDER_MBAPP")
@Getter
@Setter
@Data
public class Provider {
	@Id
	

	@Column(name = "ID")
	private String providerId;
	
	@Column(name = "PROVIDER_CODE")
	private String providerCode;
	
	@Column(name = "PROVIDER_NAME")
	private String providerName;
	
	@Column(name = "SERVICE_CODE")
	private String serviceCode;

	@Column(name = "PARTNER")
	private String providerPartner;
	
	@Column(name = "STATUS")
	private String providerStatus;
	
}
