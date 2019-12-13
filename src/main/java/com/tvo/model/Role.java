package com.tvo.model;

import com.tvo.common.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CMS_ROLE")
@Getter
@Setter
public class Role implements Serializable {

    private static final long serialVersionUID = 8912960400201534248L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_ROLE_SQ_GENERATOR")
    @SequenceGenerator(sequenceName = "AI_CMS_ROLE_SQ", allocationSize = 1, name = "AI_CMS_ROLE_SQ_GENERATOR")
    @Column(name = "ROLE_ID", insertable = false)
    private Long roleId;

    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BRIEF_DESCRIPTION")
    private String briefDescription;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate = DateTimeUtil.getNow();

    @Column(name = "CREATED_USER")
    private String updatedBy;

    @Column(name = "STATUS")
    private String status;

}
