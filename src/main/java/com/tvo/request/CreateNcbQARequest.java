package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNcbQARequest {

	private String productCode;

	private String productName;

	private String question;

	private String answer;

}
