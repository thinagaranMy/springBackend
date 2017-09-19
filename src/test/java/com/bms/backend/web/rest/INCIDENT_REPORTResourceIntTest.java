package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.INCIDENT_REPORT;
import com.bms.backend.repository.INCIDENT_REPORTRepository;
import com.bms.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the INCIDENT_REPORTResource REST controller.
 *
 * @see INCIDENT_REPORTResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class INCIDENT_REPORTResourceIntTest {

    @Autowired
    private INCIDENT_REPORTRepository iNCIDENT_REPORTRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restINCIDENT_REPORTMockMvc;

    private INCIDENT_REPORT iNCIDENT_REPORT;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        INCIDENT_REPORTResource iNCIDENT_REPORTResource = new INCIDENT_REPORTResource(iNCIDENT_REPORTRepository);
        this.restINCIDENT_REPORTMockMvc = MockMvcBuilders.standaloneSetup(iNCIDENT_REPORTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static INCIDENT_REPORT createEntity(EntityManager em) {
        INCIDENT_REPORT iNCIDENT_REPORT = new INCIDENT_REPORT();
        return iNCIDENT_REPORT;
    }

    @Before
    public void initTest() {
        iNCIDENT_REPORT = createEntity(em);
    }

    @Test
    @Transactional
    public void createINCIDENT_REPORT() throws Exception {
        int databaseSizeBeforeCreate = iNCIDENT_REPORTRepository.findAll().size();

        // Create the INCIDENT_REPORT
        restINCIDENT_REPORTMockMvc.perform(post("/api/i-ncident-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iNCIDENT_REPORT)))
            .andExpect(status().isCreated());

        // Validate the INCIDENT_REPORT in the database
        List<INCIDENT_REPORT> iNCIDENT_REPORTList = iNCIDENT_REPORTRepository.findAll();
        assertThat(iNCIDENT_REPORTList).hasSize(databaseSizeBeforeCreate + 1);
        INCIDENT_REPORT testINCIDENT_REPORT = iNCIDENT_REPORTList.get(iNCIDENT_REPORTList.size() - 1);
    }

    @Test
    @Transactional
    public void createINCIDENT_REPORTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iNCIDENT_REPORTRepository.findAll().size();

        // Create the INCIDENT_REPORT with an existing ID
        iNCIDENT_REPORT.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restINCIDENT_REPORTMockMvc.perform(post("/api/i-ncident-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iNCIDENT_REPORT)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<INCIDENT_REPORT> iNCIDENT_REPORTList = iNCIDENT_REPORTRepository.findAll();
        assertThat(iNCIDENT_REPORTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllINCIDENT_REPORTS() throws Exception {
        // Initialize the database
        iNCIDENT_REPORTRepository.saveAndFlush(iNCIDENT_REPORT);

        // Get all the iNCIDENT_REPORTList
        restINCIDENT_REPORTMockMvc.perform(get("/api/i-ncident-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iNCIDENT_REPORT.getId().intValue())));
    }

    @Test
    @Transactional
    public void getINCIDENT_REPORT() throws Exception {
        // Initialize the database
        iNCIDENT_REPORTRepository.saveAndFlush(iNCIDENT_REPORT);

        // Get the iNCIDENT_REPORT
        restINCIDENT_REPORTMockMvc.perform(get("/api/i-ncident-reports/{id}", iNCIDENT_REPORT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iNCIDENT_REPORT.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingINCIDENT_REPORT() throws Exception {
        // Get the iNCIDENT_REPORT
        restINCIDENT_REPORTMockMvc.perform(get("/api/i-ncident-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateINCIDENT_REPORT() throws Exception {
        // Initialize the database
        iNCIDENT_REPORTRepository.saveAndFlush(iNCIDENT_REPORT);
        int databaseSizeBeforeUpdate = iNCIDENT_REPORTRepository.findAll().size();

        // Update the iNCIDENT_REPORT
        INCIDENT_REPORT updatedINCIDENT_REPORT = iNCIDENT_REPORTRepository.findOne(iNCIDENT_REPORT.getId());

        restINCIDENT_REPORTMockMvc.perform(put("/api/i-ncident-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedINCIDENT_REPORT)))
            .andExpect(status().isOk());

        // Validate the INCIDENT_REPORT in the database
        List<INCIDENT_REPORT> iNCIDENT_REPORTList = iNCIDENT_REPORTRepository.findAll();
        assertThat(iNCIDENT_REPORTList).hasSize(databaseSizeBeforeUpdate);
        INCIDENT_REPORT testINCIDENT_REPORT = iNCIDENT_REPORTList.get(iNCIDENT_REPORTList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingINCIDENT_REPORT() throws Exception {
        int databaseSizeBeforeUpdate = iNCIDENT_REPORTRepository.findAll().size();

        // Create the INCIDENT_REPORT

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restINCIDENT_REPORTMockMvc.perform(put("/api/i-ncident-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iNCIDENT_REPORT)))
            .andExpect(status().isCreated());

        // Validate the INCIDENT_REPORT in the database
        List<INCIDENT_REPORT> iNCIDENT_REPORTList = iNCIDENT_REPORTRepository.findAll();
        assertThat(iNCIDENT_REPORTList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteINCIDENT_REPORT() throws Exception {
        // Initialize the database
        iNCIDENT_REPORTRepository.saveAndFlush(iNCIDENT_REPORT);
        int databaseSizeBeforeDelete = iNCIDENT_REPORTRepository.findAll().size();

        // Get the iNCIDENT_REPORT
        restINCIDENT_REPORTMockMvc.perform(delete("/api/i-ncident-reports/{id}", iNCIDENT_REPORT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<INCIDENT_REPORT> iNCIDENT_REPORTList = iNCIDENT_REPORTRepository.findAll();
        assertThat(iNCIDENT_REPORTList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(INCIDENT_REPORT.class);
        INCIDENT_REPORT iNCIDENT_REPORT1 = new INCIDENT_REPORT();
        iNCIDENT_REPORT1.setId(1L);
        INCIDENT_REPORT iNCIDENT_REPORT2 = new INCIDENT_REPORT();
        iNCIDENT_REPORT2.setId(iNCIDENT_REPORT1.getId());
        assertThat(iNCIDENT_REPORT1).isEqualTo(iNCIDENT_REPORT2);
        iNCIDENT_REPORT2.setId(2L);
        assertThat(iNCIDENT_REPORT1).isNotEqualTo(iNCIDENT_REPORT2);
        iNCIDENT_REPORT1.setId(null);
        assertThat(iNCIDENT_REPORT1).isNotEqualTo(iNCIDENT_REPORT2);
    }
}
