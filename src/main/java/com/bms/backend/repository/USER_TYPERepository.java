package com.bms.backend.repository;

import com.bms.backend.domain.USER_TYPE;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the USER_TYPE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface USER_TYPERepository extends JpaRepository<USER_TYPE,Long> {
    
}
