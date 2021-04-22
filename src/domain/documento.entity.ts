import { Entity, Column, ManyToOne } from 'typeorm';
import { BaseEntity } from './base/base.entity';

import Solicitud from './solicitud.entity';

@Entity('documento')
export default class Documento extends BaseEntity {
  @Column({ name: 'nombreDocumento' })
  nombreDocumento: string;

  @Column({ type: 'date' })
  fechaSubida: any;

  @Column({ nullable: true })
  documentoType: string;
  
  @Column({ nullable: true })
  observacion: string;

  @Column({ nullable: true })
  ruta: string;

  @Column({ type: 'boolean', nullable: true })
  privado: boolean;

  @ManyToOne(type => Solicitud)
  solicitud: Solicitud;
  solicitudId?: string;

}
