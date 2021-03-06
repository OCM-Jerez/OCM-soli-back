import { Entity, Column } from 'typeorm';
import { BaseEntity } from './base/base.entity';
// import { validate, Contains, IsInt, Length, IsEmail, IsFQDN, IsDate, Min, Max } from 'class-validator';

@Entity('solicitud')
export default class Solicitud extends BaseEntity {
  @Column({ name: 'descripcion' })
  descripcion: string;

  @Column({ type: 'date', name: 'fechaSolicitud' })
  fechaSolicitud: any;

  @Column({ type: 'date', nullable: true })
  fechaInicio: any;

  @Column({ type: 'date', nullable: true })
  fechaRespuesta: any;

  @Column({ type: 'date', nullable: true})
  fechaReclamacionCTA: any;

  @Column({ type: 'date', nullable: true })
  fechaInicioCTA: any;

  @Column({ type: 'date', nullable: true })
  fechaRespuestaCTA: any;

  @Column({ type: 'date', nullable: true })
  fechaRespuestaAytoCTA: any;

  @Column({ nullable: true })
  observacion: string;

  @Column({ nullable: true })
  observacionCTA: string;

  @Column({ nullable: true })
  isAdmitida: boolean;

  @Column({ nullable: true })
  isReclamadaCTA: boolean;
  
  @Column({ nullable: true })
  isAtendidaCTA: boolean;

  @Column({ nullable: true })
  isCerrada: boolean;

  @Column({ nullable: true })
  calidadRespuesta: number;

  @Column({ nullable: true })
  year: number;

  @Column({ nullable: true })
  code: number;

}
