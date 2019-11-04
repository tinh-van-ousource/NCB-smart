package com.tvo.service;

import com.tvo.controllerDto.SearchPrdPromotion;
import com.tvo.dto.CreatePrdPromotionRqDto;
import com.tvo.dto.PrdPromotionDto;
import com.tvo.dto.PrdPromotionRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrdPromotionService {

    Page<PrdPromotionDto> search(SearchPrdPromotion searchPrdPromotion, Pageable pageable);

    PrdPromotionDto detail(Long prdPromotionId);

    PrdPromotionDto create(CreatePrdPromotionRqDto createPrdPromotionRqDto);

    PrdPromotionDto update(PrdPromotionRq prdPromotionRq);

    Boolean delete(Long prdPromotionId);

}
