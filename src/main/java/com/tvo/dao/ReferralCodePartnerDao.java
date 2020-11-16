package com.tvo.dao;

import com.tvo.model.QrServiceEntity;
import com.tvo.model.ReferFriendRegistrationEntity;
import com.tvo.model.ReferralCodePartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Repository
public interface ReferralCodePartnerDao extends JpaRepository<ReferralCodePartnerEntity, Long> {
    @Query("SELECT rf FROM ReferralCodePartnerEntity rf WHERE rf.id = ?1 AND rf.deletedAt IS NULL")
    ReferralCodePartnerEntity findByIdNotDeleted(Long id);
    @Query("SELECT rf FROM ReferralCodePartnerEntity rf WHERE rf.partnerCode = ?1 AND rf.deletedAt IS NULL")
    ReferralCodePartnerEntity findByReferralCodeNotDeleted(String partnerCode);
}
