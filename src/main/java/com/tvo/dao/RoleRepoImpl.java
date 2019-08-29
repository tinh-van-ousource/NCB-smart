package com.tvo.dao;

import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.model.Role;

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

        if (roleSearchReqDto.getRoleName() != null) {
            queryString.append(" AND r.roleName = :roleName ");
        }

        if (roleSearchReqDto.getStatus() != null) {
            queryString.append(" AND r.status = :status ");
        }

        TypedQuery<Role> query = em.createQuery(queryString.toString(), Role.class);

        if (roleSearchReqDto.getRoleName() != null) {
            query.setParameter("roleName", roleSearchReqDto.getRoleName());
        }

        if (roleSearchReqDto.getStatus() != null) {
            query.setParameter("status", roleSearchReqDto.getStatus());
        }

        return query.getResultList();
    }
}
