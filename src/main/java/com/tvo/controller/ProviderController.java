package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchProviderReqDto;
import com.tvo.dto.ProviderResDto;
import com.tvo.request.ProviderCreateReqDto;
import com.tvo.request.ProviderUpdateReqDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ProviderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public ResponeData<ProviderResDto> detail(@RequestParam Long id) {
        ProviderResDto ProviderDtos = providerServiceImpl.detail(id);
        if (ProviderDtos == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ProviderDtos);
    }

    @DeleteMapping(value = "/delete")
    public ResponeData<Boolean> delete(@RequestParam Long id) {
        boolean result = providerServiceImpl.delete(id);
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

}
