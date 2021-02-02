package com.mamjeres.service;

import com.mamjeres.service.dto.SolicitudDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mamjeres.domain.Solicitud}.
 */
public interface SolicitudService {
    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save.
     * @return the persisted entity.
     */
    SolicitudDTO save(SolicitudDTO solicitudDTO);

    /**
     * Get all the solicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SolicitudDTO> findAll(Pageable pageable);

    /**
     * Get the "id" solicitud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SolicitudDTO> findOne(Long id);

    /**
     * Delete the "id" solicitud.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
