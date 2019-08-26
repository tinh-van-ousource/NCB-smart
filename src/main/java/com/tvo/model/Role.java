/**
 * 
 */
package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Ace
 *
 */
@Entity
@Table(name = "CMS_ROLE")
@Getter
@Setter
public class Role extends AbstractEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8912960400201534248L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_ROLE_SQ")
	@SequenceGenerator(sequenceName = "AI_CMS_ROLE_SQ", allocationSize = 1, name = "AI_CMS_ROLE_SQ")
	@Column(name = "ROLE_ID",insertable = false)
	private Long roleId;

	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	

}
