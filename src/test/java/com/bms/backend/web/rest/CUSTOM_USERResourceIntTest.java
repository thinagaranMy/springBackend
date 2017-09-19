package com.bms.backend.web.rest;

import com.bms.backend.AppApp;

import com.bms.backend.domain.CUSTOM_USER;
import com.bms.backend.repository.CUSTOM_USERRepository;
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
 * Test class for the CUSTOM_USERResource REST controller.
 *
 * @see CUSTOM_USERResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class CUSTOM_USERResourceIntTest {

    @Autowired
    private CUSTOM_USERRepository cUSTOM_USERRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCUSTOM_USERMockMvc;

    private CUSTOM_USER cUSTOM_USER;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CUSTOM_USERResource cUSTOM_USERResource = new CUSTOM_USERResource(cUSTOM_USERRepository);
        this.restCUSTOM_USERMockMvc = MockMvcBuilders.standaloneSetup(cUSTOM_USERResource)
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
    public static CUSTOM_USER createEntity(EntityManager em) {
        CUSTOM_USER cUSTOM_USER = new CUSTOM_USER();
        return cUSTOM_USER;
    }

    @Before
    public void initTest() {
        cUSTOM_USER = createEntity(em);
    }

    @Test
    @Transactional
    public void createCUSTOM_USER() throws Exception {
        int databaseSizeBeforeCreate = cUSTOM_USERRepository.findAll().size();

        // Create the CUSTOM_USER
        restCUSTOM_USERMockMvc.perform(post("/api/c-ustom-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTOM_USER)))
            .andExpect(status().isCreated());

        // Validate the CUSTOM_USER in the database
        List<CUSTOM_USER> cUSTOM_USERList = cUSTOM_USERRepository.findAll();
        assertThat(cUSTOM_USERList).hasSize(databaseSizeBeforeCreate + 1);
        CUSTOM_USER testCUSTOM_USER = cUSTOM_USERList.get(cUSTOM_USERList.size() - 1);
    }

    @Test
    @Transactional
    public void createCUSTOM_USERWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cUSTOM_USERRepository.findAll().size();

        // Create the CUSTOM_USER with an existing ID
        cUSTOM_USER.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCUSTOM_USERMockMvc.perform(post("/api/c-ustom-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTOM_USER)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CUSTOM_USER> cUSTOM_USERList = cUSTOM_USERRepository.findAll();
        assertThat(cUSTOM_USERList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCUSTOM_USERS() throws Exception {
        // Initialize the database
        cUSTOM_USERRepository.saveAndFlush(cUSTOM_USER);

        // Get all the cUSTOM_USERList
        restCUSTOM_USERMockMvc.perform(get("/api/c-ustom-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cUSTOM_USER.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCUSTOM_USER() throws Exception {
        // Initialize the database
        cUSTOM_USERRepository.saveAndFlush(cUSTOM_USER);

        // Get the cUSTOM_USER
        restCUSTOM_USERMockMvc.perform(get("/api/c-ustom-users/{id}", cUSTOM_USER.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cUSTOM_USER.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCUSTOM_USER() throws Exception {
        // Get the cUSTOM_USER
        restCUSTOM_USERMockMvc.perform(get("/api/c-ustom-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCUSTOM_USER() throws Exception {
        // Initialize the database
        cUSTOM_USERRepository.saveAndFlush(cUSTOM_USER);
        int databaseSizeBeforeUpdate = cUSTOM_USERRepository.findAll().size();

        // Update the cUSTOM_USER
        CUSTOM_USER updatedCUSTOM_USER = cUSTOM_USERRepository.findOne(cUSTOM_USER.getId());

        restCUSTOM_USERMockMvc.perform(put("/api/c-ustom-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCUSTOM_USER)))
            .andExpect(status().isOk());

        // Validate the CUSTOM_USER in the database
        List<CUSTOM_USER> cUSTOM_USERList = cUSTOM_USERRepository.findAll();
        assertThat(cUSTOM_USERList).hasSize(databaseSizeBeforeUpdate);
        CUSTOM_USER testCUSTOM_USER = cUSTOM_USERList.get(cUSTOM_USERList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCUSTOM_USER() throws Exception {
        int databaseSizeBeforeUpdate = cUSTOM_USERRepository.findAll().size();

        // Create the CUSTOM_USER

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCUSTOM_USERMockMvc.perform(put("/api/c-ustom-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTOM_USER)))
            .andExpect(status().isCreated());

        // Validate the CUSTOM_USER in the database
        List<CUSTOM_USER> cUSTOM_USERList = cUSTOM_USERRepository.findAll();
        assertThat(cUSTOM_USERList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCUSTOM_USER() throws Exception {
        // Initialize the database
        cUSTOM_USERRepository.saveAndFlush(cUSTOM_USER);
        int databaseSizeBeforeDelete = cUSTOM_USERRepository.findAll().size();

        // Get the cUSTOM_USER
        restCUSTOM_USERMockMvc.perform(delete("/api/c-ustom-users/{id}", cUSTOM_USER.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CUSTOM_USER> cUSTOM_USERList = cUSTOM_USERRepository.findAll();
        assertThat(cUSTOM_USERList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CUSTOM_USER.class);
        CUSTOM_USER cUSTOM_USER1 = new CUSTOM_USER();
        cUSTOM_USER1.setId(1L);
        CUSTOM_USER cUSTOM_USER2 = new CUSTOM_USER();
        cUSTOM_USER2.setId(cUSTOM_USER1.getId());
        assertThat(cUSTOM_USER1).isEqualTo(cUSTOM_USER2);
        cUSTOM_USER2.setId(2L);
        assertThat(cUSTOM_USER1).isNotEqualTo(cUSTOM_USER2);
        cUSTOM_USER1.setId(null);
        assertThat(cUSTOM_USER1).isNotEqualTo(cUSTOM_USER2);
    }
}
