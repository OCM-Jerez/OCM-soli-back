package com.mamjeres.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gestion.
 */
@Entity
@Table(name = "gestion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gestion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "detalle", nullable = false)
    private String detalle;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "observacion")
    private String observacion;

    @Lob
    @Column(name = "documento")
    private byte[] documento;

    @Column(name = "documento_content_type")
    private String documentoContentType;

    @NotNull
    @Column(name = "nombre_de_documento", nullable = false)
    private String nombreDeDocumento;

    @Column(name = "privado")
    private Boolean privado;

    @ManyToOne
    @JsonIgnoreProperties(value = "gestions", allowSetters = true)
    private Solicitud solicitud;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public Gestion detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Gestion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public Gestion observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public Gestion documento(byte[] documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getDocumentoContentType() {
        return documentoContentType;
    }

    public Gestion documentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
        return this;
    }

    public void setDocumentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
    }

    public String getNombreDeDocumento() {
        return nombreDeDocumento;
    }

    public Gestion nombreDeDocumento(String nombreDeDocumento) {
        this.nombreDeDocumento = nombreDeDocumento;
        return this;
    }

    public void setNombreDeDocumento(String nombreDeDocumento) {
        this.nombreDeDocumento = nombreDeDocumento;
    }

    public Boolean isPrivado() {
        return privado;
    }

    public Gestion privado(Boolean privado) {
        this.privado = privado;
        return this;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public Gestion solicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        return this;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gestion)) {
            return false;
        }
        return id != null && id.equals(((Gestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gestion{" +
            "id=" + getId() +
            ", detalle='" + getDetalle() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", documentoContentType='" + getDocumentoContentType() + "'" +
            ", nombreDeDocumento='" + getNombreDeDocumento() + "'" +
            ", privado='" + isPrivado() + "'" +
            "}";
    }
}
