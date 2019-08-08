package com.tvo.controllerDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ServiceRegisterReqDto {

    private String compCode;

    private String idCard;

    private Integer type;

    @Min(1)
    @Max(4)
    private Integer service;

    @Min(1)
    @Max(3)
    private Integer status;

    private String fromDate;

    private String toDate;

    @Positive
    @Min(1)
    @NotNull
    private Integer page;

    @Positive
    @NotNull
    private Integer size;

    public String getCompCode() {
        return compCode;
    }

    public void setComp_code(String compCode) {
        this.compCode = compCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setId_card(String idCard) {
        this.idCard = idCard;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFrom_date(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setTo_date(String toDate) {
        this.toDate = toDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
