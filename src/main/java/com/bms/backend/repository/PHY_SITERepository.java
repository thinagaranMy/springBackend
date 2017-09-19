package com.bms.backend.repository;

import com.bms.backend.domain.PHY_SITE;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PHY_SITE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PHY_SITERepository extends JpaRepository<PHY_SITE,Long> {
    
}
