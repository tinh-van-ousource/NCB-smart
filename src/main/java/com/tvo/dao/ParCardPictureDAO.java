package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.ParCardPiture;
@Repository
public interface ParCardPictureDAO extends JpaRepository<ParCardPiture, Long> {

}
