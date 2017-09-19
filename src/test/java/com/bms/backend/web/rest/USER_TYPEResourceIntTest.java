package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.USER_TYPE;
import com.bms.backend.repository.USER_TYPERepository;
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
 * Test class for the USER_TYPEResource REST controller.
 *
 * @see USER_TYPEResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class USER_TYPEResourceIntTest {

    private static final String DEFAULT_C_ODE = "AAAAAAAAAA";
    private static final String UPDATED_C_ODE = "BBBBBBBBBB";

    private static final String DEFAULT_D_ESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_D_ESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private USER_TYPERepository uSER_TYPERepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUSER_TYPEMockMvc;

    private USER_TYPE uSER_TYPE;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        USER_TYPEResource uSER_TYPEResource = new USER_TYPEResource(uSER_TYPERepository);
        this.restUSER_TYPEMockMvc = MockMvcBuilders.standaloneSetup(uSER_TYPEResource)
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
    public static USER_TYPE createEntity(EntityManager em) {
        USER_TYPE uSER_TYPE = new USER_TYPE()
            .cOde(DEFAULT_C_ODE)
            .dEscription(DEFAULT_D_ESCRIPTION);
        return uSER_TYPE;
    }

    @Before
    public void initTest() {
        uSER_TYPE = createEntity(em);
    }

    @Test
    @Transactional
    public void createUSER_TYPE() throws Exception {
        int databaseSizeBeforeCreate = uSER_TYPERepository.findAll().size();

        // Create the USER_TYPE
        restUSER_TYPEMockMvc.perform(post("/api/u-ser-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uSER_TYPE)))
            .andExpect(status().isCreated());

        // Validate the USER_TYPE in the database
        List<USER_TYPE> uSER_TYPEList = uSER_TYPERepository.findAll();
        assertThat(uSER_TYPEList).hasSize(databaseSizeBeforeCreate + 1);
        USER_TYPE testUSER_TYPE = uSER_TYPEList.get(uSER_TYPEList.size() - 1);
        assertThat(testUSER_TYPE.getcOde()).isEqualTo(DEFAULT_C_ODE);
        assertThat(testUSER_TYPE.getdEscription()).isEqualTo(DEFAULT_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void createUSER_TYPEWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uSER_TYPERepository.findAll().size();

        // Create the USER_TYPE with an existing ID
        uSER_TYPE.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUSER_TYPEMockMvc.perform(post("/api/u-ser-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uSER_TYPE)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<USER_TYPE> uSER_TYPEList = uSER_TYPERepository.findAll();
        assertThat(uSER_TYPEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUSER_TYPES() throws Exception {
        // Initialize the database
        uSER_TYPERepository.saveAndFlush(uSER_TYPE);

        // Get all the uSER_TYPEList
        restUSER_TYPEMockMvc.perform(get("/api/u-ser-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uSER_TYPE.getId().intValue())))
            .andExpect(jsonPath("$.[*].cOde").value(hasItem(DEFAULT_C_ODE.toString())))
            .andExpect(jsonPath("$.[*].dEscription").value(hasItem(DEFAULT_D_ESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getUSER_TYPE() throws Exception {
        // Initialize the database
        uSER_TYPERepository.saveAndFlush(uSER_TYPE);

        // Get the uSER_TYPE
        restUSER_TYPEMockMvc.perform(get("/api/u-ser-types/{id}", uSER_TYPE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uSER_TYPE.getId().intValue()))
            .andExpect(jsonPath("$.cOde").value(DEFAULT_C_ODE.toString()))
            .andExpect(jsonPath("$.dEscription").value(DEFAULT_D_ESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUSER_TYPE() throws Exception {
        // Get the uSER_TYPE
        restUSER_TYPEMockMvc.perform(get("/api/u-ser-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUSER_TYPE() throws Exception {
        // Initialize the database
        uSER_TYPERepository.saveAndFlush(uSER_TYPE);
        int databaseSizeBeforeUpdate = uSER_TYPERepository.findAll().size();

        // Update the uSER_TYPE
        USER_TYPE updatedUSER_TYPE = uSER_TYPERepository.findOne(uSER_TYPE.getId());
        updatedUSER_TYPE
            .cOde(UPDATED_C_ODE)
            .dEscription(UPDATED_D_ESCRIPTION);

        restUSER_TYPEMockMvc.perform(put("/api/u-ser-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUSER_TYPE)))
            .andExpect(status().isOk());

        // Validate the USER_TYPE in the database
        List<USER_TYPE> uSER_TYPEList = uSER_TYPERepository.findAll();
        assertThat(uSER_TYPEList).hasSize(databaseSizeBeforeUpdate);
        USER_TYPE testUSER_TYPE = uSER_TYPEList.get(uSER_TYPEList.size() - 1);
        assertThat(testUSER_TYPE.getcOde()).isEqualTo(UPDATED_C_ODE);
        assertThat(testUSER_TYPE.getdEscription()).isEqualTo(UPDATED_D_ESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingUSER_TYPE() throws Exception {
        int databaseSizeBeforeUpdate = uSER_TYPERepository.findAll().size();

        // Create the USER_TYPE

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUSER_TYPEMockMvc.perform(put("/api/u-ser-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uSER_TYPE)))
            .andExpect(status().isCreated());

        // Validate the USER_TYPE in the database
        List<USER_TYPE> uSER_TYPEList = uSER_TYPERepository.findAll();
        assertThat(uSER_TYPEList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUSER_TYPE() throws Exception {
        // Initialize the database
        uSER_TYPERepository.saveAndFlush(uSER_TYPE);
        int databaseSizeBeforeDelete = uSER_TYPERepository.findAll().size();

        // Get the uSER_TYPE
        restUSER_TYPEMockMvc.perform(delete("/api/u-ser-types/{id}", uSER_TYPE.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<USER_TYPE> uSER_TYPEList = uSER_TYPERepository.findAll();
        assertThat(uSER_TYPEList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(USER_TYPE.class);
        USER_TYPE uSER_TYPE1 = new USER_TYPE();
        uSER_TYPE1.setId(1L);
        USER_TYPE uSER_TYPE2 = new USER_TYPE();
        uSER_TYPE2.setId(uSER_TYPE1.getId());
        assertThat(uSER_TYPE1).isEqualTo(uSER_TYPE2);
        uSER_TYPE2.setId(2L);
        assertThat(uSER_TYPE1).isNotEqualTo(uSER_TYPE2);
        uSER_TYPE1.setId(null);
        assertThat(uSER_TYPE1).isNotEqualTo(uSER_TYPE2);
    }
}
