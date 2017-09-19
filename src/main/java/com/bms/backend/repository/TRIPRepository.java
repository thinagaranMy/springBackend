package com.bms.backend.repository;

import com.bms.backend.domain.TRIP;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TRIP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TRIPRepository extends JpaRepository<TRIP,Long> {
    
}
