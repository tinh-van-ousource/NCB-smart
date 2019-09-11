package com.tvo.service;

import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.model.ParCardProductEntity;
import com.tvo.request.ParCardProductCreateReqDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ParCardProductService {
    Page<ParCardProductResDto> search(ParCardSearch searchModel, Pageable pageable);

    ParCardProductEntity findPrdcode(String prdcode);

    ParCardProductResDto edit(ParCardProductCreateReqDto request);

    String delete(String prdCode);

    ParCardProductResDto create(MultipartFile multipartFiles, ParCardProductCreateReqDto parCardProductCreateReqDto);
}
