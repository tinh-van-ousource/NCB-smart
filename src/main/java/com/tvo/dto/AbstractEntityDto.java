/**
 * 
 */
package com.tvo.dto;

import com.tvo.common.AppConstant;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
