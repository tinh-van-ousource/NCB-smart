package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.CompanyCreateReqDto;
import com.tvo.controllerDto.CompanySearchReqDto;
import com.tvo.controllerDto.CompanyUpdateReqDto;
import com.tvo.dto.CompanyResDto;
import com.tvo.response.ResponeData;
import com.tvo.service.CompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/search")
    public ResponeData<Page<CompanyResDto>> search(@ModelAttribute CompanySearchReqDto companySearchReqDto,
                                                   @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<CompanyResDto> dts = companyService.search(companySearchReqDto, pageable);
        logger.info("Tìm kiếm Chi nhánh");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
    }

    @GetMapping(value = "/detail")
    public ResponeData<CompanyResDto> detail(@RequestParam String compCode, String mcn, String mp) {
        CompanyResDto dts = companyService.detail(compCode, mcn, mp);
        logger.info("Chi tiết Chi nhánh");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
    }

    @PostMapping(value = "/create")
    public ResponeData<CompanyResDto> create(@Valid @RequestBody CompanyCreateReqDto companyCreateReqDto) {
        CompanyResDto resDto = companyService.create(companyCreateReqDto);
        if (resDto == null) {
            return new ResponeData<>(AppConstant.COMPANY_EXISTED_CODE, AppConstant.COMPANY_EXISTED_MESSAGE, null);
        }
        logger.info("Tạo mới Chi nhánh");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, resDto);
    }

    @PutMapping(value = "/update")
    public ResponeData<CompanyResDto> update(@Valid @RequestBody CompanyUpdateReqDto companyUpdateReqDto) {
        CompanyResDto resDto = companyService.update(companyUpdateReqDto);
        if (resDto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        logger.info("Cập nhật thông tin Chi nhánh");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, resDto);
    }

    @DeleteMapping(value = "/delete")
    public ResponeData<Boolean> delete(@RequestParam String compCode, String mcn, String mp) {
        Boolean resDto = companyService.delete(compCode, mcn, mp);
        logger.info("Xóa Chi nhánh");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, resDto);
    }

}
