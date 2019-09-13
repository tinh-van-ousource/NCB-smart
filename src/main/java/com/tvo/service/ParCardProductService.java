package com.tvo.service;

import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.model.ParCardProductEntity;
import com.tvo.request.ParCardProductCreateReqDto;
import com.tvo.request.ParCardProductUpdateReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParCardProductService {
    Page<ParCardProductResDto> search(ParCardSearch searchModel, Pageable pageable);

    ParCardProductResDto findByPrdcode(String prdcode);

    boolean delete(String prdCode);

    ParCardProductResDto create(ParCardProductCreateReqDto parCardProductCreateReqDto);

    ParCardProductResDto update(ParCardProductUpdateReqDto parCardProductUpdateReqDto);
}
