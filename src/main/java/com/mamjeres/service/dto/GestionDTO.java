package com.mamjeres.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mamjeres.domain.Gestion} entity.
 */
public class GestionDTO implements Serializable {
    private Long id;

    @NotNull
    private String detalle;

    @NotNull
    private LocalDate fecha;

    private String observacion;

    @Lob
    private byte[] documento;

    private String documentoContentType;

    @NotNull
    private String nombreDeDocumento;

    private Boolean privado;

    private Long solicitudId;

    private String solicitudDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public String getNombreDeDocumento() {
        return nombreDeDocumento;
    }

    public void setNombreDeDocumento(String nombreDeDocumento) {
        this.nombreDeDocumento = nombreDeDocumento;
    }

    public Boolean isPrivado() {
        return privado;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }

    public String getSolicitudDescripcion() {
        return solicitudDescripcion;
    }

    public void setSolicitudDescripcion(String solicitudDescripcion) {
        this.solicitudDescripcion = solicitudDescripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestionDTO)) {
            return false;
        }

        return id != null && id.equals(((GestionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestionDTO{" +
            "id=" + getId() +
            ", detalle='" + getDetalle() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", nombreDeDocumento='" + getNombreDeDocumento() + "'" +
            ", privado='" + isPrivado() + "'" +
            ", solicitudId=" + getSolicitudId() +
            ", solicitudDescripcion='" + getSolicitudDescripcion() + "'" +
            "}";
    }
}
