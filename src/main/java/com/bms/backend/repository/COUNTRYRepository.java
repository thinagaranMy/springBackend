package com.bms.backend.repository;

import com.bms.backend.domain.COUNTRY;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the COUNTRY entity.
 */
@SuppressWarnings("unused")
@Repository
public interface COUNTRYRepository extends JpaRepository<COUNTRY,Long> {
    
}
