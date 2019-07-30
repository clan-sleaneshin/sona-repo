package io.github.jdubois.bugtracker.web.rest;

import io.github.jdubois.bugtracker.BugtrackerApp;
import io.github.jdubois.bugtracker.domain.Sworker;
import io.github.jdubois.bugtracker.repository.SworkerRepository;
import io.github.jdubois.bugtracker.service.SworkerService;
import io.github.jdubois.bugtracker.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.jdubois.bugtracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SworkerResource} REST controller.
 */
@SpringBootTest(classes = BugtrackerApp.class)
public class SworkerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final String DEFAULT_HAIR_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_HAIR_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_EYE_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_EYE_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_ETHNICITY = "AAAAAAAAAA";
    private static final String UPDATED_ETHNICITY = "BBBBBBBBBB";

    @Autowired
    private SworkerRepository sworkerRepository;

    @Autowired
    private SworkerService sworkerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSworkerMockMvc;

    private Sworker sworker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SworkerResource sworkerResource = new SworkerResource(sworkerService);
        this.restSworkerMockMvc = MockMvcBuilders.standaloneSetup(sworkerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sworker createEntity(EntityManager em) {
        Sworker sworker = new Sworker()
            .name(DEFAULT_NAME)
            .nationality(DEFAULT_NATIONALITY)
            .birthDate(DEFAULT_BIRTH_DATE)
            .height(DEFAULT_HEIGHT)
            .hairColor(DEFAULT_HAIR_COLOR)
            .eyeColor(DEFAULT_EYE_COLOR)
            .ethnicity(DEFAULT_ETHNICITY);
        return sworker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sworker createUpdatedEntity(EntityManager em) {
        Sworker sworker = new Sworker()
            .name(UPDATED_NAME)
            .nationality(UPDATED_NATIONALITY)
            .birthDate(UPDATED_BIRTH_DATE)
            .height(UPDATED_HEIGHT)
            .hairColor(UPDATED_HAIR_COLOR)
            .eyeColor(UPDATED_EYE_COLOR)
            .ethnicity(UPDATED_ETHNICITY);
        return sworker;
    }

    @BeforeEach
    public void initTest() {
        sworker = createEntity(em);
    }

    @Test
    @Transactional
    public void createSworker() throws Exception {
        int databaseSizeBeforeCreate = sworkerRepository.findAll().size();

        // Create the Sworker
        restSworkerMockMvc.perform(post("/api/sworkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sworker)))
            .andExpect(status().isCreated());

        // Validate the Sworker in the database
        List<Sworker> sworkerList = sworkerRepository.findAll();
        assertThat(sworkerList).hasSize(databaseSizeBeforeCreate + 1);
        Sworker testSworker = sworkerList.get(sworkerList.size() - 1);
        assertThat(testSworker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSworker.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testSworker.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testSworker.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testSworker.getHairColor()).isEqualTo(DEFAULT_HAIR_COLOR);
        assertThat(testSworker.getEyeColor()).isEqualTo(DEFAULT_EYE_COLOR);
        assertThat(testSworker.getEthnicity()).isEqualTo(DEFAULT_ETHNICITY);
    }

    @Test
    @Transactional
    public void createSworkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sworkerRepository.findAll().size();

        // Create the Sworker with an existing ID
        sworker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSworkerMockMvc.perform(post("/api/sworkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sworker)))
            .andExpect(status().isBadRequest());

        // Validate the Sworker in the database
        List<Sworker> sworkerList = sworkerRepository.findAll();
        assertThat(sworkerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sworkerRepository.findAll().size();
        // set the field null
        sworker.setName(null);

        // Create the Sworker, which fails.

        restSworkerMockMvc.perform(post("/api/sworkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sworker)))
            .andExpect(status().isBadRequest());

        List<Sworker> sworkerList = sworkerRepository.findAll();
        assertThat(sworkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSworkers() throws Exception {
        // Initialize the database
        sworkerRepository.saveAndFlush(sworker);

        // Get all the sworkerList
        restSworkerMockMvc.perform(get("/api/sworkers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sworker.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].hairColor").value(hasItem(DEFAULT_HAIR_COLOR.toString())))
            .andExpect(jsonPath("$.[*].eyeColor").value(hasItem(DEFAULT_EYE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].ethnicity").value(hasItem(DEFAULT_ETHNICITY.toString())));
    }
    
    @Test
    @Transactional
    public void getSworker() throws Exception {
        // Initialize the database
        sworkerRepository.saveAndFlush(sworker);

        // Get the sworker
        restSworkerMockMvc.perform(get("/api/sworkers/{id}", sworker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sworker.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.hairColor").value(DEFAULT_HAIR_COLOR.toString()))
            .andExpect(jsonPath("$.eyeColor").value(DEFAULT_EYE_COLOR.toString()))
            .andExpect(jsonPath("$.ethnicity").value(DEFAULT_ETHNICITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSworker() throws Exception {
        // Get the sworker
        restSworkerMockMvc.perform(get("/api/sworkers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSworker() throws Exception {
        // Initialize the database
        sworkerService.save(sworker);

        int databaseSizeBeforeUpdate = sworkerRepository.findAll().size();

        // Update the sworker
        Sworker updatedSworker = sworkerRepository.findById(sworker.getId()).get();
        // Disconnect from session so that the updates on updatedSworker are not directly saved in db
        em.detach(updatedSworker);
        updatedSworker
            .name(UPDATED_NAME)
            .nationality(UPDATED_NATIONALITY)
            .birthDate(UPDATED_BIRTH_DATE)
            .height(UPDATED_HEIGHT)
            .hairColor(UPDATED_HAIR_COLOR)
            .eyeColor(UPDATED_EYE_COLOR)
            .ethnicity(UPDATED_ETHNICITY);

        restSworkerMockMvc.perform(put("/api/sworkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSworker)))
            .andExpect(status().isOk());

        // Validate the Sworker in the database
        List<Sworker> sworkerList = sworkerRepository.findAll();
        assertThat(sworkerList).hasSize(databaseSizeBeforeUpdate);
        Sworker testSworker = sworkerList.get(sworkerList.size() - 1);
        assertThat(testSworker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSworker.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testSworker.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testSworker.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testSworker.getHairColor()).isEqualTo(UPDATED_HAIR_COLOR);
        assertThat(testSworker.getEyeColor()).isEqualTo(UPDATED_EYE_COLOR);
        assertThat(testSworker.getEthnicity()).isEqualTo(UPDATED_ETHNICITY);
    }

    @Test
    @Transactional
    public void updateNonExistingSworker() throws Exception {
        int databaseSizeBeforeUpdate = sworkerRepository.findAll().size();

        // Create the Sworker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSworkerMockMvc.perform(put("/api/sworkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sworker)))
            .andExpect(status().isBadRequest());

        // Validate the Sworker in the database
        List<Sworker> sworkerList = sworkerRepository.findAll();
        assertThat(sworkerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSworker() throws Exception {
        // Initialize the database
        sworkerService.save(sworker);

        int databaseSizeBeforeDelete = sworkerRepository.findAll().size();

        // Delete the sworker
        restSworkerMockMvc.perform(delete("/api/sworkers/{id}", sworker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Sworker> sworkerList = sworkerRepository.findAll();
        assertThat(sworkerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sworker.class);
        Sworker sworker1 = new Sworker();
        sworker1.setId(1L);
        Sworker sworker2 = new Sworker();
        sworker2.setId(sworker1.getId());
        assertThat(sworker1).isEqualTo(sworker2);
        sworker2.setId(2L);
        assertThat(sworker1).isNotEqualTo(sworker2);
        sworker1.setId(null);
        assertThat(sworker1).isNotEqualTo(sworker2);
    }
}
