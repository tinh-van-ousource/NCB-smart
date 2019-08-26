/**
 * 
 */
package com.tvo.dao;

import com.tvo.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace
 *
 */
@Repository
public interface BranchDao extends JpaRepository<Branch, Integer> {
	public Branch findByBranchName(String branchName);
}
