package com.tvo.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

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

    private LocalDateTime deletedAt;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
