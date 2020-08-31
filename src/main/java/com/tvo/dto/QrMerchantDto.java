package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author thanglt on 8/31/2020
 * @version 1.0
 */
@Data
public class QrMerchantDto {
    private Long id;

    private String name;

    private String address;

    private Date createdAt;

    private String createdBy;
}
