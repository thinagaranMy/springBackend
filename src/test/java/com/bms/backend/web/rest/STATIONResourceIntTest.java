package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.STATION;
import com.bms.backend.repository.STATIONRepository;
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
 * Test class for the STATIONResource REST controller.
 *
 * @see STATIONResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class STATIONResourceIntTest {

    private static final String DEFAULT_C_ODE = "AAAAAAAAAA";
    private static final String UPDATED_C_ODE = "BBBBBBBBBB";

    private static final String DEFAULT_D_ESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_D_ESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private STATIONRepository sTATIONRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSTATIONMockMvc;

    private STATION sTATION;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        STATIONResource sTATIONResource = new STATIONResource(sTATIONRepository);
        this.restSTATIONMockMvc = MockMvcBuilders.standaloneSetup(sTATIONResource)
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
    public static STATION createEntity(EntityManager em) {
        STATION sTATION = new STATION()
            .cOde(DEFAULT_C_ODE)
            .dEscription(DEFAULT_D_ESCRIPTION);
        return sTATION;
    }

    @Before
    public void initTest() {
        sTATION = createEntity(em);
    }

    @Test
    @Transactional
    public void createSTATION() throws Exception {
        int databaseSizeBeforeCreate = sTATIONRepository.findAll().size();

        // Create the STATION
        restSTATIONMockMvc.perform(post("/api/s-tations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sTATION)))
            .andExpect(status().isCreated());

        // Validate the STATION in the database
        List<STATION> sTATIONList = sTATIONRepository.findAll();
        assertThat(sTATIONList).hasSize(databaseSizeBeforeCreate + 1);
        STATION testSTATION = sTATIONList.get(sTATIONList.size() - 1);
        assertThat(testSTATION.getcOde()).isEqualTo(DEFAULT_C_ODE);
        assertThat(testSTATION.getdEscription()).isEqualTo(DEFAULT_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void createSTATIONWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sTATIONRepository.findAll().size();

        // Create the STATION with an existing ID
        sTATION.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSTATIONMockMvc.perform(post("/api/s-tations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sTATION)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<STATION> sTATIONList = sTATIONRepository.findAll();
        assertThat(sTATIONList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSTATIONS() throws Exception {
        // Initialize the database
        sTATIONRepository.saveAndFlush(sTATION);

        // Get all the sTATIONList
        restSTATIONMockMvc.perform(get("/api/s-tations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sTATION.getId().intValue())))
            .andExpect(jsonPath("$.[*].cOde").value(hasItem(DEFAULT_C_ODE.toString())))
            .andExpect(jsonPath("$.[*].dEscription").value(hasItem(DEFAULT_D_ESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSTATION() throws Exception {
        // Initialize the database
        sTATIONRepository.saveAndFlush(sTATION);

        // Get the sTATION
        restSTATIONMockMvc.perform(get("/api/s-tations/{id}", sTATION.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sTATION.getId().intValue()))
            .andExpect(jsonPath("$.cOde").value(DEFAULT_C_ODE.toString()))
            .andExpect(jsonPath("$.dEscription").value(DEFAULT_D_ESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSTATION() throws Exception {
        // Get the sTATION
        restSTATIONMockMvc.perform(get("/api/s-tations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSTATION() throws Exception {
        // Initialize the database
        sTATIONRepository.saveAndFlush(sTATION);
        int databaseSizeBeforeUpdate = sTATIONRepository.findAll().size();

        // Update the sTATION
        STATION updatedSTATION = sTATIONRepository.findOne(sTATION.getId());
        updatedSTATION
            .cOde(UPDATED_C_ODE)
            .dEscription(UPDATED_D_ESCRIPTION);

        restSTATIONMockMvc.perform(put("/api/s-tations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSTATION)))
            .andExpect(status().isOk());

        // Validate the STATION in the database
        List<STATION> sTATIONList = sTATIONRepository.findAll();
        assertThat(sTATIONList).hasSize(databaseSizeBeforeUpdate);
        STATION testSTATION = sTATIONList.get(sTATIONList.size() - 1);
        assertThat(testSTATION.getcOde()).isEqualTo(UPDATED_C_ODE);
        assertThat(testSTATION.getdEscription()).isEqualTo(UPDATED_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSTATION() throws Exception {
        int databaseSizeBeforeUpdate = sTATIONRepository.findAll().size();

        // Create the STATION

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSTATIONMockMvc.perform(put("/api/s-tations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sTATION)))
            .andExpect(status().isCreated());

        // Validate the STATION in the database
        List<STATION> sTATIONList = sTATIONRepository.findAll();
        assertThat(sTATIONList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSTATION() throws Exception {
        // Initialize the database
        sTATIONRepository.saveAndFlush(sTATION);
        int databaseSizeBeforeDelete = sTATIONRepository.findAll().size();

        // Get the sTATION
        restSTATIONMockMvc.perform(delete("/api/s-tations/{id}", sTATION.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<STATION> sTATIONList = sTATIONRepository.findAll();
        assertThat(sTATIONList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(STATION.class);
        STATION sTATION1 = new STATION();
        sTATION1.setId(1L);
        STATION sTATION2 = new STATION();
        sTATION2.setId(sTATION1.getId());
        assertThat(sTATION1).isEqualTo(sTATION2);
        sTATION2.setId(2L);
        assertThat(sTATION1).isNotEqualTo(sTATION2);
        sTATION1.setId(null);
        assertThat(sTATION1).isNotEqualTo(sTATION2);
    }
}
