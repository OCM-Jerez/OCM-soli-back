package com.mamjeres.service.impl;

import com.mamjeres.domain.Documento;
import com.mamjeres.domain.Gestion;
import com.mamjeres.domain.Solicitud;
import com.mamjeres.domain.User;
import com.mamjeres.repository.GestionRepository;
import com.mamjeres.repository.SolicitudRepository;
import com.mamjeres.repository.UserRepository;
import com.mamjeres.security.AuthoritiesConstants;
import com.mamjeres.service.GestionService;
import com.mamjeres.service.dto.GestionDTO;
import com.mamjeres.service.mapper.GestionMapper;
import java.util.ArrayList;
import java.util.List;
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

    private final UserRepository userRepository;

    private final SolicitudRepository solicitudRepository;

    public GestionServiceImpl(
        GestionRepository gestionRepository,
        GestionMapper gestionMapper,
        UserRepository userRepository,
        SolicitudRepository solicitudRepository
    ) {
        this.gestionRepository = gestionRepository;
        this.gestionMapper = gestionMapper;
        this.userRepository = userRepository;
        this.solicitudRepository = solicitudRepository;
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
    public List<GestionDTO> findAllByUsuarioAndSolicitud(Long solicitudId, Long usuarioId) {
        User user = this.userRepository.getOne(usuarioId);
        Solicitud solicitud = this.solicitudRepository.getOne(solicitudId);
        List<Gestion> list = this.gestionRepository.findAllBySolicitud(solicitud);
        List<Gestion> listFiltered = new ArrayList<>();
        list.forEach(
            gestion -> {
                if (!gestion.isPrivado()) {
                    listFiltered.add(gestion);
                }
                if (user.getAuthorities().contains(AuthoritiesConstants.ADMIN) && gestion.isPrivado()) {
                    //            if(user.getAuthorities().stream().filter(authority -> authority == AuthoritiesConstants.ADMIN).){
                    listFiltered.add(gestion);
                }
            }
        );

        return this.gestionMapper.toDto(listFiltered);
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
