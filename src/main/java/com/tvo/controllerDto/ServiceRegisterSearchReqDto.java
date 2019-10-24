package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class ServiceRegisterSearchReqDto {

    private String compCode; // ma chi nhanh - phong giao dich

    private String idCard; // cmnd - ho chieu

    private String type; // phan he

    private String service; // dich vu

    private String status; // trang thai

    private String fromDate; // tu ngay

    private String toDate; // den ngay

    @Positive
    @Min(1)
    @NotNull
    private Integer page;

    @Positive
    @Min(1)
    @NotNull
    private Integer size;

}
