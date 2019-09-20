 	/**
 * 
 */
package com.tvo.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
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
		@JsonFormat(pattern = "yyyy/M/d")
		private Date createdDate;
}
