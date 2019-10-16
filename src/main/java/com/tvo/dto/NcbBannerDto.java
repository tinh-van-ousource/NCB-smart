package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 13, 2019
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NcbBannerDto {
    private String id;

    private String bannerCode;

    private String bannerName;

    private String linkImg;

    private String linkUrlVn;

    private String linkUrlEn;

    private String status;

    @JsonFormat(pattern = "yyyy/M/d")
    private LocalDateTime createdDate;

    private String actionScreen;
    
    @JsonFormat(pattern = "yyyy/M/d")
    private Date scheduleStart;
    
    @JsonFormat(pattern = "yyyy/M/d")
    private Date scheduleEnd;
    
    private Boolean oneTimeShow;

}
