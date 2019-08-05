/**
 * 
 */
package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.Transaction;



/**
 * @author Ace
 *
 */
@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer>{
		public Transaction findByTransactionName(String transactionName);
}
