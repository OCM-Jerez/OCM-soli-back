package com.mamjeres.service;

import com.mamjeres.service.dto.DocumentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mamjeres.domain.Documento}.
 */
public interface DocumentoService {
    /**
     * Save a documento.
     *
     * @param documentoDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentoDTO save(DocumentoDTO documentoDTO);

    /**
     * Get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" documento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentoDTO> findOne(Long id);

    /**
     * Delete the "id" documento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
