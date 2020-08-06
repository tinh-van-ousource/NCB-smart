package com.tvo.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Data
public class UpdateQrService {

    private String title;

    private String serviceType;

    private String status;
}
