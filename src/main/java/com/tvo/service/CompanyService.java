package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.CompanyCreateReqDto;
import com.tvo.controllerDto.CompanySearchReqDto;
import com.tvo.controllerDto.CompanyUpdateReqDto;
import com.tvo.dto.CompanyResDto;

public interface CompanyService {

    CompanyResDto create(CompanyCreateReqDto companyCreateReqDto);

    CompanyResDto update(CompanyUpdateReqDto companyUpdateReqDto);

    Page<CompanyResDto> search(CompanySearchReqDto companySearchReqDto, Pageable pageable);

    CompanyResDto detail(String compCode, String mcn, String mp);

    Boolean delete(String compCode, String mcn, String mp);
}
