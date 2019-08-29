/**
 * 
 */
package com.tvo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ace
 *
 */
@Getter
@Setter
public class RoleDto {

	private Long roleId;

	private String roleName;
	
	private String description;

	private String status;

}
