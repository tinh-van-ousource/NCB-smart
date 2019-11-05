package com.tvo.controllerDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigCronjobRqDto {

    @NonNull
    private String timeStart;

    @NonNull
    private String timeEnd;

    private String createdBy;
}
