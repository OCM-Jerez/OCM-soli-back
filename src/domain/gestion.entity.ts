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

  @Column({ name: 'documento_content_type', nullable: true })
  documentoContentType: string;

  @Column({ name: 'nombre_de_documento' })
  nombreDeDocumento: string;

  @Column({ type: 'boolean', name: 'privado', nullable: true })
  privado: boolean;

  @ManyToOne(type => Solicitud)
  solicitud: Solicitud;

  solicitudId?: string;

}
