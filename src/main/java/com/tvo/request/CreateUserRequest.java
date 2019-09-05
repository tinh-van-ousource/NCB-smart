package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

	@NotNull
	private String userName;

	@NotNull
	private String fullName;

	@NotNull
	private String password;

	@NotNull
	private String branchCode;

	@NotNull
	private String transactionCode;

	@NotNull
    private Long roleId;

	@NotNull
	private String email;

	private String phone;

	@NotNull
	private String userCode;
}
