/**
 * 
 */
package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ace
 *
 */
@Entity
@Table(name = "PROVINCE_MBAPP")
@Getter
@Setter
public class City implements Serializable {
	@Id
	@Column(name = "PRO_ID")
	private String proId;

	@Column(name = "SHRTNAME")
	private String shrtName;

	@Column(name = "STATUS")
	private String status;

}
