package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author NgocDC
 */
@Setter
@Getter
public class UserChangePasswordReqDto {

    private String username; // username

    private String oldPassword; // mat khau cu

    private String newPassword; // mat khau moi

}
