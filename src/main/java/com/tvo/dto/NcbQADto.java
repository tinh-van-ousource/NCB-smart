package com.tvo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NcbQADto {

	private String id;

	private String productCode;

	private String productName;

	private String question;

	private String answer;
	
	private String status;

	private String createdDate;
}
