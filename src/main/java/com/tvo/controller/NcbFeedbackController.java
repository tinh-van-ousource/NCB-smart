package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchNcbFeedbackModel;
import com.tvo.dto.NcbFeedbackDto;
import com.tvo.request.CreateNcbFeedbackRequest;
import com.tvo.request.UpdateNcbFeedbackRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbFeedbackService;
import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "ncb-feedback")
@Api(tags = "Ncb Feedback")
public class NcbFeedbackController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private NcbFeedbackService ncbFeedbackService;

    @GetMapping(value = "search")
    public ResponeData<Page<NcbFeedbackDto>> searchNcbQA(@ModelAttribute SearchNcbFeedbackModel searchModel,
                                                         @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<NcbFeedbackDto> dts = ncbFeedbackService.searchNcbFeedback(searchModel, pageable);
        logger.info("Xóa Banner");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
    }

    @GetMapping(value = "detail")
    public ResponeData<NcbFeedbackDto> detail(@RequestParam Long id) {
        if (id == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        NcbFeedbackDto result = ncbFeedbackService.findById(id);
        logger.info("Xóa Banner");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @PostMapping(value = "create")
    public ResponeData<NcbFeedbackDto> create(@Valid @RequestBody CreateNcbFeedbackRequest request) {
        NcbFeedbackDto ncbFeedbackDto = ncbFeedbackService.create(request);
        logger.info("Xóa Banner");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbFeedbackDto);
    }

    @PutMapping(value = "update")
    public ResponeData<NcbFeedbackDto> update(@Valid @RequestBody UpdateNcbFeedbackRequest request) {
        NcbFeedbackDto ncbFeedbackDto = ncbFeedbackService.update(request);
        if (ncbFeedbackDto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        logger.info("Xóa Banner");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbFeedbackDto);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam Long id) {
        boolean deleteFlag = ncbFeedbackService.delete(id);
        if (deleteFlag == true) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        logger.info("Xóa Banner");
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
