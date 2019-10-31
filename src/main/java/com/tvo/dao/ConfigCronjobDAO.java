package com.tvo.dao;

import com.tvo.model.ConfigCronjob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigCronjobDAO extends JpaRepository<ConfigCronjob, Long> {

}
