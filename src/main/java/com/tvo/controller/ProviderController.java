package com.tvo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchProviderReqDto;
import com.tvo.dto.ProviderResDto;
import com.tvo.dto.ServiceMbappCodeListDto;
import com.tvo.request.ProviderCreateReqDto;
import com.tvo.request.ProviderUpdateReqDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ProviderServiceImpl;

@RestController
@RequestMapping(value = "/provider")
public class ProviderController {

    @Autowired
    ProviderServiceImpl providerServiceImpl;

    @GetMapping(value = "/search")
    public ResponeData<Page<ProviderResDto>> search(SearchProviderReqDto searchProviderReqDto, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<ProviderResDto> ProviderDtos = providerServiceImpl.search(searchProviderReqDto, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ProviderDtos);
    }

    @GetMapping(value = "/detail")
    public ResponeData<ProviderResDto> detail(@RequestParam String providerCode) {
        ProviderResDto ProviderDtos = providerServiceImpl.detail(providerCode);
        if (ProviderDtos == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ProviderDtos);
    }

    @DeleteMapping(value = "/delete")
    public ResponeData<Boolean> delete(@RequestParam String providerCode) {
        boolean result = providerServiceImpl.delete(providerCode);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }

    @PostMapping(value = "/create")
    public ResponeData<ProviderResDto> create(@RequestBody ProviderCreateReqDto request) {
        ProviderResDto dto = providerServiceImpl.create(request);
        if (dto == null) {
            return new ResponeData<>(AppConstant.PROVIDER_EXISTED_CODE, AppConstant.PROVIDER_EXISTED_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @PutMapping(value = "/update")
    public ResponeData<ProviderResDto> update(@RequestBody ProviderUpdateReqDto request) {
        ProviderResDto dto = providerServiceImpl.update(request);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }
    
    @GetMapping(value = "/list-service-code")
    public ResponeData<List<ServiceMbappCodeListDto>> getServiceCodeList() {
		List<ServiceMbappCodeListDto> res = providerServiceImpl.getServiceCodeList();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }

}
