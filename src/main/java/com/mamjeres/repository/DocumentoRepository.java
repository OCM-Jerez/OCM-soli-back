package com.mamjeres.repository;

import com.mamjeres.domain.Documento;
import com.mamjeres.domain.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findAllBySolicitud(Solicitud solicitud);
}
