package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class UserUpdateReqDto {

    private String username;

    private String branchCode;

    private String posCode;

    private String fullName;

    private String email;

    private String phone;

}
