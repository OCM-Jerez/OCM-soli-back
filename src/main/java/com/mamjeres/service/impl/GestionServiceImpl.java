package com.mamjeres.service.impl;

import com.mamjeres.domain.Gestion;
import com.mamjeres.repository.GestionRepository;
import com.mamjeres.service.GestionService;
import com.mamjeres.service.dto.GestionDTO;
import com.mamjeres.service.mapper.GestionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gestion}.
 */
@Service
@Transactional
public class GestionServiceImpl implements GestionService {
    private final Logger log = LoggerFactory.getLogger(GestionServiceImpl.class);

    private final GestionRepository gestionRepository;

    private final GestionMapper gestionMapper;

    public GestionServiceImpl(GestionRepository gestionRepository, GestionMapper gestionMapper) {
        this.gestionRepository = gestionRepository;
        this.gestionMapper = gestionMapper;
    }

    @Override
    public GestionDTO save(GestionDTO gestionDTO) {
        log.debug("Request to save Gestion : {}", gestionDTO);
        Gestion gestion = gestionMapper.toEntity(gestionDTO);
        gestion = gestionRepository.save(gestion);
        return gestionMapper.toDto(gestion);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gestions");
        return gestionRepository.findAll(pageable).map(gestionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GestionDTO> findOne(Long id) {
        log.debug("Request to get Gestion : {}", id);
        return gestionRepository.findById(id).map(gestionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gestion : {}", id);
        gestionRepository.deleteById(id);
    }
}
