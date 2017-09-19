package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.CUSTOM_USER;

import com.bms.backend.repository.CUSTOM_USERRepository;
import com.bms.backend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CUSTOM_USER.
 */
@RestController
@RequestMapping("/api")
public class CUSTOM_USERResource {

    private final Logger log = LoggerFactory.getLogger(CUSTOM_USERResource.class);

    private static final String ENTITY_NAME = "cUSTOM_USER";

    private final CUSTOM_USERRepository cUSTOM_USERRepository;

    public CUSTOM_USERResource(CUSTOM_USERRepository cUSTOM_USERRepository) {
        this.cUSTOM_USERRepository = cUSTOM_USERRepository;
    }

    /**
     * POST  /c-ustom-users : Create a new cUSTOM_USER.
     *
     * @param cUSTOM_USER the cUSTOM_USER to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cUSTOM_USER, or with status 400 (Bad Request) if the cUSTOM_USER has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-ustom-users")
    @Timed
    public ResponseEntity<CUSTOM_USER> createCUSTOM_USER(@RequestBody CUSTOM_USER cUSTOM_USER) throws URISyntaxException {
        log.debug("REST request to save CUSTOM_USER : {}", cUSTOM_USER);
        if (cUSTOM_USER.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cUSTOM_USER cannot already have an ID")).body(null);
        }
        CUSTOM_USER result = cUSTOM_USERRepository.save(cUSTOM_USER);
        return ResponseEntity.created(new URI("/api/c-ustom-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-ustom-users : Updates an existing cUSTOM_USER.
     *
     * @param cUSTOM_USER the cUSTOM_USER to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cUSTOM_USER,
     * or with status 400 (Bad Request) if the cUSTOM_USER is not valid,
     * or with status 500 (Internal Server Error) if the cUSTOM_USER couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-ustom-users")
    @Timed
    public ResponseEntity<CUSTOM_USER> updateCUSTOM_USER(@RequestBody CUSTOM_USER cUSTOM_USER) throws URISyntaxException {
        log.debug("REST request to update CUSTOM_USER : {}", cUSTOM_USER);
        if (cUSTOM_USER.getId() == null) {
            return createCUSTOM_USER(cUSTOM_USER);
        }
        CUSTOM_USER result = cUSTOM_USERRepository.save(cUSTOM_USER);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cUSTOM_USER.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-ustom-users : get all the cUSTOM_USERS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cUSTOM_USERS in body
     */
    @GetMapping("/c-ustom-users")
    @Timed
    public List<CUSTOM_USER> getAllCUSTOM_USERS() {
        log.debug("REST request to get all CUSTOM_USERS");
        return cUSTOM_USERRepository.findAll();
    }

    /**
     * GET  /c-ustom-users/:id : get the "id" cUSTOM_USER.
     *
     * @param id the id of the cUSTOM_USER to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cUSTOM_USER, or with status 404 (Not Found)
     */
    @GetMapping("/c-ustom-users/{id}")
    @Timed
    public ResponseEntity<CUSTOM_USER> getCUSTOM_USER(@PathVariable Long id) {
        log.debug("REST request to get CUSTOM_USER : {}", id);
        CUSTOM_USER cUSTOM_USER = cUSTOM_USERRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cUSTOM_USER));
    }

    /**
     * DELETE  /c-ustom-users/:id : delete the "id" cUSTOM_USER.
     *
     * @param id the id of the cUSTOM_USER to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-ustom-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteCUSTOM_USER(@PathVariable Long id) {
        log.debug("REST request to delete CUSTOM_USER : {}", id);
        cUSTOM_USERRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
