package com.mamjeres.service.impl;

import com.mamjeres.domain.Documento;
import com.mamjeres.domain.Solicitud;
import com.mamjeres.domain.User;
import com.mamjeres.repository.DocumentoRepository;
import com.mamjeres.repository.SolicitudRepository;
import com.mamjeres.repository.UserRepository;
import com.mamjeres.security.AuthoritiesConstants;
import com.mamjeres.service.DocumentoService;
import com.mamjeres.service.dto.DocumentoDTO;
import com.mamjeres.service.mapper.DocumentoMapper;
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
 * Service Implementation for managing {@link Documento}.
 */
@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService {
    private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private final DocumentoRepository documentoRepository;

    private final UserRepository userRepository;

    private final SolicitudRepository solicitudRepository;

    private final DocumentoMapper documentoMapper;

    public DocumentoServiceImpl(
        DocumentoRepository documentoRepository,
        UserRepository userRepository,
        SolicitudRepository solicitudRepository,
        DocumentoMapper documentoMapper
    ) {
        this.documentoRepository = documentoRepository;
        this.userRepository = userRepository;
        this.solicitudRepository = solicitudRepository;
        this.documentoMapper = documentoMapper;
    }

    @Override
    public DocumentoDTO save(DocumentoDTO documentoDTO) {
        log.debug("Request to save Documento : {}", documentoDTO);
        Documento documento = documentoMapper.toEntity(documentoDTO);
        documento = documentoRepository.save(documento);
        return documentoMapper.toDto(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll(pageable).map(documentoMapper::toDto);
    }

    @Override
    public List<DocumentoDTO> findAllByUsuarioAndSolicitud(Long solicitudId, Long usuarioId) {
        User user = this.userRepository.getOne(usuarioId);
        Solicitud solicitud = this.solicitudRepository.getOne(solicitudId);
        List<Documento> list = this.documentoRepository.findAllBySolicitud(solicitud);
        List<Documento> listFiltered = new ArrayList<>();
        list.forEach(
            documento -> {
                if (!documento.isPrivado()) {
                    listFiltered.add(documento);
                }
                if (user.getAuthorities().contains(AuthoritiesConstants.ADMIN) && documento.isPrivado()) {
                    //            if(user.getAuthorities().stream().filter(authority -> authority == AuthoritiesConstants.ADMIN).){
                    listFiltered.add(documento);
                }
            }
        );

        return this.documentoMapper.toDto(listFiltered);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentoDTO> findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id).map(documentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Documento : {}", id);
        documentoRepository.deleteById(id);
    }
}
