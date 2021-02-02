package com.mamjeres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mamjeres.SolicitudesApp;
import com.mamjeres.domain.Documento;
import com.mamjeres.repository.DocumentoRepository;
import com.mamjeres.service.DocumentoService;
import com.mamjeres.service.dto.DocumentoDTO;
import com.mamjeres.service.mapper.DocumentoMapper;
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
 * Integration tests for the {@link DocumentoResource} REST controller.
 */
@SpringBootTest(classes = SolicitudesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocumentoResourceIT {
    private static final String DEFAULT_NOMBRE_DE_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DE_DOCUMENTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_SUBIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SUBIDA = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_DOCUMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final String DEFAULT_RUTA = "AAAAAAAAAA";
    private static final String UPDATED_RUTA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIVADO = false;
    private static final Boolean UPDATED_PRIVADO = true;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentoMockMvc;

    private Documento documento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createEntity(EntityManager em) {
        Documento documento = new Documento()
            .nombreDeDocumento(DEFAULT_NOMBRE_DE_DOCUMENTO)
            .fechaSubida(DEFAULT_FECHA_SUBIDA)
            .documento(DEFAULT_DOCUMENTO)
            .documentoContentType(DEFAULT_DOCUMENTO_CONTENT_TYPE)
            .observacion(DEFAULT_OBSERVACION)
            .ruta(DEFAULT_RUTA)
            .privado(DEFAULT_PRIVADO);
        return documento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createUpdatedEntity(EntityManager em) {
        Documento documento = new Documento()
            .nombreDeDocumento(UPDATED_NOMBRE_DE_DOCUMENTO)
            .fechaSubida(UPDATED_FECHA_SUBIDA)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .observacion(UPDATED_OBSERVACION)
            .ruta(UPDATED_RUTA)
            .privado(UPDATED_PRIVADO);
        return documento;
    }

    @BeforeEach
    public void initTest() {
        documento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();
        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);
        restDocumentoMockMvc
            .perform(
                post("/api/documentos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNombreDeDocumento()).isEqualTo(DEFAULT_NOMBRE_DE_DOCUMENTO);
        assertThat(testDocumento.getFechaSubida()).isEqualTo(DEFAULT_FECHA_SUBIDA);
        assertThat(testDocumento.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testDocumento.getDocumentoContentType()).isEqualTo(DEFAULT_DOCUMENTO_CONTENT_TYPE);
        assertThat(testDocumento.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testDocumento.getRuta()).isEqualTo(DEFAULT_RUTA);
        assertThat(testDocumento.isPrivado()).isEqualTo(DEFAULT_PRIVADO);
    }

    @Test
    @Transactional
    public void createDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento with an existing ID
        documento.setId(1L);
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoMockMvc
            .perform(
                post("/api/documentos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreDeDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoRepository.findAll().size();
        // set the field null
        documento.setNombreDeDocumento(null);

        // Create the Documento, which fails.
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        restDocumentoMockMvc
            .perform(
                post("/api/documentos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaSubidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoRepository.findAll().size();
        // set the field null
        documento.setFechaSubida(null);

        // Create the Documento, which fails.
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        restDocumentoMockMvc
            .perform(
                post("/api/documentos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentos() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList
        restDocumentoMockMvc
            .perform(get("/api/documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreDeDocumento").value(hasItem(DEFAULT_NOMBRE_DE_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].fechaSubida").value(hasItem(DEFAULT_FECHA_SUBIDA.toString())))
            .andExpect(jsonPath("$.[*].documentoContentType").value(hasItem(DEFAULT_DOCUMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO))))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)))
            .andExpect(jsonPath("$.[*].ruta").value(hasItem(DEFAULT_RUTA)))
            .andExpect(jsonPath("$.[*].privado").value(hasItem(DEFAULT_PRIVADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get the documento
        restDocumentoMockMvc
            .perform(get("/api/documentos/{id}", documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documento.getId().intValue()))
            .andExpect(jsonPath("$.nombreDeDocumento").value(DEFAULT_NOMBRE_DE_DOCUMENTO))
            .andExpect(jsonPath("$.fechaSubida").value(DEFAULT_FECHA_SUBIDA.toString()))
            .andExpect(jsonPath("$.documentoContentType").value(DEFAULT_DOCUMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documento").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION))
            .andExpect(jsonPath("$.ruta").value(DEFAULT_RUTA))
            .andExpect(jsonPath("$.privado").value(DEFAULT_PRIVADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumento() throws Exception {
        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findById(documento.getId()).get();
        // Disconnect from session so that the updates on updatedDocumento are not directly saved in db
        em.detach(updatedDocumento);
        updatedDocumento
            .nombreDeDocumento(UPDATED_NOMBRE_DE_DOCUMENTO)
            .fechaSubida(UPDATED_FECHA_SUBIDA)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .observacion(UPDATED_OBSERVACION)
            .ruta(UPDATED_RUTA)
            .privado(UPDATED_PRIVADO);
        DocumentoDTO documentoDTO = documentoMapper.toDto(updatedDocumento);

        restDocumentoMockMvc
            .perform(
                put("/api/documentos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNombreDeDocumento()).isEqualTo(UPDATED_NOMBRE_DE_DOCUMENTO);
        assertThat(testDocumento.getFechaSubida()).isEqualTo(UPDATED_FECHA_SUBIDA);
        assertThat(testDocumento.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testDocumento.getDocumentoContentType()).isEqualTo(UPDATED_DOCUMENTO_CONTENT_TYPE);
        assertThat(testDocumento.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testDocumento.getRuta()).isEqualTo(UPDATED_RUTA);
        assertThat(testDocumento.isPrivado()).isEqualTo(UPDATED_PRIVADO);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMockMvc
            .perform(
                put("/api/documentos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeDelete = documentoRepository.findAll().size();

        // Delete the documento
        restDocumentoMockMvc
            .perform(delete("/api/documentos/{id}", documento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
