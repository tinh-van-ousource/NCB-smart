package com.tvo.service;

import com.tvo.controllerDto.ParCardSearchReqDto;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.request.ParCardProductCreateReqDto;
import com.tvo.request.ParCardProductUpdateReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParCardProductService {
    Page<ParCardProductResDto> search(ParCardSearchReqDto searchModel, Pageable pageable);

    ParCardProductResDto findByPrdcode(String prdcode);

    boolean delete(String prdCode);

    ParCardProductResDto create(ParCardProductCreateReqDto parCardProductCreateReqDto);

    ParCardProductResDto update(ParCardProductCreateReqDto parCardProductUpdateReqDto);
}
