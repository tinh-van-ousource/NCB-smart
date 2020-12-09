package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchPromotionDetailsDto;
import com.tvo.dao.PromotionDetailsDao;
import com.tvo.dto.PromotionDetailsDto;
import com.tvo.model.PromotionDetailsEntity;
import com.tvo.request.CreateOrUpdatePromotionDetailsRequest;
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
public class PromotionDetailsServiceImpl implements PromotionDetailsService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private PromotionDetailsDao promotionDetailsDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<PromotionDetailsDto>> search(SearchPromotionDetailsDto searchPromotionDetailsDto, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<PromotionDetailsEntity> query = cb.createQuery(PromotionDetailsEntity.class);
        Object[] queryObjs = this.createPromotionDetailsRootPersist(cb, query, searchPromotionDetailsDto);
        Root<PromotionDetailsEntity> root = (Root<PromotionDetailsEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<PromotionDetailsEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<PromotionDetailsEntity> objects = typedQuery.getResultList();
        List<PromotionDetailsDto> promotionDetailsDtos = ModelMapperUtils.mapAll(objects, PromotionDetailsDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(PromotionDetailsEntity.class)));
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
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(promotionDetailsDtos, pageable, total));
    }

    private Object[] createPromotionDetailsRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchPromotionDetailsDto resource) {
        final Root<PromotionDetailsEntity> rootPersist = query.from(PromotionDetailsEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getPromotionName() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPromotionName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("promotionName")), "%" + resource.getPromotionName().toUpperCase() + "%")));
        }
        if (resource.getPromotionCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPromotionCode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("promotionCode")), "%" + resource.getPromotionCode().toUpperCase() + "%")));
        }
        if (resource.getReferPartnerCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getReferPartnerCode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("referPartnerCode")), "%" + resource.getReferPartnerCode().toUpperCase() + "%")));
        }
        if (resource.getFromDate() != null) {
            predicates.add(cb.and(cb.greaterThanOrEqualTo(rootPersist.get("createdAt"), resource.getFromDate())));
        }
        if (resource.getToDate() != null) {
            predicates.add(cb.and(cb.lessThanOrEqualTo(rootPersist.get("createdAt"), resource.getToDate())));
        }
        predicates.add(cb.and(cb.isNull(rootPersist.<LocalDateTime>get("deletedAt"))));

        if(resource.getStatus() != null){
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<PromotionDetailsDto> create(CreateOrUpdatePromotionDetailsRequest request) throws Exception {
        PromotionDetailsEntity promotionDetailsEntity = this.setCreate(request);
        PromotionDetailsEntity save = promotionDetailsDao.save(promotionDetailsEntity);
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
                " \n Tạo mới Chi tiết khuyến mãi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, PromotionDetailsDto.class));
    }

    private PromotionDetailsEntity setCreate(CreateOrUpdatePromotionDetailsRequest detailsRequest) {
        PromotionDetailsEntity promotionDetailsEntity = new PromotionDetailsEntity();
        promotionDetailsEntity.setPromotionName(StringUtils.isEmpty(detailsRequest.getPromotionName()) ? null : detailsRequest.getPromotionName());
        promotionDetailsEntity.setPromotionCode(StringUtils.isEmpty(detailsRequest.getPromotionCode()) ? null : detailsRequest.getPromotionCode());
        promotionDetailsEntity.setReferPartnerCode(StringUtils.isEmpty(detailsRequest.getReferPartnerCode()) ? null : detailsRequest.getReferPartnerCode());
        promotionDetailsEntity.setStatus("0");
        promotionDetailsEntity.setCreatedAt(DateTimeUtil.getNow());
        promotionDetailsEntity.setCreatedBy(Flag.userFlag.getUserName());
        return promotionDetailsEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<PromotionDetailsDto> update(Long id, CreateOrUpdatePromotionDetailsRequest request) throws Exception {
        PromotionDetailsEntity promotionDetailsEntity = promotionDetailsDao.findByIdNotDeleted(id);
        if (promotionDetailsEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        promotionDetailsEntity = setUpdate(promotionDetailsEntity, request);
        PromotionDetailsEntity save = promotionDetailsDao.save(promotionDetailsEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật Chi tiết khuyến mãi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, PromotionDetailsDto.class));
    }

    private PromotionDetailsEntity setUpdate(PromotionDetailsEntity promotionDetailsEntity, CreateOrUpdatePromotionDetailsRequest updateRequest) {
        if (!StringUtils.isEmpty(updateRequest.getPromotionName())) {
            promotionDetailsEntity.setPromotionName(updateRequest.getPromotionName());
        }
        if (!StringUtils.isEmpty(updateRequest.getPromotionCode())) {
            promotionDetailsEntity.setPromotionCode(updateRequest.getPromotionCode());
        }
        if (!StringUtils.isEmpty(updateRequest.getReferPartnerCode())) {
            promotionDetailsEntity.setReferPartnerCode(updateRequest.getReferPartnerCode());
        }
        if (!StringUtils.isEmpty(updateRequest.getStatus())) {
            promotionDetailsEntity.setStatus(updateRequest.getStatus());
        }
        promotionDetailsEntity.setUpdatedAt(DateTimeUtil.getNow());
        promotionDetailsEntity.setUpdatedBy(Flag.userFlag.getUserName());
        return promotionDetailsEntity;
    }

    @Override
    public ResponeData<PromotionDetailsDto> detail(Long id) throws Exception {
        PromotionDetailsEntity promotionDetailsEntity = promotionDetailsDao.findByIdNotDeleted(id);
        if (promotionDetailsEntity == null) {
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
                " \n Chi tiết khuyến mãi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(promotionDetailsEntity, PromotionDetailsDto.class));
    }

    @Override
    public ResponeData<PromotionDetailsDto> findByPromotionCode(String promotionCode) throws Exception {
        PromotionDetailsEntity promotionDetailsEntity = promotionDetailsDao.findByPromotionCodeNotDeleted(promotionCode);
        if (promotionDetailsEntity == null) {
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
                " \n Chi tiết khuyến mãi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(promotionDetailsEntity, PromotionDetailsDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> delete(Long id) throws Exception {
        PromotionDetailsEntity promotionDetailsEntity = promotionDetailsDao.findByIdNotDeleted(id);
        if (promotionDetailsEntity != null) {
            promotionDetailsEntity.setDeletedAt(DateTimeUtil.getNow());
            promotionDetailsEntity.setDeletedBy(Flag.userFlag.getUserName());
            promotionDetailsDao.save(promotionDetailsEntity);
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa Chi tiết khuyến mãi.");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }
}
