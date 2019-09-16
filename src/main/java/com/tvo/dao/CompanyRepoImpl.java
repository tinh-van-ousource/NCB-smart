package com.tvo.dao;

import javax.persistence.EntityManager;

public class CompanyRepoImpl implements CompanyRepoCustom {

    private EntityManager em;

    public CompanyRepoImpl(EntityManager em) {
        this.em = em;
    }


}
