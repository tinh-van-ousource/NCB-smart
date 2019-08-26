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
	@SequenceGenerator(sequenceName = "AI_CMS_ROLE_SQ", name = "ai_cms_role_sq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_ROLE_SQ")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID",insertable = false)
	private Long roleId;

	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	

}
