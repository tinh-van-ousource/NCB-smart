package com.tvo.dao;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.model.ServiceRegisterEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())){
            queryString.append(" AND sr.compCode = :compCode ");
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
            queryString.append(" AND sr.idCard = :idCard ");
        }

        if (serviceRegisterSearchReqDto.getService() != null) {
            queryString.append(" AND sr.service = :service ");
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            queryString.append(" AND sr.status = :status ");
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
            queryString.append(" AND TRUNC(sr.requestDate) >= :fromDate ");
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
            queryString.append(" AND TRUNC(sr.requestDate) <= :toDate ");
        }

        queryString.append(" ORDER BY sr.requestDate ");

        TypedQuery<ServiceRegisterEntity> query = em.createQuery(queryString.toString(), ServiceRegisterEntity.class);

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())){
            query.setParameter("compCode", serviceRegisterSearchReqDto.getCompCode());
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
            query.setParameter("idCard", serviceRegisterSearchReqDto.getIdCard());
        }

        if (serviceRegisterSearchReqDto.getService() != null) {
            query.setParameter("service", serviceRegisterSearchReqDto.getService().toString());
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            query.setParameter("status", serviceRegisterSearchReqDto.getStatus().toString());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
            query.setParameter("fromDate", LocalDate.parse(serviceRegisterSearchReqDto.getFromDate(), formatter));
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
            query.setParameter("toDate", LocalDate.parse(serviceRegisterSearchReqDto.getToDate(), formatter));
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

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())){
            queryString.append(" AND sr.compCode = :compCode ");
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
            queryString.append(" AND sr.idCard = :idCard ");
        }

        if (serviceRegisterSearchReqDto.getService() != null) {
            queryString.append(" AND sr.service = :service ");
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            queryString.append(" AND sr.status = :status ");
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
            queryString.append(" AND TRUNC(sr.requestDate) >= :fromDate ");
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
            queryString.append(" AND TRUNC(sr.requestDate) <= :toDate ");
        }

        TypedQuery<Long> query = em.createQuery(queryString.toString(), Long.class);

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getCompCode())){
            query.setParameter("compCode", serviceRegisterSearchReqDto.getCompCode());
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getIdCard())) {
            query.setParameter("idCard", serviceRegisterSearchReqDto.getIdCard());
        }

        if (serviceRegisterSearchReqDto.getService() != null) {
            query.setParameter("service", serviceRegisterSearchReqDto.getService().toString());
        }

        if (serviceRegisterSearchReqDto.getStatus() != null) {
            query.setParameter("status", serviceRegisterSearchReqDto.getStatus().toString());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getFromDate())) {
            query.setParameter("fromDate", LocalDate.parse(serviceRegisterSearchReqDto.getFromDate(), formatter));
        }

        if (StringUtils.isNotBlank(serviceRegisterSearchReqDto.getToDate())) {
            query.setParameter("toDate", LocalDate.parse(serviceRegisterSearchReqDto.getToDate(), formatter));
        }

        return query.getSingleResult();
    }
}
