package com.tvo.request;

import lombok.Data;

/**
 * @author Nguyen Hoang Long on 9/29/2020
 * @created 9/29/2020
 * @project NCB-smart
 */
@Data
public class UpdateVersionAppRequest {

    private String code;

    private String value;

    private String name;

    private String type;

    private String sort;

    private String description;
}
