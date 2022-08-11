import { Injectable, Logger } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Not } from "typeorm";
import { notDeepStrictEqual } from 'assert';
import { FindManyOptions, FindOneOptions, IsNull } from 'typeorm';
import Solicitud from '../domain/solicitud.entity';
import { SolicitudRepository } from '../repository/solicitud.repository';

const relationshipNames = [];

@Injectable()
export class SolicitudService {
  logger = new Logger('SolicitudService');

  constructor(@InjectRepository(SolicitudRepository) private solicitudRepository: SolicitudRepository) { }

  async findById(id: string): Promise<Solicitud | undefined> {
    const options = { relations: relationshipNames };
    return await this.solicitudRepository.findOne(id, options);
  }

  async findByfields(options: FindOneOptions<Solicitud>): Promise<Solicitud | undefined> {
    return await this.solicitudRepository.findOne(options);
  }

  async findAndCount(options: FindManyOptions<Solicitud>): Promise<[Solicitud[], number]> {
    options.relations = relationshipNames;
    return await this.solicitudRepository.findAndCount(options);
  }

  async findAndCount1(options: FindManyOptions<Solicitud>): Promise<[Solicitud[], number]> {
    options.relations = relationshipNames;
    return await this.solicitudRepository.findAndCount(options);
  }

  async findAndCount2(options: FindManyOptions<Solicitud>): Promise<[Solicitud[], number]> {
    options.relations = relationshipNames;
    return await this.solicitudRepository.findAndCount(options);
  }

  async find(): Promise<Solicitud[]> {
    // this.logger.log(`find`);
    return await this.solicitudRepository.find({
      where: [
        {
          isReclamadaCTA: 1
        },
        // {
        //   email: Not(Like('%@example.com')),
        // },
      ],
    });
  }

  async find1(): Promise<Solicitud[]> {
    return await this.solicitudRepository.find({
      where: [
        {
          isCerrada: IsNull()
        },
        // {
        //   email: Not(Like('%@example.com')),
        // },
      ],
    });
  }

  async find2(): Promise<Solicitud[]> {
    return await this.solicitudRepository.find({
      where: [
        {
          isReclamadaCTA: 1,
          fechaRespuestaAytoCTA: IsNull(),
          isCerrada: IsNull()
        },
        // {
        //   fechaRespuestaAytoCTA: IsNull()
        // },
      ],
    });
  }

  async findAll(): Promise<Solicitud[]> {
    const options = { relations: relationshipNames };
    return await this.solicitudRepository.find(options);
  }

  async save(solicitud: Solicitud): Promise<Solicitud | undefined> {
    return await this.solicitudRepository.save(solicitud);
  }

  async update(solicitud: Solicitud): Promise<Solicitud | undefined> {
    return await this.save(solicitud);
  }

  async delete(solicitud: Solicitud): Promise<Solicitud | undefined> {
    return await this.solicitudRepository.remove(solicitud);
  }
}
