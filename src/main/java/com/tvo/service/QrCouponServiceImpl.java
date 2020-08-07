package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchQrCouponDto;
import com.tvo.dao.CouponObjectUserDao;
import com.tvo.dao.QrCouponDao;
import com.tvo.dto.QrCouponDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.CouponObjectUserEntity;
import com.tvo.model.QrCouponsEntity;
import com.tvo.request.CreateQrCouponRequest;
import com.tvo.request.UpdateQrCouponRequest;
import com.tvo.request.UserCoupon;
import com.tvo.response.ResponeData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@Service
public class QrCouponServiceImpl implements QrCouponService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private QrCouponDao qrCouponDao;

    @Autowired
    private CouponObjectUserDao couponObjectUserDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<QrCouponDto>> search(SearchQrCouponDto searchQrCouponDto, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<QrCouponsEntity> query = cb.createQuery(QrCouponsEntity.class);
        Object[] queryObjs = this.createQrCouponRootPersist(cb, query, searchQrCouponDto);
        Root<QrCouponsEntity> root = (Root<QrCouponsEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<QrCouponsEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<QrCouponsEntity> objects = typedQuery.getResultList();
        List<QrCouponDto> qrCouponDtos = ModelMapperUtils.mapAll(objects, QrCouponDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(QrCouponsEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tìm kiếm Dịch vụ QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(qrCouponDtos, pageable, total));
    }

    private Object[] createQrCouponRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchQrCouponDto resource) {
        final Root<QrCouponsEntity> rootPersist = query.from(QrCouponsEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getName() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("name")), "%" + resource.getName().toUpperCase() + "%")));
        }
        if (resource.getDescription() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getDescription().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("description")), "%" + resource.getDescription().toUpperCase() + "%")));
        }
        if (resource.getStartDate() != null) {
            predicates.add(cb.and(cb.greaterThanOrEqualTo(rootPersist.get("startDate"), resource.getStartDate())));
        }
        if (resource.getEndDate() != null) {
            predicates.add(cb.and(cb.lessThanOrEqualTo(rootPersist.get("endDate"), resource.getEndDate())));
        }
        predicates.add(cb.and(cb.isNull(rootPersist.<LocalDateTime>get("deletedAt"))));
        if (resource.getStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
        }
        if (resource.getDiscountType() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getDiscountType().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("discountType"), resource.getDiscountType())));
        }
        if (resource.getServiceId() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getServiceId().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("serviceId"), resource.getServiceId())));
        }
        if (resource.getApproveStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getApproveStatus().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("approveStatus"), resource.getApproveStatus())));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<QrCouponDto> create(CreateQrCouponRequest qrCouponRequest) throws Exception {
        QrCouponsEntity qrCouponsEntity = setCreate(qrCouponRequest);
        QrCouponsEntity save = qrCouponDao.save(qrCouponsEntity);
        if (save.getObjectUserType().equals("0") && !qrCouponRequest.getUserCoupons().isEmpty()) {
            List<CouponObjectUserEntity> couponObjectUserEntities = setCreate(save.getId(), qrCouponRequest);
            couponObjectUserDao.saveAll(couponObjectUserEntities);
        }
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tạo mới Dịch vụ QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, QrCouponDto.class));
    }

    private QrCouponsEntity setCreate(CreateQrCouponRequest createQrCouponRequest) {
        QrCouponsEntity qrCouponsEntity = new QrCouponsEntity();
        qrCouponsEntity.setName(StringUtils.isEmpty(createQrCouponRequest.getName()) ? null : createQrCouponRequest.getName());
        qrCouponsEntity.setDescription(StringUtils.isEmpty(createQrCouponRequest.getDescription()) ? null : createQrCouponRequest.getDescription());
        qrCouponsEntity.setCode(StringUtils.isEmpty(createQrCouponRequest.getCode()) ? null : createQrCouponRequest.getCode());
        qrCouponsEntity.setObjectUserType(StringUtils.isEmpty(createQrCouponRequest.getObjectUserType()) ? null : createQrCouponRequest.getObjectUserType());
        qrCouponsEntity.setDiscountType(StringUtils.isEmpty(createQrCouponRequest.getDiscountType()) ? null : createQrCouponRequest.getDiscountType());
        qrCouponsEntity.setServiceId(StringUtils.isEmpty(createQrCouponRequest.getServiceId()) ? null : createQrCouponRequest.getServiceId());
        qrCouponsEntity.setStartDate(StringUtils.isEmpty(createQrCouponRequest.getStartDate().toString()) ? null : createQrCouponRequest.getStartDate());
        qrCouponsEntity.setEndDate(StringUtils.isEmpty(createQrCouponRequest.getEndDate().toString()) ? null : createQrCouponRequest.getEndDate());
        qrCouponsEntity.setAmount(StringUtils.isEmpty(createQrCouponRequest.getAmount().toString()) ? null : createQrCouponRequest.getAmount());
        qrCouponsEntity.setPaymentMin(StringUtils.isEmpty(createQrCouponRequest.getPaymentMin().toString()) ? null : createQrCouponRequest.getPaymentMin());
        qrCouponsEntity.setAmountMax(StringUtils.isEmpty(createQrCouponRequest.getAmountMax().toString()) ? null : createQrCouponRequest.getAmountMax());
        qrCouponsEntity.setAmountPercentage(StringUtils.isEmpty(createQrCouponRequest.getAmountPercentage().toString()) ? null : createQrCouponRequest.getAmountPercentage());
        qrCouponsEntity.setNumberPerCustomer(StringUtils.isEmpty(createQrCouponRequest.getNumberPerCustomer().toString()) ? null : createQrCouponRequest.getNumberPerCustomer());
        qrCouponsEntity.setTotalNumberCoupon(StringUtils.isEmpty(createQrCouponRequest.getTotalNumberCoupon().toString()) ? null : createQrCouponRequest.getTotalNumberCoupon());
        qrCouponsEntity.setStatus(StringUtils.isEmpty(createQrCouponRequest.getStatus()) ? null : createQrCouponRequest.getStatus());
        qrCouponsEntity.setApproveStatus(StringUtils.isEmpty(createQrCouponRequest.getApproveStatus()) ? StatusActivate.STATUS_ACTIVATED.getStatus() : createQrCouponRequest.getApproveStatus());
        qrCouponsEntity.setCreatedAt(LocalDateTime.now());
        qrCouponsEntity.setCreatedBy(Flag.userFlag.getUserName());
        return qrCouponsEntity;
    }

    private List<CouponObjectUserEntity> setCreate(Long qrCouponId, CreateQrCouponRequest createQrCouponRequest) {
        List<CouponObjectUserEntity> couponObjectUserEntities = new ArrayList<>();
        List<UserCoupon> userCoupons = createQrCouponRequest.getUserCoupons();
        if (userCoupons != null && !userCoupons.isEmpty()) {
            CouponObjectUserEntity couponObjectUserEntity;
            for (UserCoupon userCoupon : userCoupons) {
                couponObjectUserEntity = ModelMapperUtils.map(userCoupon, CouponObjectUserEntity.class);
                couponObjectUserEntity.setQrCouponId(qrCouponId);
                couponObjectUserEntity.setCreatedAt(LocalDateTime.now());
                couponObjectUserEntity.setCreatedBy(Flag.userFlag.getUserName());
                couponObjectUserEntities.add(couponObjectUserEntity);
            }
            return couponObjectUserEntities;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<QrCouponDto> update(Long id, UpdateQrCouponRequest updateQrCouponRequest) throws Exception {
        QrCouponsEntity qrCouponsEntity = qrCouponDao.findByIdNotDeleted(id);
        if (qrCouponsEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        setUpdate(qrCouponsEntity, updateQrCouponRequest);
        QrCouponsEntity save = qrCouponDao.save(qrCouponsEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật thông tin Phiếu giảm giá");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, QrCouponDto.class));
    }

    private void setUpdate(QrCouponsEntity qrCouponsEntity, UpdateQrCouponRequest updateQrCouponRequest) {
        if (!StringUtils.isEmpty(updateQrCouponRequest.getName())) {
            qrCouponsEntity.setName(updateQrCouponRequest.getName());
        }
        if (!StringUtils.isEmpty(updateQrCouponRequest.getDescription())) {
            qrCouponsEntity.setDescription(updateQrCouponRequest.getDescription());
        }
        if (!StringUtils.isEmpty(updateQrCouponRequest.getCode())) {
            qrCouponsEntity.setCode(updateQrCouponRequest.getCode());
        }
        if (!StringUtils.isEmpty(updateQrCouponRequest.getObjectUserType())) {
            qrCouponsEntity.setObjectUserType(updateQrCouponRequest.getObjectUserType());
        }
        if (!StringUtils.isEmpty(updateQrCouponRequest.getDiscountType())) {
            qrCouponsEntity.setDiscountType(updateQrCouponRequest.getDiscountType());
        }
        if (!StringUtils.isEmpty(updateQrCouponRequest.getServiceId())) {
            qrCouponsEntity.setServiceId(updateQrCouponRequest.getServiceId());
        }
        if (updateQrCouponRequest.getStartDate() != null) {
            qrCouponsEntity.setStartDate(updateQrCouponRequest.getStartDate());
        }
        if (updateQrCouponRequest.getEndDate() != null) {
            qrCouponsEntity.setEndDate(updateQrCouponRequest.getEndDate());
        }
        if (updateQrCouponRequest.getAmount() != null) {
            qrCouponsEntity.setAmount(updateQrCouponRequest.getAmount());
        }
        if (updateQrCouponRequest.getPaymentMin() != null) {
            qrCouponsEntity.setPaymentMin(updateQrCouponRequest.getPaymentMin());
        }
        if (updateQrCouponRequest.getAmountMax() != null) {
            qrCouponsEntity.setAmountMax(updateQrCouponRequest.getAmountMax());
        }
        if (updateQrCouponRequest.getAmountPercentage() != null) {
            qrCouponsEntity.setAmountPercentage(updateQrCouponRequest.getAmountPercentage());
        }
        if (updateQrCouponRequest.getNumberPerCustomer() != null) {
            qrCouponsEntity.setNumberPerCustomer(updateQrCouponRequest.getNumberPerCustomer());
        }
        if (updateQrCouponRequest.getTotalNumberCoupon() != null) {
            qrCouponsEntity.setTotalNumberCoupon(updateQrCouponRequest.getTotalNumberCoupon());
        }
        if (!StringUtils.isEmpty(updateQrCouponRequest.getStatus())) {
            qrCouponsEntity.setStatus(updateQrCouponRequest.getStatus());
        }
        if (!StringUtils.isEmpty(updateQrCouponRequest.getApproveStatus())) {
            qrCouponsEntity.setApproveStatus(updateQrCouponRequest.getApproveStatus());
        }
        qrCouponsEntity.setUpdatedAt(LocalDateTime.now());
        qrCouponsEntity.setUpdatedBy(Flag.userFlag.getUserName());
    }

    @Override
    public ResponeData<QrCouponDto> detail(Long id) throws Exception {
        QrCouponsEntity qrCouponsEntity = qrCouponDao.findByIdNotDeleted(id);
        if (qrCouponsEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Chi tiết Phiếu giảm giá");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(qrCouponsEntity, QrCouponDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> delete(Long id) throws Exception {
        QrCouponsEntity qrCouponsEntity = qrCouponDao.findByIdNotDeleted(id);
        if (qrCouponsEntity != null) {
            qrCouponsEntity.setDeletedAt(LocalDateTime.now());
            qrCouponDao.save(qrCouponsEntity);
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa Phiếu giảm giá");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }

}
