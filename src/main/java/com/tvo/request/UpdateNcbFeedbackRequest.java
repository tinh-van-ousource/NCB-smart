package com.tvo.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateNcbFeedbackRequest {
	@NonNull
	private Long id;

	@Size(max = 15)
	private String productCode;

	private String productName;

	private String type;

	private String phone;

	private String email;

	private String status;

	private String name;

	private String address;

	private String description;
}
