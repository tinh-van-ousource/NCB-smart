package com.tvo.dao;

import com.tvo.model.DatCfmast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author thanglt on 12/1/2020
 * @version 1.0
 */
public interface DatCfmastDao extends JpaRepository<DatCfmast, Long> {

    @Query("SELECT MAX(d.name1) FROM DatCfmast d WHERE d.cifno = ?1")
    String findUserNameByCifNo(String cifNo);

}
