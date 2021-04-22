import { Entity, Column, ManyToOne } from 'typeorm';
import { BaseEntity } from './base/base.entity';

import Solicitud from './solicitud.entity';
@Entity('gestion')
export default class Gestion extends BaseEntity {
  @Column({ name: 'detalle' })
  detalle: string;

  @Column({ type: 'date' })
  fecha: any;

  @Column({ nullable: true })
  observacion: string;
  
  @Column({ type: 'boolean', nullable: true })
  privado: boolean;

  @ManyToOne(type => Solicitud)
  solicitud: Solicitud;
  solicitudId?: string;

}
