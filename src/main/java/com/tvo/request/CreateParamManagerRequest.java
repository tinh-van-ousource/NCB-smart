package com.tvo.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreateParamManagerRequest {

	private String name;

	private String value;

	private String description;
}