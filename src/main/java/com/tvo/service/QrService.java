package com.tvo.service;

import com.tvo.controllerDto.SearchQrServiceDto;
import com.tvo.dto.QrServiceDto;
import com.tvo.request.CreateQrService;
import com.tvo.request.UpdateQrService;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
public interface QrService {
    /**
     * Search QrService by title or status.
     *
     * @param searchQrServiceDto searchQrServiceDto
     * @param pageable           pageable
     * @return Page<QrServiceDto>
     * @throws Exception
     */
    ResponeData<Page<QrServiceDto>> search(SearchQrServiceDto searchQrServiceDto, Pageable pageable) throws Exception;

    /**
     * Create QrService.
     *
     * @param createQrService createQrService
     * @return QrServiceDto
     * @throws Exception
     */
    ResponeData<QrServiceDto> create(CreateQrService createQrService) throws Exception;

    /**
     * Update QRService.
     *
     * @param id              id
     * @param updateQrService updateQrService
     * @return QrServiceDto
     * @throws Exception
     */
    ResponeData<QrServiceDto> update(Long id, UpdateQrService updateQrService) throws Exception;

    /**
     * Find QRService by id.
     *
     * @param id id
     * @return QrServiceDto
     * @throws Exception
     */
    ResponeData<QrServiceDto> detail(Long id) throws Exception;

    /**
     * Delete QrService.
     *
     * @param id id
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> delete(Long id) throws Exception;

}
