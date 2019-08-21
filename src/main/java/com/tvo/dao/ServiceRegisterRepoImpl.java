package com.tvo.dao;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.model.ServiceRegisterEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author NgocDC
 */
public class ServiceRegisterRepoImpl implements ServiceRegisterRepoCustom {

    private EntityManager em;

    public ServiceRegisterRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ServiceRegisterEntity> retrieveListServiceRegister(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT sr ");
        queryString.append(" FROM ServiceRegisterEntity sr ");
        queryString.append(" WHERE ");
        queryString.append(" 1 = 1 ");

        if (serviceRegisterSearchReqDto.getCompCode() != null) {
            queryString.append(" AND sr.compCode = :compCode ");
        }

        if (serviceRegisterSearchReqDto.getIdCard() != null) {
            queryString.append(" AND sr.idCard = :idCard ");
        }

        if (serviceRegisterSearchReqDto.getType() != null) {
            queryString.append(" AND sr.type = :type ");
        }
;
        if (serviceRegisterSearchReqDto.getService() != null) {
            queryString.append(" AND sr.service = :service ");
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            queryString.append(" AND sr.status = :status ");
        }

        queryString.append(" ORDER BY sr.requestDate ");

        TypedQuery<ServiceRegisterEntity> query = em.createQuery(queryString.toString(), ServiceRegisterEntity.class);

        if (serviceRegisterSearchReqDto.getCompCode() != null) {
            query.setParameter("compCode", serviceRegisterSearchReqDto.getCompCode());
        }

        if (serviceRegisterSearchReqDto.getIdCard() != null) {
            query.setParameter("idCard", serviceRegisterSearchReqDto.getIdCard());
        }

        if (serviceRegisterSearchReqDto.getType() != null) {
            query.setParameter("type", serviceRegisterSearchReqDto.getType().toString());
        }

        if (serviceRegisterSearchReqDto.getService() != null) {
            query.setParameter("service", serviceRegisterSearchReqDto.getService().toString());
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            query.setParameter("status", serviceRegisterSearchReqDto.getStatus().toString());
        }

        query.setFirstResult(AppConstant.getOffset(serviceRegisterSearchReqDto.getPage(), serviceRegisterSearchReqDto.getSize()))
                .setMaxResults(serviceRegisterSearchReqDto.getSize());

        return query.getResultList();
    }

    @Override
    public Long retrieveListServiceRegisterCount(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT count(sr.id) ");
        queryString.append(" FROM ServiceRegisterEntity sr ");
        queryString.append(" WHERE ");
        queryString.append(" 1 = 1 ");

        if (serviceRegisterSearchReqDto.getCompCode() != null) {
            queryString.append(" AND sr.compCode = :compCode ");
        }

        if (serviceRegisterSearchReqDto.getIdCard() != null) {
            queryString.append(" AND sr.idCard = :idCard ");
        }

        if (serviceRegisterSearchReqDto.getType() != null) {
            queryString.append(" AND sr.type = :type ");
        }
        ;
        if (serviceRegisterSearchReqDto.getService() != null) {
            queryString.append(" AND sr.service = :service ");
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            queryString.append(" AND sr.status = :status ");
        }

        TypedQuery<Long> query = em.createQuery(queryString.toString(), Long.class);

        if (serviceRegisterSearchReqDto.getCompCode() != null) {
            query.setParameter("compCode", serviceRegisterSearchReqDto.getCompCode());
        }

        if (serviceRegisterSearchReqDto.getIdCard() != null) {
            query.setParameter("idCard", serviceRegisterSearchReqDto.getIdCard());
        }

        if (serviceRegisterSearchReqDto.getType() != null) {
            query.setParameter("type", serviceRegisterSearchReqDto.getType().toString());
        }

        if (serviceRegisterSearchReqDto.getService() != null) {
            query.setParameter("service", serviceRegisterSearchReqDto.getService().toString());
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            query.setParameter("status", serviceRegisterSearchReqDto.getStatus().toString());
        }

        return query.getSingleResult();
    }
}
