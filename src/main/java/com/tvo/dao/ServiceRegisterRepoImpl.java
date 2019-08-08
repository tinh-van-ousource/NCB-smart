package com.tvo.dao;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ServiceRegisterReqDto;
import com.tvo.model.ServiceRegisterEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ServiceRegisterRepoImpl implements ServiceRegisterRepoCustom {

    private EntityManager em;

    public ServiceRegisterRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ServiceRegisterEntity> retrieveListServiceRegister(ServiceRegisterReqDto serviceRegisterReqDto) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT sr ");
        queryString.append(" FROM ServiceRegisterEntity sr ");
        queryString.append(" WHERE ");
        queryString.append(" 1 = 1 ");

        if (serviceRegisterReqDto.getCompCode() != null) {
            queryString.append(" AND sr.compCode = :compCode ");
        }

        if (serviceRegisterReqDto.getIdCard() != null) {
            queryString.append(" AND sr.idCard = :idCard ");
        }

        if (serviceRegisterReqDto.getType() != null) {
            queryString.append(" AND sr.type = :type ");
        }
;
        if (serviceRegisterReqDto.getService() != null) {
            queryString.append(" AND sr.service = :service ");
        }

        if (serviceRegisterReqDto.getStatus() != null) {
            queryString.append(" AND sr.status = :status ");
        }

        queryString.append(" ORDER BY sr.requestDate ");

        TypedQuery<ServiceRegisterEntity> query = em.createQuery(queryString.toString(), ServiceRegisterEntity.class);

        if (serviceRegisterReqDto.getCompCode() != null) {
            query.setParameter("compCode", serviceRegisterReqDto.getCompCode());
        }

        if (serviceRegisterReqDto.getIdCard() != null) {
            query.setParameter("idCard", serviceRegisterReqDto.getIdCard());
        }

        if (serviceRegisterReqDto.getType() != null) {
            query.setParameter("type", serviceRegisterReqDto.getType().toString());
        }

        if (serviceRegisterReqDto.getService() != null) {
            query.setParameter("service", serviceRegisterReqDto.getService().toString());
        }

        if (serviceRegisterReqDto.getStatus() != null) {
            query.setParameter("status", serviceRegisterReqDto.getStatus().toString());
        }

        query.setFirstResult(AppConstant.getOffset(serviceRegisterReqDto.getPage(), serviceRegisterReqDto.getSize()))
                .setMaxResults(serviceRegisterReqDto.getSize());

        return query.getResultList();
    }
}
