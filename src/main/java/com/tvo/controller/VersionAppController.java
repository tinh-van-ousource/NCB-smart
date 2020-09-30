package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.dto.ConfigMbAppDto;
import com.tvo.request.CreateNotificationRequest;
import com.tvo.request.CreateVersionAppRequest;
import com.tvo.request.UpdateVersionAppRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.VersionAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nguyen Hoang Long on 9/29/2020
 * @created 9/29/2020
 * @project NCB-smart
 */
@RestController
@RequestMapping("/version-app")
public class VersionAppController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final VersionAppService versionAppService;

    public VersionAppController(VersionAppService versionAppService) {
        this.versionAppService = versionAppService;
    }

    @GetMapping("")
    public ResponeData<List<ConfigMbAppDto>> list(){
        try {
            return versionAppService.list();
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
    @PostMapping("")
    public ResponeData<ConfigMbAppDto> created(@RequestBody CreateVersionAppRequest createVersionAppRequest){
        try {
            return versionAppService.created(createVersionAppRequest);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
    @PutMapping("/{code}")
    public ResponeData<ConfigMbAppDto> updated(@PathVariable String code,@RequestBody UpdateVersionAppRequest updateVersionAppRequest){
        try {
            return versionAppService.updated(code,updateVersionAppRequest);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
    @GetMapping("/{code}")
    public ResponeData<ConfigMbAppDto> details(@PathVariable String code){
        try {
            return versionAppService.details(code);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
    @DeleteMapping("/{code}")
    public ResponeData<Boolean> deleted(@PathVariable String code){
        try {
            return versionAppService.deleted(code);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }
}
