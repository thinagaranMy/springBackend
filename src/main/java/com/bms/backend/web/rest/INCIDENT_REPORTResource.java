package com.bms.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bms.backend.domain.INCIDENT_REPORT;

import com.bms.backend.repository.INCIDENT_REPORTRepository;
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
 * REST controller for managing INCIDENT_REPORT.
 */
@RestController
@RequestMapping("/api")
public class INCIDENT_REPORTResource {

    private final Logger log = LoggerFactory.getLogger(INCIDENT_REPORTResource.class);

    private static final String ENTITY_NAME = "iNCIDENT_REPORT";

    private final INCIDENT_REPORTRepository iNCIDENT_REPORTRepository;

    public INCIDENT_REPORTResource(INCIDENT_REPORTRepository iNCIDENT_REPORTRepository) {
        this.iNCIDENT_REPORTRepository = iNCIDENT_REPORTRepository;
    }

    /**
     * POST  /i-ncident-reports : Create a new iNCIDENT_REPORT.
     *
     * @param iNCIDENT_REPORT the iNCIDENT_REPORT to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iNCIDENT_REPORT, or with status 400 (Bad Request) if the iNCIDENT_REPORT has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/i-ncident-reports")
    @Timed
    public ResponseEntity<INCIDENT_REPORT> createINCIDENT_REPORT(@RequestBody INCIDENT_REPORT iNCIDENT_REPORT) throws URISyntaxException {
        log.debug("REST request to save INCIDENT_REPORT : {}", iNCIDENT_REPORT);
        if (iNCIDENT_REPORT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new iNCIDENT_REPORT cannot already have an ID")).body(null);
        }
        INCIDENT_REPORT result = iNCIDENT_REPORTRepository.save(iNCIDENT_REPORT);
        return ResponseEntity.created(new URI("/api/i-ncident-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /i-ncident-reports : Updates an existing iNCIDENT_REPORT.
     *
     * @param iNCIDENT_REPORT the iNCIDENT_REPORT to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iNCIDENT_REPORT,
     * or with status 400 (Bad Request) if the iNCIDENT_REPORT is not valid,
     * or with status 500 (Internal Server Error) if the iNCIDENT_REPORT couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/i-ncident-reports")
    @Timed
    public ResponseEntity<INCIDENT_REPORT> updateINCIDENT_REPORT(@RequestBody INCIDENT_REPORT iNCIDENT_REPORT) throws URISyntaxException {
        log.debug("REST request to update INCIDENT_REPORT : {}", iNCIDENT_REPORT);
        if (iNCIDENT_REPORT.getId() == null) {
            return createINCIDENT_REPORT(iNCIDENT_REPORT);
        }
        INCIDENT_REPORT result = iNCIDENT_REPORTRepository.save(iNCIDENT_REPORT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iNCIDENT_REPORT.getId().toString()))
            .body(result);
    }

    /**
     * GET  /i-ncident-reports : get all the iNCIDENT_REPORTS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iNCIDENT_REPORTS in body
     */
    @GetMapping("/i-ncident-reports")
    @Timed
    public List<INCIDENT_REPORT> getAllINCIDENT_REPORTS() {
        log.debug("REST request to get all INCIDENT_REPORTS");
        return iNCIDENT_REPORTRepository.findAll();
    }

    /**
     * GET  /i-ncident-reports/:id : get the "id" iNCIDENT_REPORT.
     *
     * @param id the id of the iNCIDENT_REPORT to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iNCIDENT_REPORT, or with status 404 (Not Found)
     */
    @GetMapping("/i-ncident-reports/{id}")
    @Timed
    public ResponseEntity<INCIDENT_REPORT> getINCIDENT_REPORT(@PathVariable Long id) {
        log.debug("REST request to get INCIDENT_REPORT : {}", id);
        INCIDENT_REPORT iNCIDENT_REPORT = iNCIDENT_REPORTRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(iNCIDENT_REPORT));
    }

    /**
     * DELETE  /i-ncident-reports/:id : delete the "id" iNCIDENT_REPORT.
     *
     * @param id the id of the iNCIDENT_REPORT to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/i-ncident-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteINCIDENT_REPORT(@PathVariable Long id) {
        log.debug("REST request to delete INCIDENT_REPORT : {}", id);
        iNCIDENT_REPORTRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
