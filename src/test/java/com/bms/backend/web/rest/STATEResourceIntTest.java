package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.STATE;
import com.bms.backend.repository.STATERepository;
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
 * Test class for the STATEResource REST controller.
 *
 * @see STATEResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class STATEResourceIntTest {

    private static final String DEFAULT_C_ODE = "AAAAAAAAAA";
    private static final String UPDATED_C_ODE = "BBBBBBBBBB";

    private static final String DEFAULT_D_ESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_D_ESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private STATERepository sTATERepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSTATEMockMvc;

    private STATE sTATE;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        STATEResource sTATEResource = new STATEResource(sTATERepository);
        this.restSTATEMockMvc = MockMvcBuilders.standaloneSetup(sTATEResource)
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
    public static STATE createEntity(EntityManager em) {
        STATE sTATE = new STATE()
            .cOde(DEFAULT_C_ODE)
            .dEscription(DEFAULT_D_ESCRIPTION);
        return sTATE;
    }

    @Before
    public void initTest() {
        sTATE = createEntity(em);
    }

    @Test
    @Transactional
    public void createSTATE() throws Exception {
        int databaseSizeBeforeCreate = sTATERepository.findAll().size();

        // Create the STATE
        restSTATEMockMvc.perform(post("/api/s-tates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sTATE)))
            .andExpect(status().isCreated());

        // Validate the STATE in the database
        List<STATE> sTATEList = sTATERepository.findAll();
        assertThat(sTATEList).hasSize(databaseSizeBeforeCreate + 1);
        STATE testSTATE = sTATEList.get(sTATEList.size() - 1);
        assertThat(testSTATE.getcOde()).isEqualTo(DEFAULT_C_ODE);
        assertThat(testSTATE.getdEscription()).isEqualTo(DEFAULT_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void createSTATEWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sTATERepository.findAll().size();

        // Create the STATE with an existing ID
        sTATE.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSTATEMockMvc.perform(post("/api/s-tates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sTATE)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<STATE> sTATEList = sTATERepository.findAll();
        assertThat(sTATEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSTATES() throws Exception {
        // Initialize the database
        sTATERepository.saveAndFlush(sTATE);

        // Get all the sTATEList
        restSTATEMockMvc.perform(get("/api/s-tates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sTATE.getId().intValue())))
            .andExpect(jsonPath("$.[*].cOde").value(hasItem(DEFAULT_C_ODE.toString())))
            .andExpect(jsonPath("$.[*].dEscription").value(hasItem(DEFAULT_D_ESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSTATE() throws Exception {
        // Initialize the database
        sTATERepository.saveAndFlush(sTATE);

        // Get the sTATE
        restSTATEMockMvc.perform(get("/api/s-tates/{id}", sTATE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sTATE.getId().intValue()))
            .andExpect(jsonPath("$.cOde").value(DEFAULT_C_ODE.toString()))
            .andExpect(jsonPath("$.dEscription").value(DEFAULT_D_ESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSTATE() throws Exception {
        // Get the sTATE
        restSTATEMockMvc.perform(get("/api/s-tates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSTATE() throws Exception {
        // Initialize the database
        sTATERepository.saveAndFlush(sTATE);
        int databaseSizeBeforeUpdate = sTATERepository.findAll().size();

        // Update the sTATE
        STATE updatedSTATE = sTATERepository.findOne(sTATE.getId());
        updatedSTATE
            .cOde(UPDATED_C_ODE)
            .dEscription(UPDATED_D_ESCRIPTION);

        restSTATEMockMvc.perform(put("/api/s-tates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSTATE)))
            .andExpect(status().isOk());

        // Validate the STATE in the database
        List<STATE> sTATEList = sTATERepository.findAll();
        assertThat(sTATEList).hasSize(databaseSizeBeforeUpdate);
        STATE testSTATE = sTATEList.get(sTATEList.size() - 1);
        assertThat(testSTATE.getcOde()).isEqualTo(UPDATED_C_ODE);
        assertThat(testSTATE.getdEscription()).isEqualTo(UPDATED_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSTATE() throws Exception {
        int databaseSizeBeforeUpdate = sTATERepository.findAll().size();

        // Create the STATE

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSTATEMockMvc.perform(put("/api/s-tates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sTATE)))
            .andExpect(status().isCreated());

        // Validate the STATE in the database
        List<STATE> sTATEList = sTATERepository.findAll();
        assertThat(sTATEList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSTATE() throws Exception {
        // Initialize the database
        sTATERepository.saveAndFlush(sTATE);
        int databaseSizeBeforeDelete = sTATERepository.findAll().size();

        // Get the sTATE
        restSTATEMockMvc.perform(delete("/api/s-tates/{id}", sTATE.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<STATE> sTATEList = sTATERepository.findAll();
        assertThat(sTATEList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(STATE.class);
        STATE sTATE1 = new STATE();
        sTATE1.setId(1L);
        STATE sTATE2 = new STATE();
        sTATE2.setId(sTATE1.getId());
        assertThat(sTATE1).isEqualTo(sTATE2);
        sTATE2.setId(2L);
        assertThat(sTATE1).isNotEqualTo(sTATE2);
        sTATE1.setId(null);
        assertThat(sTATE1).isNotEqualTo(sTATE2);
    }
}
