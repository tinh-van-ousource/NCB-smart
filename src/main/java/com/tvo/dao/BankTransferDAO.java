package com.tvo.dao;

import com.tvo.model.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransferDAO extends JpaRepository<BankTransfer, String> {

    BankTransfer findByBankCode(String bankName);

}
