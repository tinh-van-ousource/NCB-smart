package com.tvo.service;

import com.tvo.dto.QrMerchantDto;
import com.tvo.request.QrMerchantRequest;
import com.tvo.request.QrMerchantCreateRequest;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author thanglt on 8/31/2020
 * @version 1.0
 */
public interface QrMerchantService {
    /**
     * Search QrMerchant by title or status.
     *
     * @param search   search
     * @param pageable pageable
     * @return Page<QrMerchantDto>
     * @throws Exception
     */
    ResponeData<Page<QrMerchantDto>> search(String search, Pageable pageable) throws Exception;

    /**
     * Create QrMerchant.
     *
     * @param qrMerchantCreateRequest qrMerchantCreateRequest
     * @return QrMerchantDto
     * @throws Exception
     */
    ResponeData<Boolean> create(QrMerchantCreateRequest qrMerchantCreateRequest) throws Exception;

    /**
     * Update QrMerchant.
     *
     * @param id                id
     * @param qrMerchantRequest qrMerchantRequest
     * @return QrServiceDto
     * @throws Exception
     */
    ResponeData<QrMerchantDto> update(Long id, QrMerchantRequest qrMerchantRequest) throws Exception;

    /**
     * Find QrMerchant by id.
     *
     * @param id id
     * @return QrMerchantDto
     * @throws Exception
     */
    ResponeData<QrMerchantDto> detail(Long id) throws Exception;

    /**
     * Delete QrMerchant.
     *
     * @param id id
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> delete(Long id) throws Exception;
}
