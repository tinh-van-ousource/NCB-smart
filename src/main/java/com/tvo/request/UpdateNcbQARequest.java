package com.tvo.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UpdateNcbQARequest {
	@NonNull
	private Long id;

	private String productCode;

	private String productName;

	private String question;

	private String answer;

	private String status;

}
