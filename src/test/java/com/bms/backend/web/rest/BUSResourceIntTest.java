package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.BUS;
import com.bms.backend.repository.BUSRepository;
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
 * Test class for the BUSResource REST controller.
 *
 * @see BUSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class BUSResourceIntTest {

    private static final String DEFAULT_C_APACITY = "AAAAAAAAAA";
    private static final String UPDATED_C_APACITY = "BBBBBBBBBB";

    private static final String DEFAULT_R_EGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_R_EGISTRATION_NUMBER = "BBBBBBBBBB";

    @Autowired
    private BUSRepository bUSRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBUSMockMvc;

    private BUS bUS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BUSResource bUSResource = new BUSResource(bUSRepository);
        this.restBUSMockMvc = MockMvcBuilders.standaloneSetup(bUSResource)
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
    public static BUS createEntity(EntityManager em) {
        BUS bUS = new BUS()
            .cApacity(DEFAULT_C_APACITY)
            .rEgistrationNumber(DEFAULT_R_EGISTRATION_NUMBER);
        return bUS;
    }

    @Before
    public void initTest() {
        bUS = createEntity(em);
    }

    @Test
    @Transactional
    public void createBUS() throws Exception {
        int databaseSizeBeforeCreate = bUSRepository.findAll().size();

        // Create the BUS
        restBUSMockMvc.perform(post("/api/b-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bUS)))
            .andExpect(status().isCreated());

        // Validate the BUS in the database
        List<BUS> bUSList = bUSRepository.findAll();
        assertThat(bUSList).hasSize(databaseSizeBeforeCreate + 1);
        BUS testBUS = bUSList.get(bUSList.size() - 1);
        assertThat(testBUS.getcApacity()).isEqualTo(DEFAULT_C_APACITY);
        assertThat(testBUS.getrEgistrationNumber()).isEqualTo(DEFAULT_R_EGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void createBUSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bUSRepository.findAll().size();

        // Create the BUS with an existing ID
        bUS.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBUSMockMvc.perform(post("/api/b-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bUS)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BUS> bUSList = bUSRepository.findAll();
        assertThat(bUSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBUSES() throws Exception {
        // Initialize the database
        bUSRepository.saveAndFlush(bUS);

        // Get all the bUSList
        restBUSMockMvc.perform(get("/api/b-uses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bUS.getId().intValue())))
            .andExpect(jsonPath("$.[*].cApacity").value(hasItem(DEFAULT_C_APACITY.toString())))
            .andExpect(jsonPath("$.[*].rEgistrationNumber").value(hasItem(DEFAULT_R_EGISTRATION_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getBUS() throws Exception {
        // Initialize the database
        bUSRepository.saveAndFlush(bUS);

        // Get the bUS
        restBUSMockMvc.perform(get("/api/b-uses/{id}", bUS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bUS.getId().intValue()))
            .andExpect(jsonPath("$.cApacity").value(DEFAULT_C_APACITY.toString()))
            .andExpect(jsonPath("$.rEgistrationNumber").value(DEFAULT_R_EGISTRATION_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBUS() throws Exception {
        // Get the bUS
        restBUSMockMvc.perform(get("/api/b-uses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBUS() throws Exception {
        // Initialize the database
        bUSRepository.saveAndFlush(bUS);
        int databaseSizeBeforeUpdate = bUSRepository.findAll().size();

        // Update the bUS
        BUS updatedBUS = bUSRepository.findOne(bUS.getId());
        updatedBUS
            .cApacity(UPDATED_C_APACITY)
            .rEgistrationNumber(UPDATED_R_EGISTRATION_NUMBER);

        restBUSMockMvc.perform(put("/api/b-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBUS)))
            .andExpect(status().isOk());

        // Validate the BUS in the database
        List<BUS> bUSList = bUSRepository.findAll();
        assertThat(bUSList).hasSize(databaseSizeBeforeUpdate);
        BUS testBUS = bUSList.get(bUSList.size() - 1);
        assertThat(testBUS.getcApacity()).isEqualTo(UPDATED_C_APACITY);
        assertThat(testBUS.getrEgistrationNumber()).isEqualTo(UPDATED_R_EGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingBUS() throws Exception {
        int databaseSizeBeforeUpdate = bUSRepository.findAll().size();

        // Create the BUS

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBUSMockMvc.perform(put("/api/b-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bUS)))
            .andExpect(status().isCreated());

        // Validate the BUS in the database
        List<BUS> bUSList = bUSRepository.findAll();
        assertThat(bUSList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBUS() throws Exception {
        // Initialize the database
        bUSRepository.saveAndFlush(bUS);
        int databaseSizeBeforeDelete = bUSRepository.findAll().size();

        // Get the bUS
        restBUSMockMvc.perform(delete("/api/b-uses/{id}", bUS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BUS> bUSList = bUSRepository.findAll();
        assertThat(bUSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BUS.class);
        BUS bUS1 = new BUS();
        bUS1.setId(1L);
        BUS bUS2 = new BUS();
        bUS2.setId(bUS1.getId());
        assertThat(bUS1).isEqualTo(bUS2);
        bUS2.setId(2L);
        assertThat(bUS1).isNotEqualTo(bUS2);
        bUS1.setId(null);
        assertThat(bUS1).isNotEqualTo(bUS2);
    }
}
