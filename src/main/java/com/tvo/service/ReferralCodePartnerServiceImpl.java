package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchReferralCodePartnerDto;
import com.tvo.dao.ReferralCodePartnerDao;
import com.tvo.dto.ReferralCodePartnerDto;
import com.tvo.model.QrServiceEntity;
import com.tvo.model.ReferralCodePartnerEntity;
import com.tvo.request.CreateOrUpdateReferralCodePartnerRequest;
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
 * @author thanglt on 11/16/2020
 * @version 1.0
 */
@Service
public class ReferralCodePartnerServiceImpl implements ReferralCodePartnerService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private ReferralCodePartnerDao referralCodePartnerDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<ReferralCodePartnerDto>> search(SearchReferralCodePartnerDto searchReferralCodePartnerDto, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ReferralCodePartnerEntity> query = cb.createQuery(ReferralCodePartnerEntity.class);
        Object[] queryObjs = this.createReferralCodePartnerRootPersist(cb, query, searchReferralCodePartnerDto);
        Root<ReferralCodePartnerEntity> root = (Root<ReferralCodePartnerEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<ReferralCodePartnerEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ReferralCodePartnerEntity> objects = typedQuery.getResultList();
        List<ReferralCodePartnerDto> referralCodePartnerDtos = ModelMapperUtils.mapAll(objects, ReferralCodePartnerDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ReferralCodePartnerEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tìm kiếm Đối tác mã giới thiệu");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(referralCodePartnerDtos, pageable, total));
    }

    private Object[] createReferralCodePartnerRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchReferralCodePartnerDto resource) {
        final Root<ReferralCodePartnerEntity> rootPersist = query.from(ReferralCodePartnerEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getPartnerName() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPartnerName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("partnerName")), "%" + resource.getPartnerName().toUpperCase() + "%")));
        }
        if (resource.getPartnerCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPartnerCode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("partnerCode")), "%" + resource.getPartnerCode().toUpperCase() + "%")));
        }
        predicates.add(cb.and(cb.isNull(rootPersist.<LocalDateTime>get("deletedAt"))));
        if (resource.getFromDate() != null) {
            predicates.add(cb.and(cb.greaterThanOrEqualTo(rootPersist.get("createdAt"), resource.getFromDate())));
        }
        if (resource.getFromDate() != null) {
            predicates.add(cb.and(cb.lessThanOrEqualTo(rootPersist.get("createdAt"), resource.getToDate())));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<ReferralCodePartnerDto> create(CreateOrUpdateReferralCodePartnerRequest request) throws Exception {
        ReferralCodePartnerEntity referralCodePartnerEntity = this.setCreate(request);
        ReferralCodePartnerEntity save = referralCodePartnerDao.save(referralCodePartnerEntity);
        if (save == null) {
            return new ResponeData<>(AppConstant.PROVIDER_EXISTED_CODE, AppConstant.PROVIDER_EXISTED_MESSAGE, null);
        }

        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tạo mới Đối tác mã giới thiệu");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, ReferralCodePartnerDto.class));
    }

    private ReferralCodePartnerEntity setCreate(CreateOrUpdateReferralCodePartnerRequest codePartnerRequest) {
        ReferralCodePartnerEntity referralCodePartnerEntity = new ReferralCodePartnerEntity();
        referralCodePartnerEntity.setPartnerName(StringUtils.isEmpty(codePartnerRequest.getPartnerName()) ? null : codePartnerRequest.getPartnerName());
        referralCodePartnerEntity.setPartnerCode(StringUtils.isEmpty(codePartnerRequest.getPartnerCode()) ? null : codePartnerRequest.getPartnerCode());
        referralCodePartnerEntity.setCreatedAt(DateTimeUtil.getNow());
        referralCodePartnerEntity.setCreatedBy(Flag.userFlag.getUserName());
        return referralCodePartnerEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<ReferralCodePartnerDto> update(Long id, CreateOrUpdateReferralCodePartnerRequest request) throws Exception {
        ReferralCodePartnerEntity referralCodePartnerEntity = referralCodePartnerDao.findByIdNotDeleted(id);
        if (referralCodePartnerEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        referralCodePartnerEntity = setUpdate(referralCodePartnerEntity, request);
        ReferralCodePartnerEntity save = referralCodePartnerDao.save(referralCodePartnerEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật Đối tác mã giới thiệu");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, ReferralCodePartnerDto.class));
    }

    private ReferralCodePartnerEntity setUpdate(ReferralCodePartnerEntity codePartnerEntity, CreateOrUpdateReferralCodePartnerRequest updateRequest) {
        if (!StringUtils.isEmpty(updateRequest.getPartnerName())) {
            codePartnerEntity.setPartnerName(updateRequest.getPartnerName());
        }
        if (!StringUtils.isEmpty(updateRequest.getPartnerCode())) {
            codePartnerEntity.setPartnerCode(updateRequest.getPartnerCode());
        }
        codePartnerEntity.setUpdatedAt(DateTimeUtil.getNow());
        codePartnerEntity.setUpdatedBy(Flag.userFlag.getUserName());
        return codePartnerEntity;
    }

    @Override
    public ResponeData<ReferralCodePartnerDto> detail(Long id) throws Exception {
        ReferralCodePartnerEntity referralCodePartnerEntity = referralCodePartnerDao.findByIdNotDeleted(id);
        if (referralCodePartnerEntity == null) {
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
                " \n Chi tiết Đối tác mã giới thiệu");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(referralCodePartnerEntity, ReferralCodePartnerDto.class));
    }

    @Override
    public ResponeData<ReferralCodePartnerDto> findByReferralCode(String referralCode) throws Exception {
        ReferralCodePartnerEntity referralCodePartnerEntity = referralCodePartnerDao.findByReferralCodeNotDeleted(referralCode);
        if (referralCodePartnerEntity == null) {
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
                " \n Chi tiết Đối tác mã giới thiệu");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(referralCodePartnerEntity, ReferralCodePartnerDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> delete(Long id) throws Exception {
        ReferralCodePartnerEntity referralCodePartnerEntity = referralCodePartnerDao.findByIdNotDeleted(id);
        if (referralCodePartnerEntity != null) {
            referralCodePartnerEntity.setDeletedAt(DateTimeUtil.getNow());
            referralCodePartnerEntity.setDeletedBy(Flag.userFlag.getUserName());
            referralCodePartnerDao.save(referralCodePartnerEntity);
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa Đối tác mã giới thiệu");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }
}
