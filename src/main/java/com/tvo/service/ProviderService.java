package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchProviderReqDto;
import com.tvo.dto.ProviderResDto;
import com.tvo.dto.ServiceMbappCodeListDto;
import com.tvo.request.ProviderCreateReqDto;
import com.tvo.request.ProviderUpdateReqDto;

public interface ProviderService {
    Page<ProviderResDto> search(SearchProviderReqDto searchProviderReqDto, Pageable pageable);

    ProviderResDto create(ProviderCreateReqDto request);

    ProviderResDto update(ProviderUpdateReqDto request);

    ProviderResDto detail(String providerCode);
    
    List<ServiceMbappCodeListDto> getServiceCodeList();

    boolean delete(String providerCode);
}
