package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.dao.QrMerchantDao;
import com.tvo.dto.QrMerchantDto;
import com.tvo.model.QrMerchantEntity;
import com.tvo.request.QrMerchantRequest;
import com.tvo.request.QrMerchantCreateRequest;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author thanglt on 8/31/2020
 * @version 1.0
 */
@Service
public class QrMerchantServiceImpl implements QrMerchantService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private QrMerchantDao qrMerchantDao;

    @Autowired
    private QrMerchantService qrMerchantService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<QrMerchantDto>> search(String search, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<QrMerchantEntity> query = cb.createQuery(QrMerchantEntity.class);
        Object[] queryObjs = this.createQrMerchantRootPersist(cb, query, search);
        Root<QrMerchantEntity> root = (Root<QrMerchantEntity>) queryObjs[0];
        query.orderBy(cb.asc(root.get("name")));
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<QrMerchantEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<QrMerchantEntity> objects = typedQuery.getResultList();
        List<QrMerchantDto> qrMerchantDtos = ModelMapperUtils.mapAll(objects, QrMerchantDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(QrMerchantEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tìm kiếm nhà cung cấp QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(qrMerchantDtos, pageable, total));
    }

    private Object[] createQrMerchantRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, String search) {
        final Root<QrMerchantEntity> rootPersist = query.from(QrMerchantEntity.class);
        final List<Predicate> predicates = new ArrayList<>();
        if (search != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(search.trim())) {
            predicates.add(cb.and(cb.or(cb.like(cb.upper(rootPersist.get("name")), "%" + search.toUpperCase() + "%"),
                    cb.like(cb.upper(rootPersist.get("address")), "%" + search.toUpperCase() + "%"))));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> create(QrMerchantCreateRequest qrMerchantCreateRequest) throws Exception {
        QrMerchantEntity qrMerchantEntity;
        List<QrMerchantEntity> qrMerchantEntities = new ArrayList<>();
        List<QrMerchantRequest> qrMerchantRequests = qrMerchantCreateRequest.getQrMerchants();
        if (qrMerchantCreateRequest != null && !(qrMerchantCreateRequest.getQrMerchants() == null || qrMerchantCreateRequest.getQrMerchants().isEmpty())) {
            for (QrMerchantRequest qrMerchantRequest : qrMerchantRequests) {
                QrMerchantEntity checkNameAndAddressExist = qrMerchantDao.findByName(qrMerchantRequest.getName().toUpperCase());
                if (checkNameAndAddressExist != null ) {
                    logger.warn("Name bị trùng \n" + "name: " + qrMerchantRequest.getName());
                    return new ResponeData<>(AppConstant.CITY_CREATE_DUPLICATE_ERROR_CODE, AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE, false);
                }
                qrMerchantEntity = ModelMapperUtils.map(qrMerchantRequest, QrMerchantEntity.class);
                qrMerchantEntity.setCreatedAt(DateTimeUtil.getNow());
                qrMerchantEntity.setCreatedBy(Flag.userFlag.getUserName());
                qrMerchantEntities.add(qrMerchantEntity);
            }
            qrMerchantDao.saveAll(qrMerchantEntities);
        } else {
            QrMerchantEntity checkNameAndAddress = qrMerchantDao.findByName(qrMerchantCreateRequest.getName().toUpperCase());
            if (checkNameAndAddress != null ) {
                logger.warn("Name bị trùng \n" + "name: " + qrMerchantCreateRequest.getName());
                return new ResponeData<>(AppConstant.CITY_CREATE_DUPLICATE_ERROR_CODE, AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE, false);
            }
            qrMerchantEntity = setCreate(qrMerchantCreateRequest);
            qrMerchantEntity.setCreatedAt(DateTimeUtil.getNow());
            qrMerchantEntity.setCreatedBy(Flag.userFlag.getUserName());
            qrMerchantDao.save(qrMerchantEntity);
        }
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tạo mới nhà cung cấp QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
    }

    private QrMerchantEntity setCreate(QrMerchantCreateRequest qrMerchantCreateRequest) {
        QrMerchantEntity qrMerchantEntity = new QrMerchantEntity();
        qrMerchantEntity.setName(StringUtils.isEmpty(qrMerchantCreateRequest.getName()) ? null : qrMerchantCreateRequest.getName());
        qrMerchantEntity.setAddress(StringUtils.isEmpty(qrMerchantCreateRequest.getAddress()) ? null : qrMerchantCreateRequest.getAddress());
        return qrMerchantEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<QrMerchantDto> update(Long id, QrMerchantRequest qrMerchantRequest) throws Exception {
        QrMerchantEntity qrMerchantEntity = qrMerchantDao.getOne(id);
        if (qrMerchantEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        QrMerchantEntity checkNameAndAddress = qrMerchantDao.findByName(qrMerchantRequest.getName().toUpperCase());
        if (checkNameAndAddress != null && !qrMerchantRequest.getName().equals(qrMerchantEntity.getName())
        ) {
            logger.warn("Name bị trùng \n" + "name: " + qrMerchantRequest.getName());
            return new ResponeData<>(AppConstant.CITY_CREATE_DUPLICATE_ERROR_CODE, AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE, null);
        }
        setUpdate(qrMerchantEntity, qrMerchantRequest);
        QrMerchantEntity save = qrMerchantDao.save(qrMerchantEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật thông tin nhà cung cấp QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, QrMerchantDto.class));
    }

    private void setUpdate(QrMerchantEntity qrMerchantEntity, QrMerchantRequest qrMerchantRequest) {
        if (!StringUtils.isEmpty(qrMerchantRequest.getName())) {
            qrMerchantEntity.setName(qrMerchantRequest.getName());
        }
        if (!StringUtils.isEmpty(qrMerchantRequest.getAddress())) {
            qrMerchantEntity.setAddress(qrMerchantRequest.getAddress());
        }
    }

    @Override
    public ResponeData<QrMerchantDto> detail(Long id) throws Exception {
        QrMerchantEntity qrMerchantEntity = qrMerchantDao.getOne(id);
        if (qrMerchantEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        QrMerchantDto qrMerchantDto = ModelMapperUtils.map(qrMerchantEntity, QrMerchantDto.class);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Chi tiết nhà cung cấp QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, qrMerchantDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> delete(Long id) throws Exception {
        QrMerchantEntity qrMerchantEntity = qrMerchantDao.getOne(id);
        if (qrMerchantEntity != null) {
            qrMerchantDao.delete(qrMerchantEntity);
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa nhà cung cấp QR");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }
}
