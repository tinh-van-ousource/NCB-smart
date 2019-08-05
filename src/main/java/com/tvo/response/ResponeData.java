/**
 * 
 */
package com.tvo.response;

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
public class ResponeData<T> {
	private String code;
	private String description;
	private T body;
	
	public ResponeData(String code, String description) {
        this.code = code;
        this.description = description;
    }
	public ResponeData(T iSaving) {
		this.body = iSaving;
	}
}
