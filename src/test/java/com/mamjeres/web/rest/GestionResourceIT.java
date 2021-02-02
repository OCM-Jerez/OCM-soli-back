package com.mamjeres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mamjeres.SolicitudesApp;
import com.mamjeres.domain.Gestion;
import com.mamjeres.repository.GestionRepository;
import com.mamjeres.service.GestionService;
import com.mamjeres.service.dto.GestionDTO;
import com.mamjeres.service.mapper.GestionMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link GestionResource} REST controller.
 */
@SpringBootTest(classes = SolicitudesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GestionResourceIT {
    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOMBRE_DE_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DE_DOCUMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIVADO = false;
    private static final Boolean UPDATED_PRIVADO = true;

    @Autowired
    private GestionRepository gestionRepository;

    @Autowired
    private GestionMapper gestionMapper;

    @Autowired
    private GestionService gestionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGestionMockMvc;

    private Gestion gestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gestion createEntity(EntityManager em) {
        Gestion gestion = new Gestion()
            .detalle(DEFAULT_DETALLE)
            .fecha(DEFAULT_FECHA)
            .observacion(DEFAULT_OBSERVACION)
            .documento(DEFAULT_DOCUMENTO)
            .documentoContentType(DEFAULT_DOCUMENTO_CONTENT_TYPE)
            .nombreDeDocumento(DEFAULT_NOMBRE_DE_DOCUMENTO)
            .privado(DEFAULT_PRIVADO);
        return gestion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gestion createUpdatedEntity(EntityManager em) {
        Gestion gestion = new Gestion()
            .detalle(UPDATED_DETALLE)
            .fecha(UPDATED_FECHA)
            .observacion(UPDATED_OBSERVACION)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .nombreDeDocumento(UPDATED_NOMBRE_DE_DOCUMENTO)
            .privado(UPDATED_PRIVADO);
        return gestion;
    }

    @BeforeEach
    public void initTest() {
        gestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createGestion() throws Exception {
        int databaseSizeBeforeCreate = gestionRepository.findAll().size();
        // Create the Gestion
        GestionDTO gestionDTO = gestionMapper.toDto(gestion);
        restGestionMockMvc
            .perform(post("/api/gestions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionDTO)))
            .andExpect(status().isCreated());

        // Validate the Gestion in the database
        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeCreate + 1);
        Gestion testGestion = gestionList.get(gestionList.size() - 1);
        assertThat(testGestion.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testGestion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testGestion.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testGestion.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testGestion.getDocumentoContentType()).isEqualTo(DEFAULT_DOCUMENTO_CONTENT_TYPE);
        assertThat(testGestion.getNombreDeDocumento()).isEqualTo(DEFAULT_NOMBRE_DE_DOCUMENTO);
        assertThat(testGestion.isPrivado()).isEqualTo(DEFAULT_PRIVADO);
    }

    @Test
    @Transactional
    public void createGestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gestionRepository.findAll().size();

        // Create the Gestion with an existing ID
        gestion.setId(1L);
        GestionDTO gestionDTO = gestionMapper.toDto(gestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestionMockMvc
            .perform(post("/api/gestions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gestion in the database
        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDetalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionRepository.findAll().size();
        // set the field null
        gestion.setDetalle(null);

        // Create the Gestion, which fails.
        GestionDTO gestionDTO = gestionMapper.toDto(gestion);

        restGestionMockMvc
            .perform(post("/api/gestions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionDTO)))
            .andExpect(status().isBadRequest());

        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionRepository.findAll().size();
        // set the field null
        gestion.setFecha(null);

        // Create the Gestion, which fails.
        GestionDTO gestionDTO = gestionMapper.toDto(gestion);

        restGestionMockMvc
            .perform(post("/api/gestions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionDTO)))
            .andExpect(status().isBadRequest());

        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreDeDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gestionRepository.findAll().size();
        // set the field null
        gestion.setNombreDeDocumento(null);

        // Create the Gestion, which fails.
        GestionDTO gestionDTO = gestionMapper.toDto(gestion);

        restGestionMockMvc
            .perform(post("/api/gestions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionDTO)))
            .andExpect(status().isBadRequest());

        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGestions() throws Exception {
        // Initialize the database
        gestionRepository.saveAndFlush(gestion);

        // Get all the gestionList
        restGestionMockMvc
            .perform(get("/api/gestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)))
            .andExpect(jsonPath("$.[*].documentoContentType").value(hasItem(DEFAULT_DOCUMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO))))
            .andExpect(jsonPath("$.[*].nombreDeDocumento").value(hasItem(DEFAULT_NOMBRE_DE_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].privado").value(hasItem(DEFAULT_PRIVADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getGestion() throws Exception {
        // Initialize the database
        gestionRepository.saveAndFlush(gestion);

        // Get the gestion
        restGestionMockMvc
            .perform(get("/api/gestions/{id}", gestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gestion.getId().intValue()))
            .andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION))
            .andExpect(jsonPath("$.documentoContentType").value(DEFAULT_DOCUMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documento").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.nombreDeDocumento").value(DEFAULT_NOMBRE_DE_DOCUMENTO))
            .andExpect(jsonPath("$.privado").value(DEFAULT_PRIVADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGestion() throws Exception {
        // Get the gestion
        restGestionMockMvc.perform(get("/api/gestions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGestion() throws Exception {
        // Initialize the database
        gestionRepository.saveAndFlush(gestion);

        int databaseSizeBeforeUpdate = gestionRepository.findAll().size();

        // Update the gestion
        Gestion updatedGestion = gestionRepository.findById(gestion.getId()).get();
        // Disconnect from session so that the updates on updatedGestion are not directly saved in db
        em.detach(updatedGestion);
        updatedGestion
            .detalle(UPDATED_DETALLE)
            .fecha(UPDATED_FECHA)
            .observacion(UPDATED_OBSERVACION)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .nombreDeDocumento(UPDATED_NOMBRE_DE_DOCUMENTO)
            .privado(UPDATED_PRIVADO);
        GestionDTO gestionDTO = gestionMapper.toDto(updatedGestion);

        restGestionMockMvc
            .perform(put("/api/gestions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionDTO)))
            .andExpect(status().isOk());

        // Validate the Gestion in the database
        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeUpdate);
        Gestion testGestion = gestionList.get(gestionList.size() - 1);
        assertThat(testGestion.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testGestion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testGestion.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testGestion.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testGestion.getDocumentoContentType()).isEqualTo(UPDATED_DOCUMENTO_CONTENT_TYPE);
        assertThat(testGestion.getNombreDeDocumento()).isEqualTo(UPDATED_NOMBRE_DE_DOCUMENTO);
        assertThat(testGestion.isPrivado()).isEqualTo(UPDATED_PRIVADO);
    }

    @Test
    @Transactional
    public void updateNonExistingGestion() throws Exception {
        int databaseSizeBeforeUpdate = gestionRepository.findAll().size();

        // Create the Gestion
        GestionDTO gestionDTO = gestionMapper.toDto(gestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionMockMvc
            .perform(put("/api/gestions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gestion in the database
        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGestion() throws Exception {
        // Initialize the database
        gestionRepository.saveAndFlush(gestion);

        int databaseSizeBeforeDelete = gestionRepository.findAll().size();

        // Delete the gestion
        restGestionMockMvc
            .perform(delete("/api/gestions/{id}", gestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gestion> gestionList = gestionRepository.findAll();
        assertThat(gestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
