/**
 * 
 */
package com.tvo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tvo.common.DateTimeUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ace
 *
 */
@Getter
@Setter
@MappedSuperclass
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", updatable = false)
	private Date createdDate = DateTimeUtil.getNow();

	@CreatedBy
	@Column(name = "CREATED_USER", updatable = false)
	private Long createdBy;
	
	@Column(name = "STATUS")
	private String  status;
	
}
