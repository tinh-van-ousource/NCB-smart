package com.tvo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse {
	private String fileName;
	private String fileUploadUri;
	private String fileType;
	private long size;
}
