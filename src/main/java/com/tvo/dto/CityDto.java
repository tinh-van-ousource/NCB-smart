 	/**
 * 
 */
package com.tvo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ace
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
		private Long cityId;
	
		private String cityCode;
	
		private String cityName;
	
		private String status;
	
		private Date createdDate;
}
