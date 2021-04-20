import { Entity, Column, ManyToOne } from 'typeorm';
import { BaseEntity } from './base/base.entity';

import Solicitud from './solicitud.entity';
@Entity('gestion')
export default class Gestion extends BaseEntity {
  @Column({ name: 'detalle' , nullable: true})
  detalle: string;

  @Column({ type: 'date', name: 'fecha' })
  fecha: any;

  @Column({ name: 'observacion', nullable: true })
  observacion: string;

  @Column({ type: 'blob', name: 'documento', nullable: true })
  documento: any;

  @Column({ name: 'documentoType', nullable: true })
  documentoType: string;

  @Column({ name: 'nombreDocumento' })
  nombreDocumento: string;

  @Column({ type: 'boolean', name: 'privado', nullable: true })
  privado: boolean;

  @ManyToOne(type => Solicitud)
  solicitud: Solicitud;

  solicitudId?: string;

}
