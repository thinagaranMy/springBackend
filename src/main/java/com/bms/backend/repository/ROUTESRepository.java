package com.bms.backend.repository;

import com.bms.backend.domain.ROUTES;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ROUTES entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ROUTESRepository extends JpaRepository<ROUTES,Long> {
    
}
