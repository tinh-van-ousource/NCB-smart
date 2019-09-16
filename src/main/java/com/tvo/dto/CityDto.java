 	/**
 * 
 */
package com.tvo.dto;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
		@JsonFormat(pattern = "yyyy/M/d")
		private Date createdDate;
}
