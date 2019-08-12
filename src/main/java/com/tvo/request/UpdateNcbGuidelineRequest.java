package com.tvo.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UpdateNcbGuidelineRequest {
	@NonNull
	private Long id;

	private String serviceId;

	private String content;

	private String status;
}
