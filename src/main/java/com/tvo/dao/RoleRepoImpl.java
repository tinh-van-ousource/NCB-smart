package com.tvo.dao;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.model.Role;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RoleRepoImpl implements RoleRepoCustom {

    private EntityManager em;

    public RoleRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Role> search(RoleSearchReqDto roleSearchReqDto) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT r ");
        queryString.append(" FROM Role r ");
        queryString.append(" WHERE ");
        queryString.append(" 1 = 1 ");

        if (roleSearchReqDto.getRoleName() != null && !roleSearchReqDto.getRoleName().trim().equals(StringUtils.EMPTY)) {
            queryString.append(" AND r.roleName LIKE :roleName ");
        }

        if (roleSearchReqDto.getStatus() != null && !roleSearchReqDto.getStatus().trim().equals(StringUtils.EMPTY)) {
            queryString.append(" AND r.status = :status ");
        }

        queryString.append(" ORDER BY r.createdDate ");

        TypedQuery<Role> query = em.createQuery(queryString.toString(), Role.class);

        if (roleSearchReqDto.getRoleName() != null && !roleSearchReqDto.getRoleName().trim().equals(StringUtils.EMPTY)) {
            query.setParameter("roleName", "%" + roleSearchReqDto.getRoleName() + "%");
        }

        if (roleSearchReqDto.getStatus() != null && !roleSearchReqDto.getStatus().trim().equals(StringUtils.EMPTY)) {
            query.setParameter("status", roleSearchReqDto.getStatus());
        }

        query.setFirstResult(AppConstant.getOffset(roleSearchReqDto.getPage(), roleSearchReqDto.getSize()))
                .setMaxResults(roleSearchReqDto.getSize());

        return query.getResultList();
    }

    @Override
    public Long searchCount(RoleSearchReqDto roleSearchReqDto) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT COUNT(r.roleId) ");
        queryString.append(" FROM Role r ");
        queryString.append(" WHERE ");
        queryString.append(" 1 = 1 ");

        if (roleSearchReqDto.getRoleName() != null && !roleSearchReqDto.getRoleName().trim().equals(StringUtils.EMPTY)) {
            queryString.append(" AND r.roleName = :roleName ");
        }

        if (roleSearchReqDto.getStatus() != null && !roleSearchReqDto.getStatus().trim().equals(StringUtils.EMPTY)) {
            queryString.append(" AND r.status = :status ");
        }

        TypedQuery<Long> query = em.createQuery(queryString.toString(), Long.class);

        if (roleSearchReqDto.getRoleName() != null && !roleSearchReqDto.getRoleName().trim().equals(StringUtils.EMPTY)) {
            query.setParameter("roleName", roleSearchReqDto.getRoleName());
        }

        if (roleSearchReqDto.getStatus() != null && !roleSearchReqDto.getStatus().trim().equals(StringUtils.EMPTY)) {
            query.setParameter("status", roleSearchReqDto.getStatus());
        }

        return query.getSingleResult();
    }
}
