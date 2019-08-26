package com.tvo.dao;

import com.tvo.model.NcbGuideline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 9, 2019
 */
@Repository
public interface NcbGuidelineDao extends JpaRepository<NcbGuideline, Long> {

}
