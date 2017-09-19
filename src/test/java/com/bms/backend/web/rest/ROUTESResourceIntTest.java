package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.ROUTES;
import com.bms.backend.repository.ROUTESRepository;
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
 * Test class for the ROUTESResource REST controller.
 *
 * @see ROUTESResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class ROUTESResourceIntTest {

    private static final String DEFAULT_C_ODE = "AAAAAAAAAA";
    private static final String UPDATED_C_ODE = "BBBBBBBBBB";

    private static final String DEFAULT_D_ESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_D_ESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ROUTESRepository rOUTESRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restROUTESMockMvc;

    private ROUTES rOUTES;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ROUTESResource rOUTESResource = new ROUTESResource(rOUTESRepository);
        this.restROUTESMockMvc = MockMvcBuilders.standaloneSetup(rOUTESResource)
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
    public static ROUTES createEntity(EntityManager em) {
        ROUTES rOUTES = new ROUTES()
            .cOde(DEFAULT_C_ODE)
            .dEscription(DEFAULT_D_ESCRIPTION);
        return rOUTES;
    }

    @Before
    public void initTest() {
        rOUTES = createEntity(em);
    }

    @Test
    @Transactional
    public void createROUTES() throws Exception {
        int databaseSizeBeforeCreate = rOUTESRepository.findAll().size();

        // Create the ROUTES
        restROUTESMockMvc.perform(post("/api/r-outes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rOUTES)))
            .andExpect(status().isCreated());

        // Validate the ROUTES in the database
        List<ROUTES> rOUTESList = rOUTESRepository.findAll();
        assertThat(rOUTESList).hasSize(databaseSizeBeforeCreate + 1);
        ROUTES testROUTES = rOUTESList.get(rOUTESList.size() - 1);
        assertThat(testROUTES.getcOde()).isEqualTo(DEFAULT_C_ODE);
        assertThat(testROUTES.getdEscription()).isEqualTo(DEFAULT_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void createROUTESWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rOUTESRepository.findAll().size();

        // Create the ROUTES with an existing ID
        rOUTES.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restROUTESMockMvc.perform(post("/api/r-outes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rOUTES)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ROUTES> rOUTESList = rOUTESRepository.findAll();
        assertThat(rOUTESList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllROUTES() throws Exception {
        // Initialize the database
        rOUTESRepository.saveAndFlush(rOUTES);

        // Get all the rOUTESList
        restROUTESMockMvc.perform(get("/api/r-outes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rOUTES.getId().intValue())))
            .andExpect(jsonPath("$.[*].cOde").value(hasItem(DEFAULT_C_ODE.toString())))
            .andExpect(jsonPath("$.[*].dEscription").value(hasItem(DEFAULT_D_ESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getROUTES() throws Exception {
        // Initialize the database
        rOUTESRepository.saveAndFlush(rOUTES);

        // Get the rOUTES
        restROUTESMockMvc.perform(get("/api/r-outes/{id}", rOUTES.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rOUTES.getId().intValue()))
            .andExpect(jsonPath("$.cOde").value(DEFAULT_C_ODE.toString()))
            .andExpect(jsonPath("$.dEscription").value(DEFAULT_D_ESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingROUTES() throws Exception {
        // Get the rOUTES
        restROUTESMockMvc.perform(get("/api/r-outes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateROUTES() throws Exception {
        // Initialize the database
        rOUTESRepository.saveAndFlush(rOUTES);
        int databaseSizeBeforeUpdate = rOUTESRepository.findAll().size();

        // Update the rOUTES
        ROUTES updatedROUTES = rOUTESRepository.findOne(rOUTES.getId());
        updatedROUTES
            .cOde(UPDATED_C_ODE)
            .dEscription(UPDATED_D_ESCRIPTION);

        restROUTESMockMvc.perform(put("/api/r-outes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedROUTES)))
            .andExpect(status().isOk());

        // Validate the ROUTES in the database
        List<ROUTES> rOUTESList = rOUTESRepository.findAll();
        assertThat(rOUTESList).hasSize(databaseSizeBeforeUpdate);
        ROUTES testROUTES = rOUTESList.get(rOUTESList.size() - 1);
        assertThat(testROUTES.getcOde()).isEqualTo(UPDATED_C_ODE);
        assertThat(testROUTES.getdEscription()).isEqualTo(UPDATED_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingROUTES() throws Exception {
        int databaseSizeBeforeUpdate = rOUTESRepository.findAll().size();

        // Create the ROUTES

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restROUTESMockMvc.perform(put("/api/r-outes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rOUTES)))
            .andExpect(status().isCreated());

        // Validate the ROUTES in the database
        List<ROUTES> rOUTESList = rOUTESRepository.findAll();
        assertThat(rOUTESList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteROUTES() throws Exception {
        // Initialize the database
        rOUTESRepository.saveAndFlush(rOUTES);
        int databaseSizeBeforeDelete = rOUTESRepository.findAll().size();

        // Get the rOUTES
        restROUTESMockMvc.perform(delete("/api/r-outes/{id}", rOUTES.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ROUTES> rOUTESList = rOUTESRepository.findAll();
        assertThat(rOUTESList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ROUTES.class);
        ROUTES rOUTES1 = new ROUTES();
        rOUTES1.setId(1L);
        ROUTES rOUTES2 = new ROUTES();
        rOUTES2.setId(rOUTES1.getId());
        assertThat(rOUTES1).isEqualTo(rOUTES2);
        rOUTES2.setId(2L);
        assertThat(rOUTES1).isNotEqualTo(rOUTES2);
        rOUTES1.setId(null);
        assertThat(rOUTES1).isNotEqualTo(rOUTES2);
    }
}
