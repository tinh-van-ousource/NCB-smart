package com.tvo.service;

import com.tvo.controllerDto.SearchProviderReqDto;
import com.tvo.dto.ProviderResDto;
import com.tvo.request.ProviderCreateReqDto;
import com.tvo.request.ProviderUpdateReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProviderService {
    Page<ProviderResDto> search(SearchProviderReqDto searchProviderReqDto, Pageable pageable);

    ProviderResDto create(ProviderCreateReqDto request);

    ProviderResDto update(ProviderUpdateReqDto request);

    ProviderResDto detail(Long id);

    boolean delete(Long id);
}
