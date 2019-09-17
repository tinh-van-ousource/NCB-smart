package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dto.MbProvisionResDto;
import com.tvo.request.CreateMbProvisionRequest;
import com.tvo.request.UpdateMbProvisionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.MbProvisionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 8, 2019
 */
@RestController
@RequestMapping(value = "mb-provision")
public class MbProvisionController {

    @Autowired
    private MbProvisionService mbProvisionService;

    @GetMapping(value = "search")
    public ResponeData<Page<MbProvisionResDto>> search(@ModelAttribute SearchMbProvisionModel searchModel,
                                                       @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<MbProvisionResDto> res = mbProvisionService.search(searchModel, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }

    @GetMapping(value = "detail")
    public ResponeData<MbProvisionResDto> detail(@RequestParam Long id) {
        MbProvisionResDto mbProvisionResDto = mbProvisionService.findById(id);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                mbProvisionResDto);
    }

    @PostMapping(value = "create")
    public ResponeData<MbProvisionResDto> create(@RequestBody CreateMbProvisionRequest request) {
        MbProvisionResDto mbProvision = mbProvisionService.create(request);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, mbProvision);
    }

    @PutMapping(value = "update")
    public ResponeData<MbProvisionResDto> update(@RequestBody UpdateMbProvisionRequest request) {
        MbProvisionResDto mbProvision = mbProvisionService.update(request);
        if (mbProvision == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, mbProvision);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam Long id) {
        boolean deleteFlag = mbProvisionService.delete(id);
        if (deleteFlag) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
