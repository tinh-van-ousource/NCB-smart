package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateReqDto {

    private String username;

    private String branchCode;

    private String transactionCode;

    private String fullName;

    private String email;

    private String phone;
    private Long roleId;

}
