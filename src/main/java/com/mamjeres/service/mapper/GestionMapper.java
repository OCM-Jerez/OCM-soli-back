package com.mamjeres.service.mapper;

import com.mamjeres.domain.*;
import com.mamjeres.service.dto.GestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gestion} and its DTO {@link GestionDTO}.
 */
@Mapper(componentModel = "spring", uses = { SolicitudMapper.class })
public interface GestionMapper extends EntityMapper<GestionDTO, Gestion> {
    @Mapping(source = "solicitud.id", target = "solicitudId")
    @Mapping(source = "solicitud.descripcion", target = "solicitudDescripcion")
    GestionDTO toDto(Gestion gestion);

    @Mapping(source = "solicitudId", target = "solicitud")
    Gestion toEntity(GestionDTO gestionDTO);

    default Gestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gestion gestion = new Gestion();
        gestion.setId(id);
        return gestion;
    }
}
