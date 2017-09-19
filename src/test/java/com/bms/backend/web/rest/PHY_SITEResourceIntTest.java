package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.PHY_SITE;
import com.bms.backend.repository.PHY_SITERepository;
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
 * Test class for the PHY_SITEResource REST controller.
 *
 * @see PHY_SITEResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class PHY_SITEResourceIntTest {

    private static final String DEFAULT_A_DDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_A_DDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_A_DDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_A_DDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_A_DDRESS_LINE_3 = "AAAAAAAAAA";
    private static final String UPDATED_A_DDRESS_LINE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_P_OSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_P_OSTCODE = "BBBBBBBBBB";

    @Autowired
    private PHY_SITERepository pHY_SITERepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPHY_SITEMockMvc;

    private PHY_SITE pHY_SITE;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PHY_SITEResource pHY_SITEResource = new PHY_SITEResource(pHY_SITERepository);
        this.restPHY_SITEMockMvc = MockMvcBuilders.standaloneSetup(pHY_SITEResource)
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
    public static PHY_SITE createEntity(EntityManager em) {
        PHY_SITE pHY_SITE = new PHY_SITE()
            .aDdressLine1(DEFAULT_A_DDRESS_LINE_1)
            .aDdressLine2(DEFAULT_A_DDRESS_LINE_2)
            .aDdressLine3(DEFAULT_A_DDRESS_LINE_3)
            .pOstcode(DEFAULT_P_OSTCODE);
        return pHY_SITE;
    }

    @Before
    public void initTest() {
        pHY_SITE = createEntity(em);
    }

    @Test
    @Transactional
    public void createPHY_SITE() throws Exception {
        int databaseSizeBeforeCreate = pHY_SITERepository.findAll().size();

        // Create the PHY_SITE
        restPHY_SITEMockMvc.perform(post("/api/p-hy-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pHY_SITE)))
            .andExpect(status().isCreated());

        // Validate the PHY_SITE in the database
        List<PHY_SITE> pHY_SITEList = pHY_SITERepository.findAll();
        assertThat(pHY_SITEList).hasSize(databaseSizeBeforeCreate + 1);
        PHY_SITE testPHY_SITE = pHY_SITEList.get(pHY_SITEList.size() - 1);
        assertThat(testPHY_SITE.getaDdressLine1()).isEqualTo(DEFAULT_A_DDRESS_LINE_1);
        assertThat(testPHY_SITE.getaDdressLine2()).isEqualTo(DEFAULT_A_DDRESS_LINE_2);
        assertThat(testPHY_SITE.getaDdressLine3()).isEqualTo(DEFAULT_A_DDRESS_LINE_3);
        assertThat(testPHY_SITE.getpOstcode()).isEqualTo(DEFAULT_P_OSTCODE);
    }

    @Test
    @Transactional
    public void createPHY_SITEWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pHY_SITERepository.findAll().size();

        // Create the PHY_SITE with an existing ID
        pHY_SITE.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPHY_SITEMockMvc.perform(post("/api/p-hy-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pHY_SITE)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PHY_SITE> pHY_SITEList = pHY_SITERepository.findAll();
        assertThat(pHY_SITEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPHY_SITES() throws Exception {
        // Initialize the database
        pHY_SITERepository.saveAndFlush(pHY_SITE);

        // Get all the pHY_SITEList
        restPHY_SITEMockMvc.perform(get("/api/p-hy-sites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pHY_SITE.getId().intValue())))
            .andExpect(jsonPath("$.[*].aDdressLine1").value(hasItem(DEFAULT_A_DDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].aDdressLine2").value(hasItem(DEFAULT_A_DDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].aDdressLine3").value(hasItem(DEFAULT_A_DDRESS_LINE_3.toString())))
            .andExpect(jsonPath("$.[*].pOstcode").value(hasItem(DEFAULT_P_OSTCODE.toString())));
    }

    @Test
    @Transactional
    public void getPHY_SITE() throws Exception {
        // Initialize the database
        pHY_SITERepository.saveAndFlush(pHY_SITE);

        // Get the pHY_SITE
        restPHY_SITEMockMvc.perform(get("/api/p-hy-sites/{id}", pHY_SITE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pHY_SITE.getId().intValue()))
            .andExpect(jsonPath("$.aDdressLine1").value(DEFAULT_A_DDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.aDdressLine2").value(DEFAULT_A_DDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.aDdressLine3").value(DEFAULT_A_DDRESS_LINE_3.toString()))
            .andExpect(jsonPath("$.pOstcode").value(DEFAULT_P_OSTCODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPHY_SITE() throws Exception {
        // Get the pHY_SITE
        restPHY_SITEMockMvc.perform(get("/api/p-hy-sites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePHY_SITE() throws Exception {
        // Initialize the database
        pHY_SITERepository.saveAndFlush(pHY_SITE);
        int databaseSizeBeforeUpdate = pHY_SITERepository.findAll().size();

        // Update the pHY_SITE
        PHY_SITE updatedPHY_SITE = pHY_SITERepository.findOne(pHY_SITE.getId());
        updatedPHY_SITE
            .aDdressLine1(UPDATED_A_DDRESS_LINE_1)
            .aDdressLine2(UPDATED_A_DDRESS_LINE_2)
            .aDdressLine3(UPDATED_A_DDRESS_LINE_3)
            .pOstcode(UPDATED_P_OSTCODE);

        restPHY_SITEMockMvc.perform(put("/api/p-hy-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPHY_SITE)))
            .andExpect(status().isOk());

        // Validate the PHY_SITE in the database
        List<PHY_SITE> pHY_SITEList = pHY_SITERepository.findAll();
        assertThat(pHY_SITEList).hasSize(databaseSizeBeforeUpdate);
        PHY_SITE testPHY_SITE = pHY_SITEList.get(pHY_SITEList.size() - 1);
        assertThat(testPHY_SITE.getaDdressLine1()).isEqualTo(UPDATED_A_DDRESS_LINE_1);
        assertThat(testPHY_SITE.getaDdressLine2()).isEqualTo(UPDATED_A_DDRESS_LINE_2);
        assertThat(testPHY_SITE.getaDdressLine3()).isEqualTo(UPDATED_A_DDRESS_LINE_3);
        assertThat(testPHY_SITE.getpOstcode()).isEqualTo(UPDATED_P_OSTCODE);
    }

    @Test
    @Transactional
    public void updateNonExistingPHY_SITE() throws Exception {
        int databaseSizeBeforeUpdate = pHY_SITERepository.findAll().size();

        // Create the PHY_SITE

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPHY_SITEMockMvc.perform(put("/api/p-hy-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pHY_SITE)))
            .andExpect(status().isCreated());

        // Validate the PHY_SITE in the database
        List<PHY_SITE> pHY_SITEList = pHY_SITERepository.findAll();
        assertThat(pHY_SITEList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePHY_SITE() throws Exception {
        // Initialize the database
        pHY_SITERepository.saveAndFlush(pHY_SITE);
        int databaseSizeBeforeDelete = pHY_SITERepository.findAll().size();

        // Get the pHY_SITE
        restPHY_SITEMockMvc.perform(delete("/api/p-hy-sites/{id}", pHY_SITE.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PHY_SITE> pHY_SITEList = pHY_SITERepository.findAll();
        assertThat(pHY_SITEList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PHY_SITE.class);
        PHY_SITE pHY_SITE1 = new PHY_SITE();
        pHY_SITE1.setId(1L);
        PHY_SITE pHY_SITE2 = new PHY_SITE();
        pHY_SITE2.setId(pHY_SITE1.getId());
        assertThat(pHY_SITE1).isEqualTo(pHY_SITE2);
        pHY_SITE2.setId(2L);
        assertThat(pHY_SITE1).isNotEqualTo(pHY_SITE2);
        pHY_SITE1.setId(null);
        assertThat(pHY_SITE1).isNotEqualTo(pHY_SITE2);
    }
}
