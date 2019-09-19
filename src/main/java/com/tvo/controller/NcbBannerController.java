package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchNcbBannerModel;
import com.tvo.controllerDto.UpdateNcbBannerRequest;
import com.tvo.dto.NcbBannerDto;
import com.tvo.request.CreateNcbBannerRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "ncb-banner")
@Api(tags = "Ncb Banner")
public class NcbBannerController {
    @Autowired
    private NcbBannerService ncbBannerService;

    @PostMapping(value = "create")
    public ResponeData<NcbBannerDto> create(@RequestBody CreateNcbBannerRequest request) {
        NcbBannerDto ncbBanner = ncbBannerService.create(request);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbBanner);
    }

    @GetMapping(value = "search")
    public ResponeData<Page<NcbBannerDto>> searchNcbBanner(@ModelAttribute SearchNcbBannerModel searchModel,
                                                           @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<NcbBannerDto> dts = ncbBannerService.searchNcbBanner(searchModel, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
    }

    @GetMapping(value = "detail")
    public ResponeData<NcbBannerDto> detail(@RequestParam Long id) {
        if (id == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        NcbBannerDto result = ncbBannerService.findById(id);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @PutMapping(value = "update")
    public ResponeData<NcbBannerDto> update(@RequestBody UpdateNcbBannerRequest request) {
        NcbBannerDto ncbBanner = ncbBannerService.update(request);
        if (ncbBanner == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                ncbBanner);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam Long id) {
        boolean deleteFlag = ncbBannerService.delete(id);
        if (deleteFlag) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
