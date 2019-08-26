package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateNcbFeedbackRequest {

	@Size(max = 15)
	private String productCode;

	private String productName;

	private String type;

	private String phone;

	private String email;

	private String name;

	private String address;

	private String description;
}
