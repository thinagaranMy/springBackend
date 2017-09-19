package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.TRIP;

import com.bms.backend.repository.TRIPRepository;
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
 * REST controller for managing TRIP.
 */
@RestController
@RequestMapping("/api")
public class TRIPResource {

    private final Logger log = LoggerFactory.getLogger(TRIPResource.class);

    private static final String ENTITY_NAME = "tRIP";

    private final TRIPRepository tRIPRepository;

    public TRIPResource(TRIPRepository tRIPRepository) {
        this.tRIPRepository = tRIPRepository;
    }

    /**
     * POST  /t-rips : Create a new tRIP.
     *
     * @param tRIP the tRIP to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRIP, or with status 400 (Bad Request) if the tRIP has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-rips")
    @Timed
    public ResponseEntity<TRIP> createTRIP(@RequestBody TRIP tRIP) throws URISyntaxException {
        log.debug("REST request to save TRIP : {}", tRIP);
        if (tRIP.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tRIP cannot already have an ID")).body(null);
        }
        TRIP result = tRIPRepository.save(tRIP);
        return ResponseEntity.created(new URI("/api/t-rips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-rips : Updates an existing tRIP.
     *
     * @param tRIP the tRIP to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRIP,
     * or with status 400 (Bad Request) if the tRIP is not valid,
     * or with status 500 (Internal Server Error) if the tRIP couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-rips")
    @Timed
    public ResponseEntity<TRIP> updateTRIP(@RequestBody TRIP tRIP) throws URISyntaxException {
        log.debug("REST request to update TRIP : {}", tRIP);
        if (tRIP.getId() == null) {
            return createTRIP(tRIP);
        }
        TRIP result = tRIPRepository.save(tRIP);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tRIP.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-rips : get all the tRIPS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRIPS in body
     */
    @GetMapping("/t-rips")
    @Timed
    public List<TRIP> getAllTRIPS() {
        log.debug("REST request to get all TRIPS");
        return tRIPRepository.findAll();
    }

    /**
     * GET  /t-rips/:id : get the "id" tRIP.
     *
     * @param id the id of the tRIP to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRIP, or with status 404 (Not Found)
     */
    @GetMapping("/t-rips/{id}")
    @Timed
    public ResponseEntity<TRIP> getTRIP(@PathVariable Long id) {
        log.debug("REST request to get TRIP : {}", id);
        TRIP tRIP = tRIPRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tRIP));
    }

    /**
     * DELETE  /t-rips/:id : delete the "id" tRIP.
     *
     * @param id the id of the tRIP to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-rips/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRIP(@PathVariable Long id) {
        log.debug("REST request to delete TRIP : {}", id);
        tRIPRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
