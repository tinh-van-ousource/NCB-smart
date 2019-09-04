package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResDto {
	
	private Long userId;

	private String userName;
	
	private String fullName;	

	private String password;
	
	private String branchCode;
	
	private String transactionCode;
	
	private String passChange;
	
    private RoleResDto role;
	
	private String email;
	
	private String phone;
	
	private Integer countLoginFail;
	
	private String userCode;
	
	private String token;

	private Date createdDate;

	private String updatedBy;

	private String status;
	
}
