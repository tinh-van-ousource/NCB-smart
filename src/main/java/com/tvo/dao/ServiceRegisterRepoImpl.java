package com.tvo.dao;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.model.ServiceRegisterEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ServiceRegisterRepoImpl implements ServiceRegisterRepoCustom {
    @Override
    public List<ServiceRegisterEntity> retrieveListServiceRegister(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        return null;
    }

    @Override
    public Long retrieveListServiceRegisterCount(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        return null;
    }

//    private EntityManager em;
//
//    public ServiceRegisterRepoImpl(EntityManager em) {
//        this.em = em;
//    }
}
//    @Override
//    public List<ServiceRegisterEntity> retrieveListServiceRegister(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
//        StringBuilder queryString = new StringBuilder();
//        queryString.append("SELECT sr ");
//        queryString.append(" FROM ServiceRegisterEntity sr ");
//        queryString.append(" WHERE ");
//        queryString.append(" 1 = 1 ");
//
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())){
//            queryString.append(" AND sr.compCode = :compCode ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
//            queryString.append(" AND sr.idCard LIKE :idCard ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getType())) {
//            queryString.append(" AND sr.type = :type ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getService())) {
//            queryString.append(" AND sr.service = :service ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getStatus())) {
//            queryString.append(" AND sr.status = :status ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
//            queryString.append(" AND sr.requestDate >= :fromDate ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
//            queryString.append(" AND sr.requestDate <= :toDate ");
//        }
//        queryString.append(" ORDER BY sr.requestDate DESC ");
//
//        TypedQuery<ServiceRegisterEntity> query = em.createQuery(queryString.toString(), ServiceRegisterEntity.class);
//
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())) {
//            query.setParameter("compCode", serviceRegisterSearchReqDto.getCompCode());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
//            query.setParameter("idCard", "%" + serviceRegisterSearchReqDto.getIdCard() + "%");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getService())) {
//            query.setParameter("service", serviceRegisterSearchReqDto.getService());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getType())) {
//            query.setParameter("type", serviceRegisterSearchReqDto.getType());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getStatus())) {
//            query.setParameter("status", serviceRegisterSearchReqDto.getStatus());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
//            query.setParameter("fromDate", serviceRegisterSearchReqDto.getFromDate());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
//            query.setParameter("toDate", serviceRegisterSearchReqDto.getToDate());
//        }
//        query.setFirstResult(AppConstant
//                .getOffset(serviceRegisterSearchReqDto.getPage(), serviceRegisterSearchReqDto.getSize()))
//                .setMaxResults(serviceRegisterSearchReqDto.getSize());
//        return query.getResultList();
//    }

//    @Override
//    public Long retrieveListServiceRegisterCount(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
//        StringBuilder queryString = new StringBuilder();
//        queryString.append(" SELECT count(sr.id) ");
//        queryString.append(" FROM ServiceRegisterEntity sr ");
//        queryString.append(" WHERE ");
//        queryString.append(" 1 = 1 ");
//
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())){
//            queryString.append(" AND sr.compCode = :compCode ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
//            queryString.append(" AND sr.idCard = :idCard ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getService())) {
//            queryString.append(" AND sr.service = :service ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getType())) {
//            queryString.append(" AND sr.type = :type ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getStatus())) {
//            queryString.append(" AND sr.status = :status ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
//            queryString.append(" AND sr.requestDate >= :fromDate ");
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
//            queryString.append(" AND sr.requestDate <= :toDate ");
//        }
//        TypedQuery<Long> query = em.createQuery(queryString.toString(), Long.class);
//
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())){
//            query.setParameter("compCode", serviceRegisterSearchReqDto.getCompCode());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
//            query.setParameter("idCard", serviceRegisterSearchReqDto.getIdCard());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getService())) {
//            query.setParameter("service", serviceRegisterSearchReqDto.getService());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getType())) {
//            query.setParameter("type", serviceRegisterSearchReqDto.getType());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getStatus())) {
//            query.setParameter("status", serviceRegisterSearchReqDto.getStatus());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
//            query.setParameter("fromDate", serviceRegisterSearchReqDto.getFromDate());
//        }
//        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
//            query.setParameter("toDate", serviceRegisterSearchReqDto.getToDate());
//        }
//        return query.getSingleResult();
//    }
//}
