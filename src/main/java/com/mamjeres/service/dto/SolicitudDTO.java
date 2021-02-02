package com.mamjeres.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mamjeres.domain.Solicitud} entity.
 */
public class SolicitudDTO implements Serializable {
    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    private LocalDate fechaSolicitud;

    private LocalDate fechaRespuesta;

    private String observacion;

    @Lob
    private byte[] documento;

    private String documentoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDate fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getDocumentoContentType() {
        return documentoContentType;
    }

    public void setDocumentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicitudDTO)) {
            return false;
        }

        return id != null && id.equals(((SolicitudDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicitudDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            ", fechaRespuesta='" + getFechaRespuesta() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", documento='" + getDocumento() + "'" +
            "}";
    }
}
