/* eslint-disable @typescript-eslint/no-unused-vars */
import { Entity, Column, PrimaryGeneratedColumn, JoinColumn, OneToOne, ManyToOne, OneToMany, ManyToMany, JoinTable } from 'typeorm';
import { BaseEntity } from './base/base.entity';

import { validate, Contains, IsInt, Length, IsEmail, IsFQDN, IsDate, Min, Max } from 'class-validator';

import Solicitud from './solicitud.entity';
import Gestion from './gestion.entity';

/**
 * A Documento.
 */
@Entity('documento')
export default class Documento extends BaseEntity {
  @Column({ name: 'nombre_de_documento' })
  nombreDeDocumento: string;

  @Column({ type: 'date', name: 'fecha_subida' })
  fechaSubida: any;

  @Column({ type: 'blob', name: 'documento', nullable: true })
  documento: any;

  @Column({ name: 'documento_content_type', nullable: true })
  documentoContentType: string;
  
  @Column({ name: 'observacion', nullable: true })
  observacion: string;

  @Column({ name: 'ruta', nullable: true })
  ruta: string;

  @Column({ type: 'boolean', name: 'privado', nullable: true })
  privado: boolean;

  @ManyToOne(type => Solicitud)
  solicitud: Solicitud;

  solicitudId?: string;

  @ManyToOne(type => Gestion)
  gestion: Gestion;

  // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
}
