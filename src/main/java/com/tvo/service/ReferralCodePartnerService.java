package com.tvo.service;

import com.tvo.controllerDto.SearchReferralCodePartnerDto;
import com.tvo.dto.QrServiceDto;
import com.tvo.dto.ReferralCodePartnerDto;
import com.tvo.request.CreateOrUpdateReferralCodePartnerRequest;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author thanglt on 16/11/2020
 * @version 1.0
 */
public interface ReferralCodePartnerService {
    /**
     * Search ReferralCodePartnerService by partnerName or partnerCode.
     *
     * @param searchReferralCodePartnerDto searchReferralCodePartnerDto
     * @param pageable                     pageable
     * @return Page<ReferralCodePartnerDto>
     * @throws Exception
     */
    ResponeData<Page<ReferralCodePartnerDto>> search(SearchReferralCodePartnerDto searchReferralCodePartnerDto, Pageable pageable) throws Exception;

    /**
     * Create ReferralCodePartner.
     *
     * @param request request
     * @return ReferralCodePartnerDto
     * @throws Exception
     */
    ResponeData<ReferralCodePartnerDto> create(CreateOrUpdateReferralCodePartnerRequest request) throws Exception;

    /**
     * Update ReferralCodePartner.
     *
     * @param id      id
     * @param request request
     * @return ReferralCodePartnerDto
     * @throws Exception
     */
    ResponeData<ReferralCodePartnerDto> update(Long id, CreateOrUpdateReferralCodePartnerRequest request) throws Exception;

    /**
     * Find ReferralCodePartner by id.
     *
     * @param id id
     * @return ReferralCodePartnerDto.
     * @throws Exception
     */
    ResponeData<ReferralCodePartnerDto> detail(Long id) throws Exception;

    /**
     * Find ReferralCodePartner by referralCode.
     *
     * @param referralCode referralCode
     * @return ReferralCodePartnerDto.
     * @throws Exception
     */
    ResponeData<ReferralCodePartnerDto> findByReferralCode(String referralCode) throws Exception;

    /**
     * Delete ReferralCodePartner.
     *
     * @param id id
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> delete(Long id) throws Exception;

}
