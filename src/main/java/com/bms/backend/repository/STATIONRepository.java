package com.bms.backend.repository;

import com.bms.backend.domain.STATION;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the STATION entity.
 */
@SuppressWarnings("unused")
@Repository
public interface STATIONRepository extends JpaRepository<STATION,Long> {
    
}
