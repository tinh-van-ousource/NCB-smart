package com.tvo.controllerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCity {
	private String proId;

	private String shrtName;
	private String status;
}
