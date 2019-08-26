package com.tvo.dao;

import com.tvo.model.NcbFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NcbFeedbackDao extends JpaRepository<NcbFeedback, Long> {

}
