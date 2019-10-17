package com.tvo.controllerDto;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 13, 2019
 */
@Getter
@Setter
public class UpdateNcbBannerRequest {
    private Long id;
    private String bannerCode;

    private String bannerName;

    private String linkImg;


    private String linkUrlEn;

    private String actionScreen;
    
    private String status;
    
    @JsonFormat(pattern = "yyyy/M/d")
    private LocalDateTime createdDate;
    
    @JsonFormat(pattern = "yyyy/M/d")
    private Date scheduleStart;
    
    @JsonFormat(pattern = "yyyy/M/d")
    private Date scheduleEnd;
    
    private Character oneTimeShow;

}
