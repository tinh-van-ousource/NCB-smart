package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchQrCouponDto;
import com.tvo.controllerDto.SearchUserNotificationSettingsDto;
import com.tvo.dao.UserNotificationSettingsDao;
import com.tvo.dto.QrCouponDto;
import com.tvo.dto.UserNotificationSettingsDto;
import com.tvo.model.CouponObjectUserEntity;
import com.tvo.model.NotificationObjectUserEntity;
import com.tvo.model.QrCouponsEntity;
import com.tvo.model.UserNotificationSettingsEntity;
import com.tvo.request.UpdateQrCouponRequest;
import com.tvo.request.UpdateUserNotificationSettingsRequest;
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
 * @author Nguyen Hoang Long on 9/15/2020
 * @created 9/15/2020
 * @project NCB-smart
 */
@Service
public class UserNotificationSettingsServiceImpl implements UserNotificationSettingsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserNotificationSettingsDao userNotificationSettingsDao;

    @Override
    public ResponeData<Page<UserNotificationSettingsDto>> search(SearchUserNotificationSettingsDto searchUserNotificationSettingsDto, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<UserNotificationSettingsEntity> query = cb.createQuery(UserNotificationSettingsEntity.class);
        Object[] queryObjs = this.createUserNotificationSettingsRootPersist(cb, query, searchUserNotificationSettingsDto);
        Root<UserNotificationSettingsEntity> root = (Root<UserNotificationSettingsEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<UserNotificationSettingsEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<UserNotificationSettingsEntity> objects = typedQuery.getResultList();
        List<UserNotificationSettingsDto> userNotificationSettingsDtos = ModelMapperUtils.mapAll(objects, UserNotificationSettingsDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(UserNotificationSettingsEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tìm kiếm Dịch vụ User Notification Setting");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(userNotificationSettingsDtos, pageable, total));
    }

    private Object[] createUserNotificationSettingsRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchUserNotificationSettingsDto resource) {
        final Root<UserNotificationSettingsEntity> rootPersist = query.from(UserNotificationSettingsEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getUserName() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getUserName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("userName")), "%" + resource.getUserName().toUpperCase() + "%")));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<UserNotificationSettingsDto> update(Long id,UpdateUserNotificationSettingsRequest updateUserNotificationSettingsRequest) throws Exception {
        UserNotificationSettingsEntity userNotificationSettingsEntity = userNotificationSettingsDao.findById(id).get();
        if (userNotificationSettingsEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        setUpdate(userNotificationSettingsEntity, updateUserNotificationSettingsRequest);
        UserNotificationSettingsEntity save = userNotificationSettingsDao.save(userNotificationSettingsEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật thông tin User Notification Setting");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, UserNotificationSettingsDto.class));
    }

    private void setUpdate(UserNotificationSettingsEntity userNotificationSettingsEntity, UpdateUserNotificationSettingsRequest updateUserNotificationSettingsRequest) {
        if (!StringUtils.isEmpty(updateUserNotificationSettingsRequest.getUserName())) {
            userNotificationSettingsEntity.setUserName(updateUserNotificationSettingsRequest.getUserName());
        }
        if (!StringUtils.isEmpty(updateUserNotificationSettingsRequest.getPushNotiSetting())) {
            userNotificationSettingsEntity.setPushNotiSetting(updateUserNotificationSettingsRequest.getPushNotiSetting());
        }
        if (!StringUtils.isEmpty(updateUserNotificationSettingsRequest.getSmsNotiSetting())) {
            userNotificationSettingsEntity.setSmsNotiSetting(updateUserNotificationSettingsRequest.getSmsNotiSetting());
        }
        if (!StringUtils.isEmpty(updateUserNotificationSettingsRequest.getDeviceId().toString())) {
            userNotificationSettingsEntity.setDeviceId(updateUserNotificationSettingsRequest.getDeviceId());
        }

        userNotificationSettingsEntity.setUpdatedAt(DateTimeUtil.getNow());
        userNotificationSettingsEntity.setUpdatedBy(Flag.userFlag.getUserName());
    }

    @Override
    public ResponeData<UserNotificationSettingsDto> details(Long id) throws Exception {
        UserNotificationSettingsEntity userNotificationSettingsEntity = userNotificationSettingsDao.findById(id).get();
        if (userNotificationSettingsEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        UserNotificationSettingsDto userNotificationSettingsDto = ModelMapperUtils.map(userNotificationSettingsEntity, UserNotificationSettingsDto.class);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Chi tiết User Notification Setting");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, userNotificationSettingsDto);
    }

}
