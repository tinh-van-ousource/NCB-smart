/**
 * 
 */
package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Ace
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends AbstractEntityDto {
	
	private Long userId;

	private String userName;
	
	private String fullName;	

	private String password;
	
	private String branchCode;
	
	private String posCode;
	
	private String passChange;
	
    private RoleDto role;
	
	private String email;
	
	@Size(min = 10, max = 11)
	@Pattern(regexp = "\\d")
	private String phone;
	
	private Integer countLoginFail;
	
	private String userCode;
	
	private String token;
	
}
