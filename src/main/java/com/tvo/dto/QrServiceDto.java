package com.tvo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Data
public class QrServiceDto {
    private Long id;

    private String title;

    private String serviceType;

    private String status;

    private String deletedAt;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
