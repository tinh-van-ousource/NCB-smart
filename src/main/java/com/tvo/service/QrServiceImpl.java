package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchQrServiceDto;
import com.tvo.dao.QrServiceDao;
import com.tvo.dto.QrServiceDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.QrServiceEntity;
import com.tvo.request.CreateQrService;
import com.tvo.request.UpdateQrService;
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
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Service
public class QrServiceImpl implements QrService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private QrServiceDao qrServiceDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<QrServiceDto>> search(SearchQrServiceDto searchQrServiceDto, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<QrServiceEntity> query = cb.createQuery(QrServiceEntity.class);
        Object[] queryObjs = this.createQrServiceRootPersist(cb, query, searchQrServiceDto);
        Root<QrServiceEntity> root = (Root<QrServiceEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<QrServiceEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<QrServiceEntity> objects = typedQuery.getResultList();
        List<QrServiceDto> qrServiceDtos = ModelMapperUtils.mapAll(objects, QrServiceDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(QrServiceEntity.class)));
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
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(qrServiceDtos, pageable, total));
    }

    private Object[] createQrServiceRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchQrServiceDto resource) {
        final Root<QrServiceEntity> rootPersist = query.from(QrServiceEntity.class);
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.and(cb.isNull(rootPersist.<LocalDateTime>get("deletedAt"))));
        if (resource.getTitle() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTitle().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("title")), "%" + resource.getTitle().toUpperCase() + "%")));
        }
        if (resource.getStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
        }
        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<QrServiceDto> create(CreateQrService createQrService) throws Exception {
        QrServiceEntity qrServiceEntity = setQrServiceEntity(createQrService);
        QrServiceEntity save = qrServiceDao.save(qrServiceEntity);
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
                " \n Tạo mới Dịch vụ QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, QrServiceDto.class));
    }

    private QrServiceEntity setQrServiceEntity(CreateQrService createQrService) {
        QrServiceEntity qrServiceEntity = new QrServiceEntity();
        qrServiceEntity.setTitle(StringUtils.isEmpty(createQrService.getTitle()) ? null : createQrService.getTitle());
        qrServiceEntity.setServiceType(StringUtils.isEmpty(createQrService.getServiceType()) ? null : createQrService.getServiceType());
        qrServiceEntity.setStatus(StringUtils.isEmpty(createQrService.getStatus()) ? StatusActivate.STATUS_ACTIVATED.getStatus() : createQrService.getStatus());
        qrServiceEntity.setCreatedAt(DateTimeUtil.getNow());
        qrServiceEntity.setCreatedBy(Flag.userFlag.getUserName());
        return qrServiceEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<QrServiceDto> update(Long id, UpdateQrService updateQrService) throws Exception {
        QrServiceEntity qrServiceEntity = qrServiceDao.findByIdNotDeleted(id);
        if (qrServiceEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        qrServiceEntity = setUpdate(qrServiceEntity, updateQrService);
        QrServiceEntity save = qrServiceDao.save(qrServiceEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật thông tin Dịch vụ QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, QrServiceDto.class));
    }

    private QrServiceEntity setUpdate(QrServiceEntity qrServiceEntity, UpdateQrService updateQrService) {
        if (!StringUtils.isEmpty(updateQrService.getTitle())) {
            qrServiceEntity.setTitle(updateQrService.getTitle());
        }
        if (!StringUtils.isEmpty(updateQrService.getServiceType())) {
            qrServiceEntity.setServiceType(updateQrService.getServiceType());
        }
        if (!StringUtils.isEmpty(updateQrService.getStatus())) {
            qrServiceEntity.setStatus(updateQrService.getStatus());
        }
        qrServiceEntity.setUpdatedAt(DateTimeUtil.getNow());
        qrServiceEntity.setUpdatedBy(Flag.userFlag.getUserName());
        return qrServiceEntity;
    }

    @Override
    public ResponeData<QrServiceDto> detail(Long id) throws Exception {
        QrServiceEntity qrServiceEntity = qrServiceDao.findByIdNotDeleted(id);
        if (qrServiceEntity == null) {
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
                " \n Chi tiết Dịch vụ QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(qrServiceEntity, QrServiceDto.class));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> delete(Long id) throws Exception {
        QrServiceEntity qrServiceEntity = qrServiceDao.findByIdNotDeleted(id);
        if (qrServiceEntity != null) {

//            qrServiceEntity.setDeletedAt(DateTimeUtil.getNow());
//            qrServiceDao.save(qrServiceEntity);
            qrServiceDao.delete(qrServiceEntity);

            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa Dịch vụ QR");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }

}
