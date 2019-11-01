package com.tvo.dao;

import com.tvo.model.NcbBanner;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NcbBannerDao extends JpaRepository<NcbBanner, Long> {

    NcbBanner getByBannerCode(String bannerCode);
    
    @Query("SELECT DISTINCT b from NcbBanner b WHERE b.status = 'A' AND  b.bannerCode = :bannerCode")
	List<NcbBanner> getAllActiveBanner(@Param("bannerCode") String bannerCode);

}
