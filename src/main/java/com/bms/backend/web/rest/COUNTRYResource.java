package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.COUNTRY;

import com.bms.backend.repository.COUNTRYRepository;
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
 * REST controller for managing COUNTRY.
 */
@RestController
@RequestMapping("/api")
public class COUNTRYResource {

    private final Logger log = LoggerFactory.getLogger(COUNTRYResource.class);

    private static final String ENTITY_NAME = "cOUNTRY";

    private final COUNTRYRepository cOUNTRYRepository;

    public COUNTRYResource(COUNTRYRepository cOUNTRYRepository) {
        this.cOUNTRYRepository = cOUNTRYRepository;
    }

    /**
     * POST  /c-ountries : Create a new cOUNTRY.
     *
     * @param cOUNTRY the cOUNTRY to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cOUNTRY, or with status 400 (Bad Request) if the cOUNTRY has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-ountries")
    @Timed
    public ResponseEntity<COUNTRY> createCOUNTRY(@RequestBody COUNTRY cOUNTRY) throws URISyntaxException {
        log.debug("REST request to save COUNTRY : {}", cOUNTRY);
        if (cOUNTRY.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cOUNTRY cannot already have an ID")).body(null);
        }
        COUNTRY result = cOUNTRYRepository.save(cOUNTRY);
        return ResponseEntity.created(new URI("/api/c-ountries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-ountries : Updates an existing cOUNTRY.
     *
     * @param cOUNTRY the cOUNTRY to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cOUNTRY,
     * or with status 400 (Bad Request) if the cOUNTRY is not valid,
     * or with status 500 (Internal Server Error) if the cOUNTRY couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-ountries")
    @Timed
    public ResponseEntity<COUNTRY> updateCOUNTRY(@RequestBody COUNTRY cOUNTRY) throws URISyntaxException {
        log.debug("REST request to update COUNTRY : {}", cOUNTRY);
        if (cOUNTRY.getId() == null) {
            return createCOUNTRY(cOUNTRY);
        }
        COUNTRY result = cOUNTRYRepository.save(cOUNTRY);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cOUNTRY.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-ountries : get all the cOUNTRIES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cOUNTRIES in body
     */
    @GetMapping("/c-ountries")
    @Timed
    public List<COUNTRY> getAllCOUNTRIES() {
        log.debug("REST request to get all COUNTRIES");
        return cOUNTRYRepository.findAll();
    }

    /**
     * GET  /c-ountries/:id : get the "id" cOUNTRY.
     *
     * @param id the id of the cOUNTRY to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cOUNTRY, or with status 404 (Not Found)
     */
    @GetMapping("/c-ountries/{id}")
    @Timed
    public ResponseEntity<COUNTRY> getCOUNTRY(@PathVariable Long id) {
        log.debug("REST request to get COUNTRY : {}", id);
        COUNTRY cOUNTRY = cOUNTRYRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cOUNTRY));
    }

    /**
     * DELETE  /c-ountries/:id : delete the "id" cOUNTRY.
     *
     * @param id the id of the cOUNTRY to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-ountries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCOUNTRY(@PathVariable Long id) {
        log.debug("REST request to delete COUNTRY : {}", id);
        cOUNTRYRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
