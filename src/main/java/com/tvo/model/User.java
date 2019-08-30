/**
 *
 */
package com.tvo.model;

import com.tvo.common.DateTimeUtil;
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
@Table(name = "CMS_USER")
@Setter
@Getter
public class User implements Serializable {

    private static final long serialVersionUID = 8922785714798336673L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SQ_GENERATOR")
    @SequenceGenerator(sequenceName = "AI_CMS_USER_SQ", allocationSize = 1, name = "USER_ID_SQ_GENERATOR")
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "TRANSACTION_CODE")
    private String transactionCode;

    @Column(name = "PASSCHANGE")
    private String passChange;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "COUNT_LOGIN_FAIL")
    private Integer countLoginFail;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "CREATED_DATE")
    private Date createdDate = DateTimeUtil.getNow();

    @Column(name = "CREATED_USER")
    private String updatedBy;

    @Column(name = "STATUS")
    private String  status;
}
