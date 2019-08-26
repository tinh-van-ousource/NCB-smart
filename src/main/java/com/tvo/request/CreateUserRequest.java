/**
 * 
 */
package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Ace
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
	private Long userId;

	private String userName;
	
	private String fullName;
	
	private String password;
	
	private String branchCode;
	
	private String posCode;
	
    private Long roleId;
	
	private String email;
	
	@Size(min = 10, max = 11)
	@Pattern(regexp = "\\d")
	private String phone;
	
	private String userCode;
}
