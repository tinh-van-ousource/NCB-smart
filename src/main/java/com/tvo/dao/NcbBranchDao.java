package com.tvo.dao;

import com.tvo.model.NcbBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NcbBranchDao extends JpaRepository<NcbBranch, String>, JpaSpecificationExecutor<NcbBranch> {
	public NcbBranch findByDepartCode(String departCode);

	@Query("SELECT DISTINCT b.brnCode, b.branchName from NcbBranch b WHERE b.status = 'A' ORDER BY b.branchName")
	List<Object> retrieveAllActivatedBranch();

	@Query("SELECT DISTINCT b.departCode, b.departName from NcbBranch b WHERE b.status = 'A' ORDER BY b.departName")
	List<Object> retrieveAllActivatedDepart();
	
	@Query("SELECT DISTINCT c.key.compCode, c.compName from CompanyEntity c ")
	List<Object> getListCompCode();
	
}
