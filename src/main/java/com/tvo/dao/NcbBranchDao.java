package com.tvo.dao;

import com.tvo.model.NcbBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NcbBranchDao extends JpaRepository<NcbBranch, String>, JpaSpecificationExecutor<NcbBranch> {
	public NcbBranch findByDepartCode(String departCode);
}
