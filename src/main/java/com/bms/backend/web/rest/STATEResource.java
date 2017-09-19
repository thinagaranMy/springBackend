package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.STATE;

import com.bms.backend.repository.STATERepository;
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
 * REST controller for managing STATE.
 */
@RestController
@RequestMapping("/api")
public class STATEResource {

    private final Logger log = LoggerFactory.getLogger(STATEResource.class);

    private static final String ENTITY_NAME = "sTATE";

    private final STATERepository sTATERepository;

    public STATEResource(STATERepository sTATERepository) {
        this.sTATERepository = sTATERepository;
    }

    /**
     * POST  /s-tates : Create a new sTATE.
     *
     * @param sTATE the sTATE to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sTATE, or with status 400 (Bad Request) if the sTATE has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-tates")
    @Timed
    public ResponseEntity<STATE> createSTATE(@RequestBody STATE sTATE) throws URISyntaxException {
        log.debug("REST request to save STATE : {}", sTATE);
        if (sTATE.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sTATE cannot already have an ID")).body(null);
        }
        STATE result = sTATERepository.save(sTATE);
        return ResponseEntity.created(new URI("/api/s-tates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-tates : Updates an existing sTATE.
     *
     * @param sTATE the sTATE to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sTATE,
     * or with status 400 (Bad Request) if the sTATE is not valid,
     * or with status 500 (Internal Server Error) if the sTATE couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-tates")
    @Timed
    public ResponseEntity<STATE> updateSTATE(@RequestBody STATE sTATE) throws URISyntaxException {
        log.debug("REST request to update STATE : {}", sTATE);
        if (sTATE.getId() == null) {
            return createSTATE(sTATE);
        }
        STATE result = sTATERepository.save(sTATE);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sTATE.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-tates : get all the sTATES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sTATES in body
     */
    @GetMapping("/s-tates")
    @Timed
    public List<STATE> getAllSTATES() {
        log.debug("REST request to get all STATES");
        return sTATERepository.findAll();
    }

    /**
     * GET  /s-tates/:id : get the "id" sTATE.
     *
     * @param id the id of the sTATE to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sTATE, or with status 404 (Not Found)
     */
    @GetMapping("/s-tates/{id}")
    @Timed
    public ResponseEntity<STATE> getSTATE(@PathVariable Long id) {
        log.debug("REST request to get STATE : {}", id);
        STATE sTATE = sTATERepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sTATE));
    }

    /**
     * DELETE  /s-tates/:id : delete the "id" sTATE.
     *
     * @param id the id of the sTATE to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-tates/{id}")
    @Timed
    public ResponseEntity<Void> deleteSTATE(@PathVariable Long id) {
        log.debug("REST request to delete STATE : {}", id);
        sTATERepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
