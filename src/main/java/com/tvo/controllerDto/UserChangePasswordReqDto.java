package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserChangePasswordReqDto {

    private String username; // username

    private String oldPassword; // mat khau cu

    private String newPassword; // mat khau moi

}
