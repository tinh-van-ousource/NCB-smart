package com.tvo.service;

import com.tvo.controllerDto.SearchQrCouponDto;
import com.tvo.dto.QrCouponDto;
import com.tvo.request.CreateQrCouponRequest;
import com.tvo.request.UpdateQrCouponRequest;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
public interface QrCouponService {
    /**
     * Search QrCoupon by name, description, startDate, endDate, status, approveStatus.
     *
     * @param searchQrCouponDto searchQrCouponDto
     * @param pageable          pageable
     * @return Page<QrCouponDto>
     * @throws Exception
     */
    ResponeData<Page<QrCouponDto>> search(SearchQrCouponDto searchQrCouponDto, Pageable pageable) throws Exception;

    /**
     * Create QrCoupon QrCoupon with UpdateQrCouponRequest.userCoupons() exists in DatUserProfile.
     *
     * @param qrCouponRequest qrCouponRequest
     * @return QrCouponDto
     * @throws Exception
     */
    ResponeData<QrCouponDto> create(CreateQrCouponRequest qrCouponRequest) throws Exception;

    /**
     * Update QrCoupon with UpdateQrCouponRequest.userCoupons() exists in DatUserProfile.
     *
     * @param id                    id
     * @param updateQrCouponRequest updateQrCouponRequest
     * @return QrCouponDto
     * @throws Exception
     */
    ResponeData<QrCouponDto> update(Long id, UpdateQrCouponRequest updateQrCouponRequest) throws Exception;

    /**
     * Find QrCoupon by id.
     *
     * @param id id
     * @return QrCouponDto
     * @throws Exception
     */
    ResponeData<QrCouponDto> detail(Long id) throws Exception;

    /**
     * Delete QrCoupon.
     *
     * @param id id
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> delete(Long id) throws Exception;

    /**
     * Approve coupons.
     *
     * @param id                    id
     * @param isApprove isApprove
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> approve(Long id, boolean isApprove) throws Exception;

}
