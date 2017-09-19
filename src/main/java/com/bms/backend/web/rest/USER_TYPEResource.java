package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.USER_TYPE;

import com.bms.backend.repository.USER_TYPERepository;
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
 * REST controller for managing USER_TYPE.
 */
@RestController
@RequestMapping("/api")
public class USER_TYPEResource {

    private final Logger log = LoggerFactory.getLogger(USER_TYPEResource.class);

    private static final String ENTITY_NAME = "uSER_TYPE";

    private final USER_TYPERepository uSER_TYPERepository;

    public USER_TYPEResource(USER_TYPERepository uSER_TYPERepository) {
        this.uSER_TYPERepository = uSER_TYPERepository;
    }

    /**
     * POST  /u-ser-types : Create a new uSER_TYPE.
     *
     * @param uSER_TYPE the uSER_TYPE to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uSER_TYPE, or with status 400 (Bad Request) if the uSER_TYPE has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/u-ser-types")
    @Timed
    public ResponseEntity<USER_TYPE> createUSER_TYPE(@RequestBody USER_TYPE uSER_TYPE) throws URISyntaxException {
        log.debug("REST request to save USER_TYPE : {}", uSER_TYPE);
        if (uSER_TYPE.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new uSER_TYPE cannot already have an ID")).body(null);
        }
        USER_TYPE result = uSER_TYPERepository.save(uSER_TYPE);
        return ResponseEntity.created(new URI("/api/u-ser-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /u-ser-types : Updates an existing uSER_TYPE.
     *
     * @param uSER_TYPE the uSER_TYPE to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uSER_TYPE,
     * or with status 400 (Bad Request) if the uSER_TYPE is not valid,
     * or with status 500 (Internal Server Error) if the uSER_TYPE couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/u-ser-types")
    @Timed
    public ResponseEntity<USER_TYPE> updateUSER_TYPE(@RequestBody USER_TYPE uSER_TYPE) throws URISyntaxException {
        log.debug("REST request to update USER_TYPE : {}", uSER_TYPE);
        if (uSER_TYPE.getId() == null) {
            return createUSER_TYPE(uSER_TYPE);
        }
        USER_TYPE result = uSER_TYPERepository.save(uSER_TYPE);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uSER_TYPE.getId().toString()))
            .body(result);
    }

    /**
     * GET  /u-ser-types : get all the uSER_TYPES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uSER_TYPES in body
     */
    @GetMapping("/u-ser-types")
    @Timed
    public List<USER_TYPE> getAllUSER_TYPES() {
        log.debug("REST request to get all USER_TYPES");
        return uSER_TYPERepository.findAll();
    }

    /**
     * GET  /u-ser-types/:id : get the "id" uSER_TYPE.
     *
     * @param id the id of the uSER_TYPE to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uSER_TYPE, or with status 404 (Not Found)
     */
    @GetMapping("/u-ser-types/{id}")
    @Timed
    public ResponseEntity<USER_TYPE> getUSER_TYPE(@PathVariable Long id) {
        log.debug("REST request to get USER_TYPE : {}", id);
        USER_TYPE uSER_TYPE = uSER_TYPERepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uSER_TYPE));
    }

    /**
     * DELETE  /u-ser-types/:id : delete the "id" uSER_TYPE.
     *
     * @param id the id of the uSER_TYPE to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/u-ser-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteUSER_TYPE(@PathVariable Long id) {
        log.debug("REST request to delete USER_TYPE : {}", id);
        uSER_TYPERepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
