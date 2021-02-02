package com.mamjeres.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mamjeres.domain.Documento} entity.
 */
public class DocumentoDTO implements Serializable {
    private Long id;

    @NotNull
    private String nombreDeDocumento;

    @NotNull
    private LocalDate fechaSubida;

    @Lob
    private byte[] documento;

    private String documentoContentType;
    private String observacion;

    private String ruta;

    private Boolean privado;

    private Long solicitudId;

    private String solicitudDescripcion;

    private Long gestionId;

    private String gestionDetalle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDeDocumento() {
        return nombreDeDocumento;
    }

    public void setNombreDeDocumento(String nombreDeDocumento) {
        this.nombreDeDocumento = nombreDeDocumento;
    }

    public LocalDate getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
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

    public Long getGestionId() {
        return gestionId;
    }

    public void setGestionId(Long gestionId) {
        this.gestionId = gestionId;
    }

    public String getGestionDetalle() {
        return gestionDetalle;
    }

    public void setGestionDetalle(String gestionDetalle) {
        this.gestionDetalle = gestionDetalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentoDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentoDTO{" +
            "id=" + getId() +
            ", nombreDeDocumento='" + getNombreDeDocumento() + "'" +
            ", fechaSubida='" + getFechaSubida() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", ruta='" + getRuta() + "'" +
            ", privado='" + isPrivado() + "'" +
            ", solicitudId=" + getSolicitudId() +
            ", solicitudDescripcion='" + getSolicitudDescripcion() + "'" +
            ", gestionId=" + getGestionId() +
            ", gestionDetalle='" + getGestionDetalle() + "'" +
            "}";
    }
}
