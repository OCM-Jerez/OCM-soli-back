package com.mamjeres.service.mapper;

import com.mamjeres.domain.*;
import com.mamjeres.service.dto.SolicitudDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Solicitud} and its DTO {@link SolicitudDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolicitudMapper extends EntityMapper<SolicitudDTO, Solicitud> {
    default Solicitud fromId(Long id) {
        if (id == null) {
            return null;
        }
        Solicitud solicitud = new Solicitud();
        solicitud.setId(id);
        return solicitud;
    }
}
