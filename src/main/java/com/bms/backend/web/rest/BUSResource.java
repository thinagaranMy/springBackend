package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.BUS;

import com.bms.backend.repository.BUSRepository;
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
 * REST controller for managing BUS.
 */
@RestController
@RequestMapping("/api")
public class BUSResource {

    private final Logger log = LoggerFactory.getLogger(BUSResource.class);

    private static final String ENTITY_NAME = "bUS";

    private final BUSRepository bUSRepository;

    public BUSResource(BUSRepository bUSRepository) {
        this.bUSRepository = bUSRepository;
    }

    /**
     * POST  /b-uses : Create a new bUS.
     *
     * @param bUS the bUS to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bUS, or with status 400 (Bad Request) if the bUS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-uses")
    @Timed
    public ResponseEntity<BUS> createBUS(@RequestBody BUS bUS) throws URISyntaxException {
        log.debug("REST request to save BUS : {}", bUS);
        if (bUS.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bUS cannot already have an ID")).body(null);
        }
        BUS result = bUSRepository.save(bUS);
        return ResponseEntity.created(new URI("/api/b-uses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-uses : Updates an existing bUS.
     *
     * @param bUS the bUS to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bUS,
     * or with status 400 (Bad Request) if the bUS is not valid,
     * or with status 500 (Internal Server Error) if the bUS couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-uses")
    @Timed
    public ResponseEntity<BUS> updateBUS(@RequestBody BUS bUS) throws URISyntaxException {
        log.debug("REST request to update BUS : {}", bUS);
        if (bUS.getId() == null) {
            return createBUS(bUS);
        }
        BUS result = bUSRepository.save(bUS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bUS.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-uses : get all the buses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buses in body
     */
    @GetMapping("/b-uses")
    @Timed
    public List<BUS> getAllBUSES() {
        log.debug("REST request to get all BUSES");
        return bUSRepository.findAll();
    }

    /**
     * GET  /b-uses/:id : get the "id" bUS.
     *
     * @param id the id of the bUS to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bUS, or with status 404 (Not Found)
     */
    @GetMapping("/b-uses/{id}")
    @Timed
    public ResponseEntity<BUS> getBUS(@PathVariable Long id) {
        log.debug("REST request to get BUS : {}", id);
        BUS bUS = bUSRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bUS));
    }

    /**
     * DELETE  /b-uses/:id : delete the "id" bUS.
     *
     * @param id the id of the bUS to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-uses/{id}")
    @Timed
    public ResponseEntity<Void> deleteBUS(@PathVariable Long id) {
        log.debug("REST request to delete BUS : {}", id);
        bUSRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
