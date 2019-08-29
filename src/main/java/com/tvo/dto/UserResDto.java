/**
 * 
 */
package com.tvo.dto;

import com.tvo.common.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Ace
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResDto {
	
	private Long userId;

	private String userName;
	
	private String fullName;	

	private String password;
	
	private String branchCode;
	
	private String transactionCode;
	
	private String passChange;
	
    private RoleDto role;
	
	private String email;
	
	private String phone;
	
	private Integer countLoginFail;
	
	private String userCode;
	
	private String token;

	private Long loginCount;

	private Date createdDate;

	private String updatedBy;

	private String status;
	
}
