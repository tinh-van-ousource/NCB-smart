/**
 * 
 */
package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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
