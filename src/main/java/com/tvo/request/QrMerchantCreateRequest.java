package com.tvo.request;

import lombok.Data;

import java.util.List;

/**
 * @author thanglt on 8/31/2020
 * @version 1.0
 */
@Data
public class QrMerchantCreateRequest {
    private String name;
    private String address;
    List<QrMerchantRequest> qrMerchants;
}
