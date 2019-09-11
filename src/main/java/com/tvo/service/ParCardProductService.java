package com.tvo.service;

import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.model.ParCardProductEntity;
import com.tvo.request.ParCardProductCreateReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParCardProductService {
    Page<ParCardProductResDto> search(ParCardSearch searchModel, Pageable pageable);

    ParCardProductEntity findPrdcode(String prdcode);

    ParCardProductResDto edit(ParCardProductCreateReqDto request);

    String delete(String prdCode);

    ParCardProductResDto create(ParCardProductCreateReqDto parCardProductCreateReqDto);

}
