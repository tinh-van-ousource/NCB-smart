package com.tvo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tvo.model.ParCardPiture;

@Repository
public interface ParCardPictureDAO extends JpaRepository<ParCardPiture, Long> {
	ParCardPiture findByFileName(String fileName);

	@Query("SELECT DISTINCT pp.linkUrl FROM ParCardPiture pp")
	List<String> getListLinkUrl();
}
