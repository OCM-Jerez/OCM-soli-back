/* eslint-disable @typescript-eslint/no-unused-vars */
import { Entity, Column, PrimaryGeneratedColumn, JoinColumn, OneToOne, ManyToOne, OneToMany, ManyToMany, JoinTable } from 'typeorm';
import { BaseEntity } from './base/base.entity';

import { validate, Contains, IsInt, Length, IsEmail, IsFQDN, IsDate, Min, Max } from 'class-validator';

import Solicitud from './solicitud.entity';

/**
 * A Gestion.
 */
@Entity('gestion')
export default class Gestion extends BaseEntity {
  @Column({ name: 'detalle' })
  detalle: string;

  @Column({ type: 'date', name: 'fecha' })
  fecha: any;

  @Column({ name: 'observacion', nullable: true })
  observacion: string;

  @Column({ type: 'blob', name: 'documento', nullable: true })
  documento: any;

  @Column({ name: 'documento_content_type', nullable: true })
  documentoContentType: string;

  @Column({ name: 'nombre_de_documento' })
  nombreDeDocumento: string;

  @Column({ type: 'boolean', name: 'privado', nullable: true })
  privado: boolean;

  @ManyToOne(type => Solicitud)
  solicitud: Solicitud;

  solicitudId?: string;

  // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
}
