package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.PHY_SITE;

import com.bms.backend.repository.PHY_SITERepository;
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
 * REST controller for managing PHY_SITE.
 */
@RestController
@RequestMapping("/api")
public class PHY_SITEResource {

    private final Logger log = LoggerFactory.getLogger(PHY_SITEResource.class);

    private static final String ENTITY_NAME = "pHY_SITE";

    private final PHY_SITERepository pHY_SITERepository;

    public PHY_SITEResource(PHY_SITERepository pHY_SITERepository) {
        this.pHY_SITERepository = pHY_SITERepository;
    }

    /**
     * POST  /p-hy-sites : Create a new pHY_SITE.
     *
     * @param pHY_SITE the pHY_SITE to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pHY_SITE, or with status 400 (Bad Request) if the pHY_SITE has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-hy-sites")
    @Timed
    public ResponseEntity<PHY_SITE> createPHY_SITE(@RequestBody PHY_SITE pHY_SITE) throws URISyntaxException {
        log.debug("REST request to save PHY_SITE : {}", pHY_SITE);
        if (pHY_SITE.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pHY_SITE cannot already have an ID")).body(null);
        }
        PHY_SITE result = pHY_SITERepository.save(pHY_SITE);
        return ResponseEntity.created(new URI("/api/p-hy-sites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-hy-sites : Updates an existing pHY_SITE.
     *
     * @param pHY_SITE the pHY_SITE to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pHY_SITE,
     * or with status 400 (Bad Request) if the pHY_SITE is not valid,
     * or with status 500 (Internal Server Error) if the pHY_SITE couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-hy-sites")
    @Timed
    public ResponseEntity<PHY_SITE> updatePHY_SITE(@RequestBody PHY_SITE pHY_SITE) throws URISyntaxException {
        log.debug("REST request to update PHY_SITE : {}", pHY_SITE);
        if (pHY_SITE.getId() == null) {
            return createPHY_SITE(pHY_SITE);
        }
        PHY_SITE result = pHY_SITERepository.save(pHY_SITE);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pHY_SITE.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-hy-sites : get all the pHY_SITES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pHY_SITES in body
     */
    @GetMapping("/p-hy-sites")
    @Timed
    public List<PHY_SITE> getAllPHY_SITES() {
        log.debug("REST request to get all PHY_SITES");
        return pHY_SITERepository.findAll();
    }

    /**
     * GET  /p-hy-sites/:id : get the "id" pHY_SITE.
     *
     * @param id the id of the pHY_SITE to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pHY_SITE, or with status 404 (Not Found)
     */
    @GetMapping("/p-hy-sites/{id}")
    @Timed
    public ResponseEntity<PHY_SITE> getPHY_SITE(@PathVariable Long id) {
        log.debug("REST request to get PHY_SITE : {}", id);
        PHY_SITE pHY_SITE = pHY_SITERepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pHY_SITE));
    }

    /**
     * DELETE  /p-hy-sites/:id : delete the "id" pHY_SITE.
     *
     * @param id the id of the pHY_SITE to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-hy-sites/{id}")
    @Timed
    public ResponseEntity<Void> deletePHY_SITE(@PathVariable Long id) {
        log.debug("REST request to delete PHY_SITE : {}", id);
        pHY_SITERepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
