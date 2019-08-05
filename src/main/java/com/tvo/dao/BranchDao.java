/**
 * 
 */
package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.Branch;

/**
 * @author Ace
 *
 */
@Repository
public interface BranchDao extends JpaRepository<Branch, Integer> {
	public Branch findByBranchName(String branchName);
}
