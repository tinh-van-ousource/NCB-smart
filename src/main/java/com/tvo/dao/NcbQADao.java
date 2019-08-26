package com.tvo.dao;

import com.tvo.model.NcbQA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 9, 2019
 */
@Repository
public interface NcbQADao extends JpaRepository<NcbQA, Long> {

}
