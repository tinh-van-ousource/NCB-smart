package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.NcbFeedback;

@Repository
public interface NcbFeedbackDao extends JpaRepository<NcbFeedback, Long> {

}
