package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dto.MbProvisionResDto;
import com.tvo.request.CreateMbProvisionRequest;
import com.tvo.request.UpdateMbProvisionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.MbProvisionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MbProvisionService mbProvisionService;

    @GetMapping(value = "search")
    public ResponeData<Page<MbProvisionResDto>> search(@ModelAttribute SearchMbProvisionModel searchModel,
                                                       @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<MbProvisionResDto> res = mbProvisionService.search(searchModel, pageable);
        logger.info("Tìm kiếm Thông tin điều khoản sử dụng");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }

    @GetMapping(value = "detail")
    public ResponeData<MbProvisionResDto> detail(@RequestParam Long id) {
        MbProvisionResDto mbProvisionResDto = mbProvisionService.findById(id);
        logger.info("Chi tiết Điều khoản sử dụng");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                mbProvisionResDto);
    }

    @PostMapping(value = "create")
    public ResponeData<MbProvisionResDto> create(@RequestBody CreateMbProvisionRequest request) {
        MbProvisionResDto mbProvision = mbProvisionService.create(request);
        logger.info("Tạo mới Điều khoản sử dụng");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, mbProvision);
    }

    @PutMapping(value = "update")
    public ResponeData<MbProvisionResDto> update(@RequestBody UpdateMbProvisionRequest request) {
        MbProvisionResDto mbProvision = mbProvisionService.update(request);
        if (mbProvision == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        logger.info("Cập nhật thông tin Điều khoản sử dụng");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, mbProvision);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam Long id) {
        boolean deleteFlag = mbProvisionService.delete(id);
        if (deleteFlag) {
        	logger.info("Xóa Điều khoản sử dụng");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
