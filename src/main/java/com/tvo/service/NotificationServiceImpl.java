package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.common.UserStoreException;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchNotificationDto;
import com.tvo.dao.DatUserProfileDao;
import com.tvo.dao.NotificationDAO;
import com.tvo.dao.NotificationObjectUserDao;
import com.tvo.dto.NotificationsDto;
import com.tvo.model.NotificationObjectUserEntity;
import com.tvo.model.NotificationsEntity;
import com.tvo.request.CreateNotificationRequest;
import com.tvo.request.UpdateNotificationRequest;
import com.tvo.request.UserNotifications;
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
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private NotificationObjectUserDao notificationObjectUserDao;

    @Autowired
    private DatUserProfileDao datUserProfileDao;

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

    private Object[] createNotificationRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchNotificationDto resource) {
        final Root<NotificationsEntity> rootPersist = query.from(NotificationsEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getSearch() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getSearch().trim())) {
            predicates.add(cb.and(cb.or(cb.like(cb.upper(rootPersist.get("title")), "%" + resource.getSearch().toUpperCase() + "%"),
                    cb.like(cb.upper(rootPersist.get("content")), "%" + resource.getSearch().toUpperCase() + "%"))));
        }
        if (resource.getRepeatType() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getRepeatType().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("repeatType"), resource.getRepeatType())));
        }
        if (resource.getObjectUserType() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getObjectUserType().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("objectUserType"), resource.getObjectUserType())));
        }
        if (resource.getStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
        }
        if (resource.getFromDate() != null) {
            predicates.add(cb.and(cb.greaterThanOrEqualTo(rootPersist.get("createdAt"), resource.getFromDate())));
        }
        if (resource.getFromDate() != null) {
            predicates.add(cb.and(cb.lessThanOrEqualTo(rootPersist.get("createdAt"), resource.getToDate())));
        }
        predicates.add(cb.and(cb.isNull(rootPersist.<LocalDateTime>get("deletedAt"))));
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

        if (save.getObjectUserType().equals("1") && !createNotificationRequest.getUserNotifications().isEmpty()) {
            List<NotificationObjectUserEntity> notificationObjectUserEntityList = setCreate(save.getId(), createNotificationRequest);
            notificationObjectUserDao.saveAll(notificationObjectUserEntityList);
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

    private List<NotificationObjectUserEntity> setCreate(Long notificationId, CreateNotificationRequest createNotificationRequest) throws Exception {
        List<String> datUserProfileList = datUserProfileDao.findAllUsrid().stream().map(String::toUpperCase).collect(Collectors.toList());
        List<NotificationObjectUserEntity> notificationObjectUserEntityList = new ArrayList<>();
        List<UserNotifications> userNotificationsList = createNotificationRequest.getUserNotifications();
        if (userNotificationsList != null && !userNotificationsList.isEmpty()) {
            NotificationObjectUserEntity notificationObjectUserEntity;
            for (UserNotifications userNotifications : userNotificationsList) {
                if (datUserProfileList.contains(userNotifications.getUserName().toUpperCase())) {
                    notificationObjectUserEntity = ModelMapperUtils.map(userNotifications, NotificationObjectUserEntity.class);
                    notificationObjectUserEntity.setNotificationId(notificationId);
                    notificationObjectUserEntity.setUserName(userNotifications.getUserName());
                    notificationObjectUserEntity.setCreatedAt(DateTimeUtil.getNow());
                    notificationObjectUserEntity.setCreatedBy(Flag.userFlag.getUserName());
                    notificationObjectUserEntityList.add(notificationObjectUserEntity);
                } else {
                    throw new UserStoreException(userNotifications.getUserName());
                }
            }
            return notificationObjectUserEntityList;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<NotificationsDto> update(Long id, UpdateNotificationRequest updateNotificationRequest) throws Exception {
        NotificationsEntity notificationsEntity = notificationDAO.findByIdNotDeleted(id);
        if (notificationsEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        notificationsEntity = setUpdate(notificationsEntity, updateNotificationRequest);
        NotificationsEntity save = notificationDAO.save(notificationsEntity);

        List<NotificationObjectUserEntity> objectUserEntityList = notificationObjectUserDao.findByNotificationId(notificationsEntity.getId());
        List<UserNotifications> userNotificationsList = ModelMapperUtils.mapAll(objectUserEntityList, UserNotifications.class);

        if (updateNotificationRequest.getObjectUserType().equals("1") && !updateNotificationRequest.getUserNotifications().isEmpty()) {
            List<NotificationObjectUserEntity> notificationObjectUserEntityList = setUpdate(save.getId(), updateNotificationRequest, userNotificationsList);
            notificationObjectUserDao.saveAll(notificationObjectUserEntityList);
        }

        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật thông tin Dịch vụ Notifications");
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
            notificationsEntity.setStatus(updateNotificationRequest.getStatus());
        }
        notificationsEntity.setUpdatedAt(DateTimeUtil.getNow());
        notificationsEntity.setUpdatedBy(Flag.userFlag.getUserName());
        return notificationsEntity;
    }

    private List<NotificationObjectUserEntity> setUpdate(Long notificationId, UpdateNotificationRequest updateNotificationRequest, List<UserNotifications> userNotificationsList) throws Exception {
        List<String> datUserProfileList = datUserProfileDao.findAllUsrid().stream().map(String::toUpperCase).collect(Collectors.toList());
        List<NotificationObjectUserEntity> notificationObjectUserEntityList = new ArrayList<>();
        List<UserNotifications> userNotificationsListRequest = updateNotificationRequest.getUserNotifications();

        if (userNotificationsListRequest != null && !userNotificationsListRequest.isEmpty()) {
            NotificationObjectUserEntity notificationObjectUserEntity;
            for (UserNotifications userNotifications : userNotificationsListRequest) {
                if (datUserProfileList.contains(userNotifications.getUserName().toUpperCase())) {
                    if (!userNotificationsList.contains(userNotifications)) {
                        notificationObjectUserEntity = ModelMapperUtils.map(userNotifications, NotificationObjectUserEntity.class);
                        notificationObjectUserEntity.setNotificationId(notificationId);
                        notificationObjectUserEntity.setUserName(userNotifications.getUserName());
                        notificationObjectUserEntity.setCreatedAt(DateTimeUtil.getNow());
                        notificationObjectUserEntity.setCreatedBy(Flag.userFlag.getUserName());
                        notificationObjectUserEntityList.add(notificationObjectUserEntity);
                    }
                } else {
                    throw new UserStoreException(userNotifications.getUserName());
                }
            }
            return notificationObjectUserEntityList;
        }
        return null;
    }

    @Override
    public ResponeData<NotificationsDto> details(Long id) throws Exception {
        NotificationsEntity notificationsEntity = notificationDAO.findByIdNotDeleted(id);
        List<NotificationObjectUserEntity> notificationObjectUserEntityList = notificationObjectUserDao.findByNotificationId(id);

        if (notificationsEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }

        NotificationsDto notificationsDto = ModelMapperUtils.map(notificationsEntity, NotificationsDto.class);
        if (notificationsEntity.getObjectUserType().equals("1")) {
            List<UserNotifications> userNotificationsList = ModelMapperUtils.mapAll(notificationObjectUserEntityList, UserNotifications.class);
            notificationsDto.setUserNotifications(userNotificationsList);
        }

        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Chi tiết Dịch vụ Notifications");

        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, notificationsDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> delete(Long id) throws Exception {
        NotificationsEntity notificationsEntity = notificationDAO.findById(id).get();
        if (notificationsEntity != null) {
//            notificationsEntity.setDeletedAt(DateTimeUtil.getNow().toString());
//            notificationDAO.save(notificationsEntity);
            notificationDAO.delete(notificationsEntity);
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa Dịch vụ Notifications");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }
}
