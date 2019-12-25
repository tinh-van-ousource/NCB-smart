package com.tvo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.tvo.model.CompanyEntity;

public class CompanyRepoImpl implements CompanyRepoCustom {

    private EntityManager em;

    public CompanyRepoImpl(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("unchecked")
	public List<CompanyEntity> findCompanyEntities(String compCode, String compName, String mcn, String mp) {
    	List<CompanyEntity> companyEntities;
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT c FROM CompanyEntity c WHERE 1 = 1");
    	if (!StringUtils.isEmpty(compCode)) {
    		sb.append(" AND lower(c.key.compCode) LIKE :compCode");
		}
    	if (!StringUtils.isEmpty(compName)) {
    		sb.append(" AND lower(c.compName) LIKE :compName");
		}
    	if (!StringUtils.isEmpty(mcn)) {
    		sb.append(" AND lower(c.key.mcn) LIKE :mcn");
		}
    	if (!StringUtils.isEmpty(mp)) {
    		sb.append(" AND lower(c.key.mp) LIKE :mp");
		}
    	sb.append(" ORDER BY c.compName ASC");
    	
    	Query query = em.createQuery(sb.toString());
    	if (!StringUtils.isEmpty(compCode)) {
    		query.setParameter("compCode", "%" + compCode.toLowerCase() + "%");
		}
    	if (!StringUtils.isEmpty(compName)) {
    		query.setParameter("compName", "%" + compName.toLowerCase() + "%");
		}
    	if (!StringUtils.isEmpty(mcn)) {
    		query.setParameter("mcn", "%" + mcn.toLowerCase() + "%");
		}
    	if (!StringUtils.isEmpty(mp)) {
    		query.setParameter("mp", "%" + mp.toLowerCase() + "%");
		}
    	companyEntities = query.getResultList();
    	return companyEntities;
    }
}
