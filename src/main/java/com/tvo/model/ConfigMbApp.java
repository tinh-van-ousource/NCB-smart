package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CONFIG_MBAPP")
@Getter
@Setter
public class ConfigMbApp implements Serializable {

  private static final long serialVersionUID = 2478522583208471030L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_CONFIG_MBAPP_SQ")
  @SequenceGenerator(sequenceName = "AI_CMS_CONFIG_MBAPP_SQ", allocationSize = 1, name = "AI_CMS_CONFIG_MBAPP_SQ")
  @Column(name = "ID")
  private Long id;

  @Column(name = "CODE")
  private String code;

  @Column(name = "VALUE")
  private String value;

  @Column(name = "NAME")
  private String name;

  @Column(name = "TYPE")
  private String type;

  @Column(name = "SORT")
  private String sort;

  @Column(name = "DESCRIPTION")
  private String description;

}
