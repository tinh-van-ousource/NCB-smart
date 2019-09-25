package com.tvo.dao;

import com.tvo.model.NcbBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NcbBannerDao extends JpaRepository<NcbBanner, Long> {

    NcbBanner getByBannerCode(String bannerCode);

}
