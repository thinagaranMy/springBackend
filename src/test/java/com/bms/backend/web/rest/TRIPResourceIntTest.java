package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.TRIP;
import com.bms.backend.repository.TRIPRepository;
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
 * Test class for the TRIPResource REST controller.
 *
 * @see TRIPResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class TRIPResourceIntTest {

    private static final String DEFAULT_S_CHEDULEDTIME = "AAAAAAAAAA";
    private static final String UPDATED_S_CHEDULEDTIME = "BBBBBBBBBB";

    @Autowired
    private TRIPRepository tRIPRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTRIPMockMvc;

    private TRIP tRIP;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRIPResource tRIPResource = new TRIPResource(tRIPRepository);
        this.restTRIPMockMvc = MockMvcBuilders.standaloneSetup(tRIPResource)
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
    public static TRIP createEntity(EntityManager em) {
        TRIP tRIP = new TRIP()
            .sCheduledtime(DEFAULT_S_CHEDULEDTIME);
        return tRIP;
    }

    @Before
    public void initTest() {
        tRIP = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRIP() throws Exception {
        int databaseSizeBeforeCreate = tRIPRepository.findAll().size();

        // Create the TRIP
        restTRIPMockMvc.perform(post("/api/t-rips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRIP)))
            .andExpect(status().isCreated());

        // Validate the TRIP in the database
        List<TRIP> tRIPList = tRIPRepository.findAll();
        assertThat(tRIPList).hasSize(databaseSizeBeforeCreate + 1);
        TRIP testTRIP = tRIPList.get(tRIPList.size() - 1);
        assertThat(testTRIP.getsCheduledtime()).isEqualTo(DEFAULT_S_CHEDULEDTIME);
    }

    @Test
    @Transactional
    public void createTRIPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRIPRepository.findAll().size();

        // Create the TRIP with an existing ID
        tRIP.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRIPMockMvc.perform(post("/api/t-rips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRIP)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRIP> tRIPList = tRIPRepository.findAll();
        assertThat(tRIPList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTRIPS() throws Exception {
        // Initialize the database
        tRIPRepository.saveAndFlush(tRIP);

        // Get all the tRIPList
        restTRIPMockMvc.perform(get("/api/t-rips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRIP.getId().intValue())))
            .andExpect(jsonPath("$.[*].sCheduledtime").value(hasItem(DEFAULT_S_CHEDULEDTIME.toString())));
    }

    @Test
    @Transactional
    public void getTRIP() throws Exception {
        // Initialize the database
        tRIPRepository.saveAndFlush(tRIP);

        // Get the tRIP
        restTRIPMockMvc.perform(get("/api/t-rips/{id}", tRIP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRIP.getId().intValue()))
            .andExpect(jsonPath("$.sCheduledtime").value(DEFAULT_S_CHEDULEDTIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTRIP() throws Exception {
        // Get the tRIP
        restTRIPMockMvc.perform(get("/api/t-rips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRIP() throws Exception {
        // Initialize the database
        tRIPRepository.saveAndFlush(tRIP);
        int databaseSizeBeforeUpdate = tRIPRepository.findAll().size();

        // Update the tRIP
        TRIP updatedTRIP = tRIPRepository.findOne(tRIP.getId());
        updatedTRIP
            .sCheduledtime(UPDATED_S_CHEDULEDTIME);

        restTRIPMockMvc.perform(put("/api/t-rips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTRIP)))
            .andExpect(status().isOk());

        // Validate the TRIP in the database
        List<TRIP> tRIPList = tRIPRepository.findAll();
        assertThat(tRIPList).hasSize(databaseSizeBeforeUpdate);
        TRIP testTRIP = tRIPList.get(tRIPList.size() - 1);
        assertThat(testTRIP.getsCheduledtime()).isEqualTo(UPDATED_S_CHEDULEDTIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTRIP() throws Exception {
        int databaseSizeBeforeUpdate = tRIPRepository.findAll().size();

        // Create the TRIP

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRIPMockMvc.perform(put("/api/t-rips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRIP)))
            .andExpect(status().isCreated());

        // Validate the TRIP in the database
        List<TRIP> tRIPList = tRIPRepository.findAll();
        assertThat(tRIPList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRIP() throws Exception {
        // Initialize the database
        tRIPRepository.saveAndFlush(tRIP);
        int databaseSizeBeforeDelete = tRIPRepository.findAll().size();

        // Get the tRIP
        restTRIPMockMvc.perform(delete("/api/t-rips/{id}", tRIP.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRIP> tRIPList = tRIPRepository.findAll();
        assertThat(tRIPList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TRIP.class);
        TRIP tRIP1 = new TRIP();
        tRIP1.setId(1L);
        TRIP tRIP2 = new TRIP();
        tRIP2.setId(tRIP1.getId());
        assertThat(tRIP1).isEqualTo(tRIP2);
        tRIP2.setId(2L);
        assertThat(tRIP1).isNotEqualTo(tRIP2);
        tRIP1.setId(null);
        assertThat(tRIP1).isNotEqualTo(tRIP2);
    }
}
