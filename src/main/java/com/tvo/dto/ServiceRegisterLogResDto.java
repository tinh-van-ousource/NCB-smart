package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRegisterLogResDto {
    @JsonProperty("datetime")
    private LocalDateTime datetime;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("new_value")
    private String newValue;

}
