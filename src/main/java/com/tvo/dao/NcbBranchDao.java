package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tvo.model.NcbBranch;

@Repository
public interface NcbBranchDao extends JpaRepository<NcbBranch, String>, JpaSpecificationExecutor<NcbBranch> {
	public NcbBranch findByBrnCode(String brnCode);
}
