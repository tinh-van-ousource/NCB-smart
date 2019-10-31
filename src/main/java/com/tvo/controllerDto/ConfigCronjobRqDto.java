package com.tvo.controllerDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigCronjobRqDto {

    private String timeStart;

    private String timeEnd;

    private String createdBy;
}
