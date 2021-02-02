package com.mamjeres.repository;

import com.mamjeres.domain.Documento;
import com.mamjeres.domain.Gestion;
import com.mamjeres.domain.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Gestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GestionRepository extends JpaRepository<Gestion, Long> {
    List<Gestion> findAllBySolicitud(Solicitud solicitud);
}
