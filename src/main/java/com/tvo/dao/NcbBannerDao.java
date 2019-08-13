package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.NcbBanner;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 13, 2019
 */
@Repository
public interface NcbBannerDao extends JpaRepository<NcbBanner, Long> {

}
