/**
 * 
 */
package com.tvo.model;

import com.tvo.common.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

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
