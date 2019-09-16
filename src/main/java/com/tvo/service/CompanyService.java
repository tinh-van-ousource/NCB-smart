package com.tvo.service;

import com.tvo.controllerDto.CompanyCreateReqDto;
import com.tvo.controllerDto.CompanySearchReqDto;
import com.tvo.controllerDto.CompanyUpdateReqDto;
import com.tvo.dto.CompanyResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    CompanyResDto create(CompanyCreateReqDto companyCreateReqDto);

    CompanyResDto update(CompanyUpdateReqDto companyUpdateReqDto);

    Page<CompanyResDto> search(CompanySearchReqDto companySearchReqDto, Pageable pageable);

    CompanyResDto detail(String compCode);

    Boolean delete(String compCode);
}
