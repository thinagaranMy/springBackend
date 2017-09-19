package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.STATION;

import com.bms.backend.repository.STATIONRepository;
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
 * REST controller for managing STATION.
 */
@RestController
@RequestMapping("/api")
public class STATIONResource {

    private final Logger log = LoggerFactory.getLogger(STATIONResource.class);

    private static final String ENTITY_NAME = "sTATION";

    private final STATIONRepository sTATIONRepository;

    public STATIONResource(STATIONRepository sTATIONRepository) {
        this.sTATIONRepository = sTATIONRepository;
    }

    /**
     * POST  /s-tations : Create a new sTATION.
     *
     * @param sTATION the sTATION to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sTATION, or with status 400 (Bad Request) if the sTATION has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-tations")
    @Timed
    public ResponseEntity<STATION> createSTATION(@RequestBody STATION sTATION) throws URISyntaxException {
        log.debug("REST request to save STATION : {}", sTATION);
        if (sTATION.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sTATION cannot already have an ID")).body(null);
        }
        STATION result = sTATIONRepository.save(sTATION);
        return ResponseEntity.created(new URI("/api/s-tations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-tations : Updates an existing sTATION.
     *
     * @param sTATION the sTATION to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sTATION,
     * or with status 400 (Bad Request) if the sTATION is not valid,
     * or with status 500 (Internal Server Error) if the sTATION couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-tations")
    @Timed
    public ResponseEntity<STATION> updateSTATION(@RequestBody STATION sTATION) throws URISyntaxException {
        log.debug("REST request to update STATION : {}", sTATION);
        if (sTATION.getId() == null) {
            return createSTATION(sTATION);
        }
        STATION result = sTATIONRepository.save(sTATION);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sTATION.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-tations : get all the sTATIONS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sTATIONS in body
     */
    @GetMapping("/s-tations")
    @Timed
    public List<STATION> getAllSTATIONS() {
        log.debug("REST request to get all STATIONS");
        return sTATIONRepository.findAll();
    }

    /**
     * GET  /s-tations/:id : get the "id" sTATION.
     *
     * @param id the id of the sTATION to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sTATION, or with status 404 (Not Found)
     */
    @GetMapping("/s-tations/{id}")
    @Timed
    public ResponseEntity<STATION> getSTATION(@PathVariable Long id) {
        log.debug("REST request to get STATION : {}", id);
        STATION sTATION = sTATIONRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sTATION));
    }

    /**
     * DELETE  /s-tations/:id : delete the "id" sTATION.
     *
     * @param id the id of the sTATION to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-tations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSTATION(@PathVariable Long id) {
        log.debug("REST request to delete STATION : {}", id);
        sTATIONRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
