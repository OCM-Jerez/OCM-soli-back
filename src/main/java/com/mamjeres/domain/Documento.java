package com.mamjeres.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Documento.
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Documento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_de_documento", nullable = false)
    private String nombreDeDocumento;

    @NotNull
    @Column(name = "fecha_subida", nullable = false)
    private LocalDate fechaSubida;

    @Lob
    @Column(name = "documento")
    private byte[] documento;

    @Column(name = "documento_content_type")
    private String documentoContentType;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "privado")
    private Boolean privado;

    @ManyToOne
    @JsonIgnoreProperties(value = "documentos", allowSetters = true)
    private Solicitud solicitud;

    @ManyToOne
    @JsonIgnoreProperties(value = "documentos", allowSetters = true)
    private Gestion gestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDeDocumento() {
        return nombreDeDocumento;
    }

    public Documento nombreDeDocumento(String nombreDeDocumento) {
        this.nombreDeDocumento = nombreDeDocumento;
        return this;
    }

    public void setNombreDeDocumento(String nombreDeDocumento) {
        this.nombreDeDocumento = nombreDeDocumento;
    }

    public LocalDate getFechaSubida() {
        return fechaSubida;
    }

    public Documento fechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
        return this;
    }

    public void setFechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public Documento documento(byte[] documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getDocumentoContentType() {
        return documentoContentType;
    }

    public Documento documentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
        return this;
    }

    public void setDocumentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
    }

    public String getObservacion() {
        return observacion;
    }

    public Documento observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRuta() {
        return ruta;
    }

    public Documento ruta(String ruta) {
        this.ruta = ruta;
        return this;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Boolean isPrivado() {
        return privado;
    }

    public Documento privado(Boolean privado) {
        this.privado = privado;
        return this;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public Documento solicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        return this;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Gestion getGestion() {
        return gestion;
    }

    public Documento gestion(Gestion gestion) {
        this.gestion = gestion;
        return this;
    }

    public void setGestion(Gestion gestion) {
        this.gestion = gestion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documento)) {
            return false;
        }
        return id != null && id.equals(((Documento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", nombreDeDocumento='" + getNombreDeDocumento() + "'" +
            ", fechaSubida='" + getFechaSubida() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", documentoContentType='" + getDocumentoContentType() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", ruta='" + getRuta() + "'" +
            ", privado='" + isPrivado() + "'" +
            "}";
    }
}
