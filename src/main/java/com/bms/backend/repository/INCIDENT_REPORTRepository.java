package com.bms.backend.repository;

import com.bms.backend.domain.INCIDENT_REPORT;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the INCIDENT_REPORT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface INCIDENT_REPORTRepository extends JpaRepository<INCIDENT_REPORT,Long> {
    
}
