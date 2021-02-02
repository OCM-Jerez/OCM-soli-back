package com.mamjeres.web.rest;

import com.mamjeres.service.GestionService;
import com.mamjeres.service.dto.GestionDTO;
import com.mamjeres.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.mamjeres.domain.Gestion}.
 */
@RestController
@RequestMapping("/api")
public class GestionResource {
    private final Logger log = LoggerFactory.getLogger(GestionResource.class);

    private static final String ENTITY_NAME = "gestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GestionService gestionService;

    public GestionResource(GestionService gestionService) {
        this.gestionService = gestionService;
    }

    /**
     * {@code POST  /gestions} : Create a new gestion.
     *
     * @param gestionDTO the gestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gestionDTO, or with status {@code 400 (Bad Request)} if the gestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gestions")
    public ResponseEntity<GestionDTO> createGestion(@Valid @RequestBody GestionDTO gestionDTO) throws URISyntaxException {
        log.debug("REST request to save Gestion : {}", gestionDTO);
        if (gestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new gestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GestionDTO result = gestionService.save(gestionDTO);
        return ResponseEntity
            .created(new URI("/api/gestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gestions} : Updates an existing gestion.
     *
     * @param gestionDTO the gestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionDTO,
     * or with status {@code 400 (Bad Request)} if the gestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gestions")
    public ResponseEntity<GestionDTO> updateGestion(@Valid @RequestBody GestionDTO gestionDTO) throws URISyntaxException {
        log.debug("REST request to update Gestion : {}", gestionDTO);
        if (gestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GestionDTO result = gestionService.save(gestionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gestions} : get all the gestions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gestions in body.
     */
    @GetMapping("/gestions")
    public ResponseEntity<List<GestionDTO>> getAllGestions(Pageable pageable) {
        log.debug("REST request to get a page of Gestions");
        Page<GestionDTO> page = gestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gestions/:id} : get the "id" gestion.
     *
     * @param id the id of the gestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gestions/{id}")
    public ResponseEntity<GestionDTO> getGestion(@PathVariable Long id) {
        log.debug("REST request to get Gestion : {}", id);
        Optional<GestionDTO> gestionDTO = gestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gestionDTO);
    }

    /**
     * {@code DELETE  /gestions/:id} : delete the "id" gestion.
     *
     * @param id the id of the gestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gestions/{id}")
    public ResponseEntity<Void> deleteGestion(@PathVariable Long id) {
        log.debug("REST request to delete Gestion : {}", id);
        gestionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
