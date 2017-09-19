package com.bms.backend.repository;

import com.bms.backend.domain.STATE;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the STATE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface STATERepository extends JpaRepository<STATE,Long> {
    
}
