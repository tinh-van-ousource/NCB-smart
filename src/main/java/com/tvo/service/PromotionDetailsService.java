package com.tvo.service;

import com.tvo.controllerDto.SearchPromotionDetailsDto;
import com.tvo.controllerDto.SearchReferralCodePartnerDto;
import com.tvo.dto.PromotionDetailsDto;
import com.tvo.dto.ReferralCodePartnerDto;
import com.tvo.request.CreateOrUpdatePromotionDetailsRequest;
import com.tvo.request.CreateOrUpdateReferralCodePartnerRequest;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author thanglt on 16/11/2020
 * @version 1.0
 */
public interface PromotionDetailsService {
    /**
     * Search PromotionDetailsService by promotionName or promotionCode or status.
     *
     * @param searchPromotionDetailsDto searchPromotionDetailsDto
     * @param pageable                     pageable
     * @return Page<PromotionDetailsDto>
     * @throws Exception
     */
    ResponeData<Page<PromotionDetailsDto>> search(SearchPromotionDetailsDto searchPromotionDetailsDto, Pageable pageable) throws Exception;

    /**
     * Create PromotionDetails.
     *
     * @param request request
     * @return PromotionDetailsDto
     * @throws Exception
     */
    ResponeData<PromotionDetailsDto> create(CreateOrUpdatePromotionDetailsRequest request) throws Exception;

    /**
     * Update PromotionDetails.
     *
     * @param id      id
     * @param request request
     * @return PromotionDetailsDto
     * @throws Exception
     */
    ResponeData<PromotionDetailsDto> update(Long id, CreateOrUpdatePromotionDetailsRequest request) throws Exception;

    /**
     * Find PromotionDetails by id.
     *
     * @param id id
     * @return PromotionDetailsDto.
     * @throws Exception
     */
    ResponeData<PromotionDetailsDto> detail(Long id) throws Exception;

    /**
     * Find PromotionDetails by promotionCode.
     *
     * @param promotionCode promotionCode
     * @return PromotionDetailsDto.
     * @throws Exception
     */
    ResponeData<PromotionDetailsDto> findByPromotionCode(String promotionCode) throws Exception;

    /**
     * Delete PromotionDetails.
     *
     * @param id id
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> delete(Long id) throws Exception;

}
