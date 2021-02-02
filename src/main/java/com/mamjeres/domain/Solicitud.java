package com.mamjeres.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Solicitud.
 */
@Entity
@Table(name = "solicitud")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Solicitud implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_respuesta")
    private LocalDate fechaRespuesta;

    @Column(name = "observacion")
    private String observacion;

    @Lob
    @Column(name = "documento")
    private byte[] documento;

    @Column(name = "documento_content_type")
    private String documentoContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Solicitud descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public Solicitud fechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        return this;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaRespuesta() {
        return fechaRespuesta;
    }

    public Solicitud fechaRespuesta(LocalDate fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
        return this;
    }

    public void setFechaRespuesta(LocalDate fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getObservacion() {
        return observacion;
    }

    public Solicitud observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public Solicitud documento(byte[] documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getDocumentoContentType() {
        return documentoContentType;
    }

    public Solicitud documentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
        return this;
    }

    public void setDocumentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solicitud)) {
            return false;
        }
        return id != null && id.equals(((Solicitud) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Solicitud{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            ", fechaRespuesta='" + getFechaRespuesta() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", documentoContentType='" + getDocumentoContentType() + "'" +
            "}";
    }
}
