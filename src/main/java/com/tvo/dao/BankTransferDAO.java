package com.tvo.dao;

import com.tvo.model.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface BankTransferDAO extends JpaRepository<BankTransfer, String> {
	@Query("select bt from BankTransfer bt where bt.bankCode IS NOT NULL")
	List<BankTransfer> findAll();
	
	public BankTransfer findByBankName(String bankName);
}
