package com.mamjeres.service;

import com.mamjeres.service.dto.GestionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mamjeres.domain.Gestion}.
 */
public interface GestionService {
    /**
     * Save a gestion.
     *
     * @param gestionDTO the entity to save.
     * @return the persisted entity.
     */
    GestionDTO save(GestionDTO gestionDTO);

    /**
     * Get all the gestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GestionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GestionDTO> findOne(Long id);

    /**
     * Delete the "id" gestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
