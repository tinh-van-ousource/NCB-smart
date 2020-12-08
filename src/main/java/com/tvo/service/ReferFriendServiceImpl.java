package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.dao.DatCfmastDao;
import com.tvo.dao.ReferFriendConfigurationDao;
import com.tvo.dto.ReferFriendConfigurationDto;
import com.tvo.dto.ReferFriendRegistrationDto;
import com.tvo.model.ReferFriendConfigurationEntity;
import com.tvo.model.ReferFriendRegistrationEntity;
import com.tvo.request.SearchReferFriendRequest;
import com.tvo.request.UpdateReferFriendConfiguration;
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
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Service
public class ReferFriendServiceImpl implements ReferFriendService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private ReferFriendConfigurationDao referFriendConfigurationDao;

    @Autowired
    private DatCfmastDao datCfmastDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<ReferFriendConfigurationDto>> search(Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ReferFriendConfigurationEntity> query = cb.createQuery(ReferFriendConfigurationEntity.class);
        Object[] queryObjs = this.createReferFriendConfigurationRootPersist(cb, query);
        Root<ReferFriendConfigurationEntity> root = (Root<ReferFriendConfigurationEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<ReferFriendConfigurationEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ReferFriendConfigurationEntity> objects = typedQuery.getResultList();
        List<ReferFriendConfigurationDto> friendConfigurationDtos = ModelMapperUtils.mapAll(objects, ReferFriendConfigurationDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ReferFriendConfigurationEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Danh sách giới thiệu bạn bè.");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(friendConfigurationDtos, pageable, total));
    }

    private Object[] createReferFriendConfigurationRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query) {
        final Root<ReferFriendConfigurationEntity> rootPersist = query.from(ReferFriendConfigurationEntity.class);
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.and(cb.isNull(rootPersist.<LocalDateTime>get("deletedAt"))));

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<ReferFriendConfigurationDto> create(UpdateReferFriendConfiguration friendsConfiguration) throws Exception {
        ReferFriendConfigurationEntity configurationEntity = setReferFriendConfigurationEntity(friendsConfiguration);
        ReferFriendConfigurationEntity save = referFriendConfigurationDao.save(configurationEntity);
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
                " \n Tạo mới giới thiệu bạn bè.");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, ReferFriendConfigurationDto.class));
    }

    private ReferFriendConfigurationEntity setReferFriendConfigurationEntity(UpdateReferFriendConfiguration friendsConfiguration) {
        ReferFriendConfigurationEntity entity = new ReferFriendConfigurationEntity();
        entity.setTitle(StringUtils.isEmpty(friendsConfiguration.getTitle()) ? null : friendsConfiguration.getTitle());
        entity.setInstruction(StringUtils.isEmpty(friendsConfiguration.getInstruction()) ? null : friendsConfiguration.getInstruction());
        entity.setObjectUserType(StringUtils.isEmpty(friendsConfiguration.getObjectUserType()) ? null : friendsConfiguration.getObjectUserType());
        entity.setUrlPromotion(StringUtils.isEmpty(friendsConfiguration.getUrlPromotion()) ? null : friendsConfiguration.getUrlPromotion());
        entity.setUrlBanner(StringUtils.isEmpty(friendsConfiguration.getUrlBanner()) ? null : friendsConfiguration.getUrlBanner());
        entity.setProvider(StringUtils.isEmpty(friendsConfiguration.getProvider()) ? null : friendsConfiguration.getProvider());
        entity.setCreatedAt(DateTimeUtil.getNow());
        entity.setStatus("1");
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<ReferFriendConfigurationDto> update(Long id, UpdateReferFriendConfiguration friendsConfiguration) throws Exception {
        ReferFriendConfigurationEntity configurationEntity = referFriendConfigurationDao.findByIdNotDeleted(id);
        if (configurationEntity == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        configurationEntity = setUpdate(configurationEntity, friendsConfiguration);
        ReferFriendConfigurationEntity save = referFriendConfigurationDao.save(configurationEntity);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật giới thiệu bạn bè.");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(save, ReferFriendConfigurationDto.class));
    }

    private ReferFriendConfigurationEntity setUpdate(ReferFriendConfigurationEntity configurationEntity,
                                                     UpdateReferFriendConfiguration friendsConfiguration) {
        if (!StringUtils.isEmpty(friendsConfiguration.getTitle())) {
            configurationEntity.setTitle(friendsConfiguration.getTitle());
        }
        if (!StringUtils.isEmpty(friendsConfiguration.getInstruction())) {
            configurationEntity.setInstruction(friendsConfiguration.getInstruction());
        }
        if (!StringUtils.isEmpty(friendsConfiguration.getObjectUserType())) {
            configurationEntity.setObjectUserType(friendsConfiguration.getObjectUserType());
        }
        if (!StringUtils.isEmpty(friendsConfiguration.getUrlPromotion())) {
            configurationEntity.setUrlPromotion(friendsConfiguration.getUrlPromotion());
        }
        if (!StringUtils.isEmpty(friendsConfiguration.getUrlBanner())) {
            configurationEntity.setUrlBanner(friendsConfiguration.getUrlBanner());
        }
        if (!StringUtils.isEmpty(friendsConfiguration.getProvider())) {
            configurationEntity.setProvider(friendsConfiguration.getProvider());
        }
        if (!StringUtils.isEmpty(friendsConfiguration.getStatus())) {
            configurationEntity.setStatus(friendsConfiguration.getStatus());
        }
        return configurationEntity;
    }

    @Override
    public ResponeData<ReferFriendConfigurationDto> detail(Long id) throws Exception {
        ReferFriendConfigurationEntity configurationEntity = referFriendConfigurationDao.findByIdNotDeleted(id);
        if (configurationEntity == null) {
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
                " \n Chi tiết giới thiệu bạn bè.");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ModelMapperUtils.map(configurationEntity, ReferFriendConfigurationDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> delete(Long id) throws Exception {
        ReferFriendConfigurationEntity configurationEntity = referFriendConfigurationDao.findByIdNotDeleted(id);
        if (configurationEntity != null) {
            referFriendConfigurationDao.delete(configurationEntity);
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                    "\n Account :" + Flag.userFlag.getUserName() +
                    "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                    " \n Địa chỉ IP đăng nhập : " + ip +
                    " \n Hostname : " + hostname +
                    " \n Xóa giới thiệu bạn bè.");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }

    @Override
    public ResponeData<Page<ReferFriendRegistrationDto>> searchReferFriend(SearchReferFriendRequest search
            , Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ReferFriendRegistrationEntity> query = cb.createQuery(ReferFriendRegistrationEntity.class);
        Object[] queryObjs = this.createReferFriendRegistrationRootPersist(cb, query, search);
        Root<ReferFriendRegistrationEntity> root = (Root<ReferFriendRegistrationEntity>) queryObjs[0];
        query.orderBy(cb.desc(root.get("createdAt")));
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<ReferFriendRegistrationEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ReferFriendRegistrationEntity> objects = typedQuery.getResultList();
        List<ReferFriendRegistrationDto> friendRegistrationDtos = ModelMapperUtils.mapAll(objects, ReferFriendRegistrationDto.class);
        List<ReferFriendRegistrationDto> friendRegistrationDtoNews = new ArrayList<>();
        for (ReferFriendRegistrationDto registrationDto : friendRegistrationDtos) {
            String userRootName = datCfmastDao.findUserNameByCifNo(registrationDto.getRootUserCif());
            registrationDto.setRootUserName(userRootName);
            Long referFriendConfigId = registrationDto.getReferFriendConfigId();
            ReferFriendConfigurationEntity configurationEntity = referFriendConfigurationDao.findByIdNotDeleted(referFriendConfigId);
            if(configurationEntity != null){
                friendRegistrationDtoNews.add(registrationDto);
            }
        }

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ReferFriendRegistrationEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Danh sách bạn bè.");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(friendRegistrationDtoNews, pageable, total));
    }

    private Object[] createReferFriendRegistrationRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
                                                              SearchReferFriendRequest resource) {
        final Root<ReferFriendRegistrationEntity> rootPersist = query.from(ReferFriendRegistrationEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getReferFriendConfigId() != null) {
            predicates.add(cb.and(cb.equal(cb.upper(rootPersist.get("referFriendConfigId")), +resource.getReferFriendConfigId())));
        }
        if (resource.getRootUserCif() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getRootUserCif().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("rootUserCif")), "%" + resource.getRootUserCif().toUpperCase() + "%")));
        }
        if (resource.getTargetUserCif() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTargetUserCif().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("targetUserCif")), "%" + resource.getTargetUserCif().toUpperCase() + "%")));
        }
        if (resource.getStatus() != null) {
            predicates.add(cb.and(cb.equal(cb.upper(rootPersist.get("status")), resource.getStatus())));
        }
        if (resource.getStart() != null) {
            predicates.add(cb.and(cb.greaterThanOrEqualTo(rootPersist.get("createdAt"), resource.getStart())));
        }
        if (resource.getEnd() != null) {
            predicates.add(cb.and(cb.lessThanOrEqualTo(rootPersist.get("createdAt"), resource.getEnd())));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }
}
