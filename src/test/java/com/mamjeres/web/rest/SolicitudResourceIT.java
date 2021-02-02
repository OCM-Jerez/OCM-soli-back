package com.mamjeres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mamjeres.SolicitudesApp;
import com.mamjeres.domain.Solicitud;
import com.mamjeres.repository.SolicitudRepository;
import com.mamjeres.service.SolicitudService;
import com.mamjeres.service.dto.SolicitudDTO;
import com.mamjeres.service.mapper.SolicitudMapper;
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
 * Integration tests for the {@link SolicitudResource} REST controller.
 */
@SpringBootTest(classes = SolicitudesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SolicitudResourceIT {
    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_SOLICITUD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SOLICITUD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_RESPUESTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_RESPUESTA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_CONTENT_TYPE = "image/png";

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolicitudMockMvc;

    private Solicitud solicitud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createEntity(EntityManager em) {
        Solicitud solicitud = new Solicitud()
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaSolicitud(DEFAULT_FECHA_SOLICITUD)
            .fechaRespuesta(DEFAULT_FECHA_RESPUESTA)
            .observacion(DEFAULT_OBSERVACION)
            .documento(DEFAULT_DOCUMENTO)
            .documentoContentType(DEFAULT_DOCUMENTO_CONTENT_TYPE);
        return solicitud;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createUpdatedEntity(EntityManager em) {
        Solicitud solicitud = new Solicitud()
            .descripcion(UPDATED_DESCRIPCION)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .fechaRespuesta(UPDATED_FECHA_RESPUESTA)
            .observacion(UPDATED_OBSERVACION)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE);
        return solicitud;
    }

    @BeforeEach
    public void initTest() {
        solicitud = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitud() throws Exception {
        int databaseSizeBeforeCreate = solicitudRepository.findAll().size();
        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);
        restSolicitudMockMvc
            .perform(
                post("/api/solicituds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate + 1);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSolicitud.getFechaSolicitud()).isEqualTo(DEFAULT_FECHA_SOLICITUD);
        assertThat(testSolicitud.getFechaRespuesta()).isEqualTo(DEFAULT_FECHA_RESPUESTA);
        assertThat(testSolicitud.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testSolicitud.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testSolicitud.getDocumentoContentType()).isEqualTo(DEFAULT_DOCUMENTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSolicitudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitudRepository.findAll().size();

        // Create the Solicitud with an existing ID
        solicitud.setId(1L);
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitudMockMvc
            .perform(
                post("/api/solicituds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setDescripcion(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        restSolicitudMockMvc
            .perform(
                post("/api/solicituds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaSolicitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setFechaSolicitud(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        restSolicitudMockMvc
            .perform(
                post("/api/solicituds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolicituds() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        // Get all the solicitudList
        restSolicitudMockMvc
            .perform(get("/api/solicituds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitud.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaSolicitud").value(hasItem(DEFAULT_FECHA_SOLICITUD.toString())))
            .andExpect(jsonPath("$.[*].fechaRespuesta").value(hasItem(DEFAULT_FECHA_RESPUESTA.toString())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)))
            .andExpect(jsonPath("$.[*].documentoContentType").value(hasItem(DEFAULT_DOCUMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO))));
    }

    @Test
    @Transactional
    public void getSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        // Get the solicitud
        restSolicitudMockMvc
            .perform(get("/api/solicituds/{id}", solicitud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicitud.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fechaSolicitud").value(DEFAULT_FECHA_SOLICITUD.toString()))
            .andExpect(jsonPath("$.fechaRespuesta").value(DEFAULT_FECHA_RESPUESTA.toString()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION))
            .andExpect(jsonPath("$.documentoContentType").value(DEFAULT_DOCUMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documento").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO)));
    }

    @Test
    @Transactional
    public void getNonExistingSolicitud() throws Exception {
        // Get the solicitud
        restSolicitudMockMvc.perform(get("/api/solicituds/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        int databaseSizeBeforeUpdate = solicitudRepository.findAll().size();

        // Update the solicitud
        Solicitud updatedSolicitud = solicitudRepository.findById(solicitud.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitud are not directly saved in db
        em.detach(updatedSolicitud);
        updatedSolicitud
            .descripcion(UPDATED_DESCRIPCION)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .fechaRespuesta(UPDATED_FECHA_RESPUESTA)
            .observacion(UPDATED_OBSERVACION)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE);
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(updatedSolicitud);

        restSolicitudMockMvc
            .perform(
                put("/api/solicituds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            )
            .andExpect(status().isOk());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSolicitud.getFechaSolicitud()).isEqualTo(UPDATED_FECHA_SOLICITUD);
        assertThat(testSolicitud.getFechaRespuesta()).isEqualTo(UPDATED_FECHA_RESPUESTA);
        assertThat(testSolicitud.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testSolicitud.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testSolicitud.getDocumentoContentType()).isEqualTo(UPDATED_DOCUMENTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().size();

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitudMockMvc
            .perform(
                put("/api/solicituds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        int databaseSizeBeforeDelete = solicitudRepository.findAll().size();

        // Delete the solicitud
        restSolicitudMockMvc
            .perform(delete("/api/solicituds/{id}", solicitud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
