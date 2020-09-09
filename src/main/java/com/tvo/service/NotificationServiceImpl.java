package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchNotificationDto;
import com.tvo.dao.NotificationDAO;
import com.tvo.dto.NotificationsDto;
import com.tvo.model.NotificationsEntity;
import com.tvo.model.QrServiceEntity;
import com.tvo.request.CreateNotificationRequest;
import com.tvo.request.UpdateNotificationRequest;
import com.tvo.response.ResponeData;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

@Service
public class NotificationServiceImpl implements NotificationService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<NotificationsDto>> search(SearchNotificationDto searchNotificationDto, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<NotificationsEntity> query = cb.createQuery(NotificationsEntity.class);
        Object[] queryObjs = this.createNotificationRootPersist(cb, query, searchNotificationDto);
        Root<NotificationsEntity> root = (Root<NotificationsEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<NotificationsEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<NotificationsEntity> objects = typedQuery.getResultList();
        List<NotificationsDto> notificationDtos = ModelMapperUtils.mapAll(objects, NotificationsDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(NotificationsEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tìm kiếm Dịch vụ Notifications");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(notificationDtos, pageable, total));
    }

    private Object[] createNotificationRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchNotificationDto resource){
        final Root<NotificationsEntity> rootPersist = query.from(NotificationsEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getTitle() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTitle().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("title")), "%" + resource.getTitle().toUpperCase() + "%")));
        }
        if (resource.getContent() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getContent().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("content")), "%" + resource.getContent().toUpperCase() + "%")));
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
    public ResponeData<NotificationsDto> create(CreateNotificationRequest createNotificationRequest) throws Exception {
        NotificationsEntity notificationsEntity = setCreate(createNotificationRequest);
        NotificationsEntity save = notificationDAO.save(notificationsEntity);

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
                " \n Tạo mới Dịch vụ Notifications");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, NotificationsDto.class));
    }

    private NotificationsEntity setCreate(CreateNotificationRequest createNotificationRequest) {
        NotificationsEntity notificationsEntity = new NotificationsEntity();
        notificationsEntity.setTitle(StringUtils.isEmpty(createNotificationRequest.getTitle()) ? null : createNotificationRequest.getTitle());
        notificationsEntity.setContent(StringUtils.isEmpty(createNotificationRequest.getContent()) ? null : createNotificationRequest.getContent());
        notificationsEntity.setRepeatType(StringUtils.isEmpty(createNotificationRequest.getRepeatType()) ? null : createNotificationRequest.getRepeatType().toString());
        notificationsEntity.setRepeatValue(StringUtils.isEmpty(createNotificationRequest.getRepeatValue()) ? null : createNotificationRequest.getRepeatValue());
        notificationsEntity.setReceiverUserId(StringUtils.isEmpty(createNotificationRequest.getReceiverUserId()) ? null : createNotificationRequest.getReceiverUserId());
        notificationsEntity.setObjectUserType(StringUtils.isEmpty(createNotificationRequest.getObjectUserType()) ? null : createNotificationRequest.getObjectUserType());
        notificationsEntity.setType(StringUtils.isEmpty(createNotificationRequest.getTitle()) ? null : createNotificationRequest.getType());
        notificationsEntity.setStatus(StringUtils.isEmpty(createNotificationRequest.getStatus()) ? null : createNotificationRequest.getStatus());
        notificationsEntity.setCreatedAt(DateTimeUtil.getNow());
        notificationsEntity.setCreatedBy(Flag.userFlag.getUserName());
        return notificationsEntity;
    }

    @Override
    public ResponeData<NotificationsDto> update(Long id, UpdateNotificationRequest updateNotificationRequest) throws Exception {
        NotificationsEntity notificationsEntity = notificationDAO.findByIdNotDeleted(id);
        if(notificationsEntity == null){
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        notificationsEntity = setUpdate(notificationsEntity,updateNotificationRequest);
        NotificationsEntity save = notificationDAO.save(notificationsEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật thông tin Dịch vụ QR");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, NotificationsDto.class));
    }

    private NotificationsEntity setUpdate(NotificationsEntity notificationsEntity, UpdateNotificationRequest updateNotificationRequest) {
        if (!StringUtils.isEmpty(updateNotificationRequest.getTitle())) {
            notificationsEntity.setTitle(updateNotificationRequest.getTitle());
        }
        if (!StringUtils.isEmpty(updateNotificationRequest.getContent())) {
            notificationsEntity.setContent(updateNotificationRequest.getContent());
        }
        if (!StringUtils.isEmpty(updateNotificationRequest.getRepeatType())) {
            notificationsEntity.setRepeatType(updateNotificationRequest.getRepeatType());
        }
        if (!StringUtils.isEmpty(updateNotificationRequest.getRepeatValue())) {
            notificationsEntity.setRepeatValue(updateNotificationRequest.getRepeatValue());
        }
        if (!StringUtils.isEmpty(updateNotificationRequest.getObjectUserType())) {
            notificationsEntity.setObjectUserType(updateNotificationRequest.getObjectUserType());
        }
        if (!StringUtils.isEmpty(updateNotificationRequest.getType())) {
            notificationsEntity.setType(updateNotificationRequest.getType());
        }
        if (!StringUtils.isEmpty(updateNotificationRequest.getStatus())) {
            notificationsEntity.setStatus(updateNotificationRequest.getType());
        }
        notificationsEntity.setUpdatedAt(DateTimeUtil.getNow());
        notificationsEntity.setUpdatedBy(Flag.userFlag.getUserName());
        return notificationsEntity;
    }

    @Override
    public ResponeData<NotificationsDto> details(Long id) throws Exception {
        NotificationsEntity notificationsEntity = notificationDAO.findByIdNotDeleted(id);
        if (notificationsEntity == null) {
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
                " \n Chi tiết Dịch vụ Notifications");

        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE,AppConstant.SYSTEM_SUCCESS_MESSAGE,ModelMapperUtils.map(notificationsEntity, NotificationsDto.class));
    }

    @Override
    public ResponeData<Boolean> delete(Long id) throws Exception {
        NotificationsEntity notificationsEntity = notificationDAO.findByIdNotDeleted(id);
        if (notificationsEntity != null) {
            notificationsEntity.setDeletedAt(DateTimeUtil.getNow().toString());
            notificationDAO.save(notificationsEntity);
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa Dịch vụ Notifications");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }
}
