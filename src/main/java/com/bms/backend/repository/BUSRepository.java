package com.bms.backend.repository;

import com.bms.backend.domain.BUS;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BUS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BUSRepository extends JpaRepository<BUS,Long> {
    
}
