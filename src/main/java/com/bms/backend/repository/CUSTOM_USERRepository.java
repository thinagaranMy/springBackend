package com.bms.backend.repository;

import com.bms.backend.domain.CUSTOM_USER;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CUSTOM_USER entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CUSTOM_USERRepository extends JpaRepository<CUSTOM_USER,Long> {
    
}
