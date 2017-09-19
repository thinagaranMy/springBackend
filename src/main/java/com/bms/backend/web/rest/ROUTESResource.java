package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.ROUTES;

import com.bms.backend.repository.ROUTESRepository;
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
 * REST controller for managing ROUTES.
 */
@RestController
@RequestMapping("/api")
public class ROUTESResource {

    private final Logger log = LoggerFactory.getLogger(ROUTESResource.class);

    private static final String ENTITY_NAME = "rOUTES";

    private final ROUTESRepository rOUTESRepository;

    public ROUTESResource(ROUTESRepository rOUTESRepository) {
        this.rOUTESRepository = rOUTESRepository;
    }

    /**
     * POST  /r-outes : Create a new rOUTES.
     *
     * @param rOUTES the rOUTES to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rOUTES, or with status 400 (Bad Request) if the rOUTES has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/r-outes")
    @Timed
    public ResponseEntity<ROUTES> createROUTES(@RequestBody ROUTES rOUTES) throws URISyntaxException {
        log.debug("REST request to save ROUTES : {}", rOUTES);
        if (rOUTES.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rOUTES cannot already have an ID")).body(null);
        }
        ROUTES result = rOUTESRepository.save(rOUTES);
        return ResponseEntity.created(new URI("/api/r-outes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r-outes : Updates an existing rOUTES.
     *
     * @param rOUTES the rOUTES to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rOUTES,
     * or with status 400 (Bad Request) if the rOUTES is not valid,
     * or with status 500 (Internal Server Error) if the rOUTES couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/r-outes")
    @Timed
    public ResponseEntity<ROUTES> updateROUTES(@RequestBody ROUTES rOUTES) throws URISyntaxException {
        log.debug("REST request to update ROUTES : {}", rOUTES);
        if (rOUTES.getId() == null) {
            return createROUTES(rOUTES);
        }
        ROUTES result = rOUTESRepository.save(rOUTES);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rOUTES.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r-outes : get all the rOUTES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rOUTES in body
     */
    @GetMapping("/r-outes")
    @Timed
    public List<ROUTES> getAllROUTES() {
        log.debug("REST request to get all ROUTES");
        return rOUTESRepository.findAll();
    }

    /**
     * GET  /r-outes/:id : get the "id" rOUTES.
     *
     * @param id the id of the rOUTES to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rOUTES, or with status 404 (Not Found)
     */
    @GetMapping("/r-outes/{id}")
    @Timed
    public ResponseEntity<ROUTES> getROUTES(@PathVariable Long id) {
        log.debug("REST request to get ROUTES : {}", id);
        ROUTES rOUTES = rOUTESRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rOUTES));
    }

    /**
     * DELETE  /r-outes/:id : delete the "id" rOUTES.
     *
     * @param id the id of the rOUTES to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/r-outes/{id}")
    @Timed
    public ResponseEntity<Void> deleteROUTES(@PathVariable Long id) {
        log.debug("REST request to delete ROUTES : {}", id);
        rOUTESRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
