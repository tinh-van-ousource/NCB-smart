package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 9, 2019
 */
@Entity
@Table(name = "NCB_QA")
@Getter
@Setter
@NoArgsConstructor
public class NcbQA  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_QA")
	@SequenceGenerator(sequenceName = "NCB_QA_SEQ", allocationSize = 1, name = "ID_QA")
	private Long id;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "QUESTION")
	private String question;

	@Column(name = "ANSWER")
	private String answer;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "STATUS")
	private String status;

}
