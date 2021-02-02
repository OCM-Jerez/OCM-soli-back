package com.mamjeres.service.mapper;

import com.mamjeres.domain.*;
import com.mamjeres.service.dto.DocumentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documento} and its DTO {@link DocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = { SolicitudMapper.class, GestionMapper.class })
public interface DocumentoMapper extends EntityMapper<DocumentoDTO, Documento> {
    @Mapping(source = "solicitud.id", target = "solicitudId")
    @Mapping(source = "solicitud.descripcion", target = "solicitudDescripcion")
    @Mapping(source = "gestion.id", target = "gestionId")
    @Mapping(source = "gestion.detalle", target = "gestionDetalle")
    DocumentoDTO toDto(Documento documento);

    @Mapping(source = "solicitudId", target = "solicitud")
    @Mapping(source = "gestionId", target = "gestion")
    Documento toEntity(DocumentoDTO documentoDTO);

    default Documento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Documento documento = new Documento();
        documento.setId(id);
        return documento;
    }
}
