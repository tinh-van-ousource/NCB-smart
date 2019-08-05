/**
 * 
 */
package com.tvo.dto;

import java.util.Date;

import com.tvo.common.AppConstant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ace
 *
 */
@Getter
@Setter
public class AbstractEntityDto {
	private Date createdDate;

	private Long createdBy;
	
	private AppConstant.Status  status;
}
