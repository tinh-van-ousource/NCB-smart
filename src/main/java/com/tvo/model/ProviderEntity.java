package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PROVIDER_MBAPP")
@Getter
@Setter
@NoArgsConstructor
public class ProviderEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROVIDER_ID_SQ_GENERATOR")
    @SequenceGenerator(sequenceName = "PROVIDER_MBAPP_SEQ", allocationSize = 1, name = "PROVIDER_ID_SQ_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Id
    @Column(name = "PROVIDER_CODE")
    private String providerCode;

    @Column(name = "PROVIDER_NAME")
    private String providerName;

    @Column(name = "SERVICE_CODE")
    private String serviceCode;

    @Column(name = "PARTNER")
    private String partner;

    @Column(name = "STATUS")
    private String status;

}
