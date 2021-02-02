package com.mamjeres.service.impl;

import com.mamjeres.domain.Solicitud;
import com.mamjeres.repository.SolicitudRepository;
import com.mamjeres.service.SolicitudService;
import com.mamjeres.service.dto.SolicitudDTO;
import com.mamjeres.service.mapper.SolicitudMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Solicitud}.
 */
@Service
@Transactional
public class SolicitudServiceImpl implements SolicitudService {
    private final Logger log = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    private final SolicitudRepository solicitudRepository;

    private final SolicitudMapper solicitudMapper;

    public SolicitudServiceImpl(SolicitudRepository solicitudRepository, SolicitudMapper solicitudMapper) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public SolicitudDTO save(SolicitudDTO solicitudDTO) {
        log.debug("Request to save Solicitud : {}", solicitudDTO);
        Solicitud solicitud = solicitudMapper.toEntity(solicitudDTO);
        solicitud = solicitudRepository.save(solicitud);
        return solicitudMapper.toDto(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SolicitudDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Solicituds");
        return solicitudRepository.findAll(pageable).map(solicitudMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SolicitudDTO> findOne(Long id) {
        log.debug("Request to get Solicitud : {}", id);
        return solicitudRepository.findById(id).map(solicitudMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Solicitud : {}", id);
        solicitudRepository.deleteById(id);
    }
}
