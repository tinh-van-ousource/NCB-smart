package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParConfigResDto {
	private Long id;
    private String param;

    private String value;

    private String code;

    private String note;

}
