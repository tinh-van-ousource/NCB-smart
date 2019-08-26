 	/**
 * 
 */
package com.tvo.dto;

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
public class CityDto {
		private Long cityId;
	
		private String cityCode;
	
		private String cityName;
	
		private String status;
	
		private Date createdDate;
}
