package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.COUNTRY;
import com.bms.backend.repository.COUNTRYRepository;
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
 * Test class for the COUNTRYResource REST controller.
 *
 * @see COUNTRYResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class COUNTRYResourceIntTest {

    private static final String DEFAULT_C_ODE = "AAAAAAAAAA";
    private static final String UPDATED_C_ODE = "BBBBBBBBBB";

    private static final String DEFAULT_D_ESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_D_ESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private COUNTRYRepository cOUNTRYRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCOUNTRYMockMvc;

    private COUNTRY cOUNTRY;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        COUNTRYResource cOUNTRYResource = new COUNTRYResource(cOUNTRYRepository);
        this.restCOUNTRYMockMvc = MockMvcBuilders.standaloneSetup(cOUNTRYResource)
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
    public static COUNTRY createEntity(EntityManager em) {
        COUNTRY cOUNTRY = new COUNTRY()
            .cOde(DEFAULT_C_ODE)
            .dEscription(DEFAULT_D_ESCRIPTION);
        return cOUNTRY;
    }

    @Before
    public void initTest() {
        cOUNTRY = createEntity(em);
    }

    @Test
    @Transactional
    public void createCOUNTRY() throws Exception {
        int databaseSizeBeforeCreate = cOUNTRYRepository.findAll().size();

        // Create the COUNTRY
        restCOUNTRYMockMvc.perform(post("/api/c-ountries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cOUNTRY)))
            .andExpect(status().isCreated());

        // Validate the COUNTRY in the database
        List<COUNTRY> cOUNTRYList = cOUNTRYRepository.findAll();
        assertThat(cOUNTRYList).hasSize(databaseSizeBeforeCreate + 1);
        COUNTRY testCOUNTRY = cOUNTRYList.get(cOUNTRYList.size() - 1);
        assertThat(testCOUNTRY.getcOde()).isEqualTo(DEFAULT_C_ODE);
        assertThat(testCOUNTRY.getdEscription()).isEqualTo(DEFAULT_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void createCOUNTRYWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cOUNTRYRepository.findAll().size();

        // Create the COUNTRY with an existing ID
        cOUNTRY.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCOUNTRYMockMvc.perform(post("/api/c-ountries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cOUNTRY)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<COUNTRY> cOUNTRYList = cOUNTRYRepository.findAll();
        assertThat(cOUNTRYList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCOUNTRIES() throws Exception {
        // Initialize the database
        cOUNTRYRepository.saveAndFlush(cOUNTRY);

        // Get all the cOUNTRYList
        restCOUNTRYMockMvc.perform(get("/api/c-ountries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cOUNTRY.getId().intValue())))
            .andExpect(jsonPath("$.[*].cOde").value(hasItem(DEFAULT_C_ODE.toString())))
            .andExpect(jsonPath("$.[*].dEscription").value(hasItem(DEFAULT_D_ESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCOUNTRY() throws Exception {
        // Initialize the database
        cOUNTRYRepository.saveAndFlush(cOUNTRY);

        // Get the cOUNTRY
        restCOUNTRYMockMvc.perform(get("/api/c-ountries/{id}", cOUNTRY.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cOUNTRY.getId().intValue()))
            .andExpect(jsonPath("$.cOde").value(DEFAULT_C_ODE.toString()))
            .andExpect(jsonPath("$.dEscription").value(DEFAULT_D_ESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCOUNTRY() throws Exception {
        // Get the cOUNTRY
        restCOUNTRYMockMvc.perform(get("/api/c-ountries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCOUNTRY() throws Exception {
        // Initialize the database
        cOUNTRYRepository.saveAndFlush(cOUNTRY);
        int databaseSizeBeforeUpdate = cOUNTRYRepository.findAll().size();

        // Update the cOUNTRY
        COUNTRY updatedCOUNTRY = cOUNTRYRepository.findOne(cOUNTRY.getId());
        updatedCOUNTRY
            .cOde(UPDATED_C_ODE)
            .dEscription(UPDATED_D_ESCRIPTION);

        restCOUNTRYMockMvc.perform(put("/api/c-ountries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCOUNTRY)))
            .andExpect(status().isOk());

        // Validate the COUNTRY in the database
        List<COUNTRY> cOUNTRYList = cOUNTRYRepository.findAll();
        assertThat(cOUNTRYList).hasSize(databaseSizeBeforeUpdate);
        COUNTRY testCOUNTRY = cOUNTRYList.get(cOUNTRYList.size() - 1);
        assertThat(testCOUNTRY.getcOde()).isEqualTo(UPDATED_C_ODE);
        assertThat(testCOUNTRY.getdEscription()).isEqualTo(UPDATED_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCOUNTRY() throws Exception {
        int databaseSizeBeforeUpdate = cOUNTRYRepository.findAll().size();

        // Create the COUNTRY

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCOUNTRYMockMvc.perform(put("/api/c-ountries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cOUNTRY)))
            .andExpect(status().isCreated());

        // Validate the COUNTRY in the database
        List<COUNTRY> cOUNTRYList = cOUNTRYRepository.findAll();
        assertThat(cOUNTRYList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCOUNTRY() throws Exception {
        // Initialize the database
        cOUNTRYRepository.saveAndFlush(cOUNTRY);
        int databaseSizeBeforeDelete = cOUNTRYRepository.findAll().size();

        // Get the cOUNTRY
        restCOUNTRYMockMvc.perform(delete("/api/c-ountries/{id}", cOUNTRY.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<COUNTRY> cOUNTRYList = cOUNTRYRepository.findAll();
        assertThat(cOUNTRYList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(COUNTRY.class);
        COUNTRY cOUNTRY1 = new COUNTRY();
        cOUNTRY1.setId(1L);
        COUNTRY cOUNTRY2 = new COUNTRY();
        cOUNTRY2.setId(cOUNTRY1.getId());
        assertThat(cOUNTRY1).isEqualTo(cOUNTRY2);
        cOUNTRY2.setId(2L);
        assertThat(cOUNTRY1).isNotEqualTo(cOUNTRY2);
        cOUNTRY1.setId(null);
        assertThat(cOUNTRY1).isNotEqualTo(cOUNTRY2);
    }
}
