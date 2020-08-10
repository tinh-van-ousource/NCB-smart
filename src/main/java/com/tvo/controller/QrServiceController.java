package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchQrServiceDto;
import com.tvo.dto.QrServiceDto;
import com.tvo.request.CreateQrService;
import com.tvo.request.UpdateQrService;
import com.tvo.response.ResponeData;
import com.tvo.service.QrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/qr-services")
public class QrServiceController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QrService qrService;

    @GetMapping(value = "")
    public ResponeData<Page<QrServiceDto>> search(@ModelAttribute SearchQrServiceDto searchQrServiceDto,
                                                  @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return qrService.search(searchQrServiceDto, pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PostMapping(value = "")
    public ResponeData<QrServiceDto> create(@RequestBody CreateQrService createQrService) {
        try {
            return qrService.create(createQrService);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<QrServiceDto> update(@PathVariable Long id,
                                            @RequestBody UpdateQrService updateQrService) {
        try {
            return qrService.update(id, updateQrService);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponeData<QrServiceDto> detail(@PathVariable Long id) {
        try {
            return qrService.detail(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return qrService.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }
}
