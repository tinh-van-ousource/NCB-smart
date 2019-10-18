package com.tvo.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "SERVICE_MBAPP")
@NoArgsConstructor
@Setter
@Getter
public class ServiceMbapp implements Serializable {
	 @Id
	@Column(name = "SERVICE_CODE")
    private String serviceCode;

	@Column(name = "SERVICE_NAME")
    private String serviceName;

	@Column(name = "SERVICE_TYPE")
    private String serviceType;

	
}
