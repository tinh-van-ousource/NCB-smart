package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.UserChangePasswordReqDto;
import com.tvo.controllerDto.UserSearchModel;
import com.tvo.controllerDto.UserUpdateReqDto;
import com.tvo.controllerDto.UserUpdateStatusReqDto;
import com.tvo.dao.RoleRepo;
import com.tvo.dao.UserRepo;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.UserResDto;
import com.tvo.enums.StatusActivate;
import com.tvo.enums.UserChangePasswordStatus;
import com.tvo.model.Role;
import com.tvo.model.User;
import com.tvo.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public Page<UserResDto> findAllUser(Pageable pageable) {
        Page<User> users = userRepo.findAll(pageable);
        List<UserResDto> userDto = ModelMapperUtils.mapAll(users.getContent(), UserResDto.class);
        return new PageImpl<>(userDto, pageable, users.getTotalElements());
    }

    public UserResDto createUser(CreateUserRequest request) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepo.findByUserName(request.getUserName());
        if (user != null) {
            return null;
        }

        user = ModelMapperUtils.map(request, User.class);

        Role role = roleRepo.findById(request.getRoleId()).orElse(null);
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        user.setPassChange(UserChangePasswordStatus.NOT_YET.getType());
        user.setCountLoginFail(0);
        user.setUpdatedBy(currentUserName);

        User save = userRepo.save(user);
        return ModelMapperUtils.map(save, UserResDto.class);
    }

    @Override
    public Page<UserResDto> searchUser(UserSearchModel searchModel, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<User> query = cb.createQuery(User.class);
        Object[] queryObjs = this.createUserRootPersist(cb, query, searchModel);
        query.select((Root<User>) queryObjs[0]);
        query.where((Predicate[]) queryObjs[1]);
        TypedQuery<User> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<User> objects = typedQuery.getResultList();
        List<UserResDto> UserDtos = ModelMapperUtils.mapAll(objects, UserResDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(User.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(UserDtos, pageable, total);
    }

    private Object[] createUserRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, UserSearchModel resource) {
        final Root<User> rootPersist = query.from(User.class);
        final List<Predicate> predicates = new ArrayList<>(6);

        if (resource.getBranchCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBranchCode().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("branchCode"), resource.getBranchCode())));
        }

        if (resource.getFullName() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getFullName().trim())) {
            predicates.add(cb.and(cb.like(cb.lower(
                    rootPersist.get("fullName")),
                    "%" + resource.getFullName().toLowerCase() + "%")));
        }

        if (resource.getTransactionCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTransactionCode().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("transactionCode"), resource.getTransactionCode())));
        }

        if (resource.getUserName() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getUserName().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("userName"), resource.getUserName())));
        }

        if (resource.getFromDate() != null && resource.getToDate() != null) {
            // den_ngay >= toDate >= tu_ngay
            predicates.add(cb.and(
                    cb.between(rootPersist.<Date>get("createdDate"), resource.getFromDate(), resource.getToDate())));
        } else if (resource.getFromDate() != null) {
            // toDate >= tu_ngay
            predicates.add(cb.greaterThan(rootPersist.<Date>get("createdDate"), resource.getFromDate()));
        } else if (resource.getToDate() != null) {
            // toDate >= den_ngay
            predicates.add(cb.lessThan(rootPersist.<Date>get("createdDate"), resource.getToDate()));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public ContentResDto getUserDetail(String username) {
        ContentResDto contentResDto = new ContentResDto();
        User user = userRepo.findByUserName(username);
        contentResDto.setContent(user);
        return contentResDto;
    }

    @Override
    public Boolean deleteUser(String username) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepo.findByUserName(username);
        if (user != null) {
            user.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
            user.setUpdatedBy(currentUserName);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean changeUserPassword(UserChangePasswordReqDto userChangePasswordReqDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepo.findByUserName(userChangePasswordReqDto.getUsername());
        // edited user must exist
        if (user != null) {
            // edited username must equal current auditor
            if (!currentUserName.equals(user.getUserName())) {
                return false;
            }

            // old password must match input old password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean isMatch = encoder.matches(userChangePasswordReqDto.getOldPassword(), user.getPassword());
            if (!isMatch) {
                return false;
            }

            user.setPassChange(UserChangePasswordStatus.CHANGED.getType());
            user.setPassword(passwordEncoder.encode(userChangePasswordReqDto.getNewPassword()));
            user.setUpdatedBy(userChangePasswordReqDto.getUsername());
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public ContentResDto update(UserUpdateReqDto userDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ContentResDto contentResDto = new ContentResDto();
        User user = userRepo.findByUserName(userDto.getUsername());
        // edited user must exist
        if (user != null) {
            user.setBranchCode(userDto.getBranchCode());
            user.setTransactionCode(userDto.getTransactionCode());
            user.setFullName(userDto.getFullName());
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            user.setUpdatedBy(currentUserName);

            contentResDto.setContent(userRepo.save(user));
            return contentResDto;
        } else {
            contentResDto.setContent(null);
            return contentResDto;
        }
    }

    @Override
    public ContentResDto updateStatus(UserUpdateStatusReqDto userDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ContentResDto contentResDto = new ContentResDto();
        User user = userRepo.findByUserName(userDto.getUsername());
        // edited user must exist
        if (user != null) {
            user.setStatus(userDto.getStatus());
            user.setUpdatedBy(currentUserName);
            if (userDto.getStatus().equals(StatusActivate.STATUS_ACTIVATED.getStatus())) {
                user.setCountLoginFail(0);
            }
            contentResDto.setContent(userRepo.save(user));
            return contentResDto;
        }
        contentResDto.setContent(null);
        return contentResDto;
    }

}
