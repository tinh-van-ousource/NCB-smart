package com.tvo.controllerDto;

import com.tvo.validator.StatusActivateConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateStatusReqDto {

    private String username;

    @StatusActivateConstraint
    private String status;

}
