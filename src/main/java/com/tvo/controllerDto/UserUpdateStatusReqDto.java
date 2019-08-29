package com.tvo.controllerDto;

import com.tvo.validator.StatusActivateConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UserUpdateStatusReqDto {

    private String username;

    @StatusActivateConstraint
    @NotBlank
    private String status;

}
