package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRegisterLogResDto {
    private LocalDateTime datetime;

    private String userId;

    private String newValue;

}
