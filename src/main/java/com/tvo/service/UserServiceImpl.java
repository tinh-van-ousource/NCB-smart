/**
 * 
 */
package com.tvo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.tvo.dto.ContentResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.searchModel;
import com.tvo.dao.AppRoleDAO;
import com.tvo.dao.AppUserDAO;
import com.tvo.dao.BranchDao;
import com.tvo.dto.UserDto;
import com.tvo.model.Role;
import com.tvo.model.User;
import com.tvo.request.CreateUserRequest;

/**
 * @author Ace
 *
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	AppUserDAO userDao;

	@Autowired
	BranchDao branchDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	AppRoleDAO appRoleDAO;

	@Override
	public User getCurrenLogin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			return userDao.findByUserName(username);
		} else {
			String username = principal.toString();
			return userDao.findByUserName(username);
		}
	}

	@Override
	public UserDto update(UserDto userDto) {
		User user = userDao.findByUserName(userDto.getUserName());
		if (user == null) {
			return null;
		}
		if (userDto.getEmail() != null) {	
			user.setEmail(userDto.getEmail());
		}
		if (userDto.getFullName() != null) {
			user.setFullName(userDto.getFullName());
		}
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return ModelMapperUtils.map(userDao.save(user), UserDto.class);
	}

	@Override
	public Page<UserDto> findAllUser(Pageable pageable) {
		Page<User> users = userDao.findAll(pageable);
		List<UserDto> userDto = ModelMapperUtils.mapAll(users.getContent(), UserDto.class);
		return new PageImpl<>(userDto, pageable, users.getTotalElements());
	}

	public UserDto createUser(CreateUserRequest request) {
		User user = userDao.findByUserName(request.getUserName());
		if (user != null) {
			return null;
		}	
		Role role = appRoleDAO.findById(request.getRoleId()).orElse(null);
		user = ModelMapperUtils.map(request, User.class);
		user.setRole(role);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		User save = userDao.save(user);
		return ModelMapperUtils.map(save, UserDto.class);
	}
	
	@Override
	public Page<UserDto> searchUser(searchModel searchModel, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<User> query = cb.createQuery(User.class);
		Object[] queryObjs = this.createUserRootPersist(cb, query, searchModel);
		query.select((Root<User>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<User> typedQuery = this.entityManager.createQuery(query);
		typedQuery.setFirstResult((int)pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());
		final List<User> objects = typedQuery.getResultList();
		List<UserDto> UserDtos = ModelMapperUtils.mapAll(objects, UserDto.class);
		
		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
	    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//	    countQuery.select(cbTotal.count((Root<User>) queryObjs[0]));
	    countQuery.select(cbTotal.count(countQuery.from(User.class)));
	    countQuery.where((Predicate[]) queryObjs[1]);
	    Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(UserDtos, pageable, total);
	}

	private Object[] createUserRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, searchModel resource) {
		final Root<User> rootPersist = query.from(User.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		if (resource.getBranchCode() != null	
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBranchCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("branchCode"), resource.getBranchCode())));
		}
		if (resource.getFullName() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getFullName().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("fullName"), resource.getFullName())));
		}

		if (resource.getTransactionCode() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTransactionCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("transactionCode"), resource.getTransactionCode())));
		}

		if (resource.getRoleId() instanceof Integer) {
			predicates.add(cb.and(cb.equal(rootPersist.<Integer>get("roleId"), resource.getRoleId())));
		}
				
		if (resource.getStatus() != null) {
			predicates.add(cb.and(cb.equal(rootPersist.<AppConstant.Status>get("status"), resource.getStatus())));
		}

		if (resource.getFromDate() != null && resource.getToDate() != null) {
			// toDate>=tu_ngay>=fromDate or toDate>=getToDate>=fromDate
			predicates.add(cb.and(
					cb.between(rootPersist.<Date>get("createdDate"), resource.getFromDate(), resource.getToDate())));
		} else if (resource.getFromDate() != null) { // toDate >= tu_ngay
			predicates.add(cb.greaterThan(rootPersist.<Date>get("createdDate"), resource.getFromDate()));
		} else if (resource.getToDate() != null) {// toDate >= den_ngay
			predicates.add(cb.lessThan(rootPersist.<Date>get("createdDate"), resource.getToDate()));
		}

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public ContentResDto getUserDetail(Long id) {
		ContentResDto contentResDto = new ContentResDto();
		Optional<User> optionalUser = userDao.findById(id);
		optionalUser.ifPresent(user -> contentResDto.setContent(ModelMapperUtils.map(user, UserDto.class)));
		return contentResDto;
	}

	@Override
	public Boolean deleteUser(Long id) {
		Optional<User> optionalUser = userDao.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setRole(null);
			userDao.save(user);
			return true;
		}
		return false;
	}

}
