package com.tvo.service;

import com.tvo.controllerDto.SearchProviderReqDto;
import com.tvo.controllerDto.SearchQrServiceDto;
import com.tvo.dto.ProviderResDto;
import com.tvo.dto.QrServiceDto;
import com.tvo.request.CreateQrService;
import com.tvo.request.UpdateQrService;
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
     */
    Page<QrServiceDto> search(SearchQrServiceDto searchQrServiceDto, Pageable pageable);

    /**
     * Create QrService.
     *
     * @param createQrService createQrService
     * @return QrServiceDto
     */
    QrServiceDto create(CreateQrService createQrService);

    /**
     * Update QRService.
     *
     * @param id              id
     * @param updateQrService updateQrService
     * @return QrServiceDto
     */
    QrServiceDto update(Long id, UpdateQrService updateQrService);

    /**
     * Find QRService by id.
     *
     * @param id id
     * @return QrServiceDto
     */
    QrServiceDto detail(Long id);

    /**
     * Delete QrService.
     *
     * @param id id
     * @return boolean
     */
    boolean delete(Long id);

}
