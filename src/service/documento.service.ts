import { Injectable, Logger } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { FindManyOptions, FindOneOptions } from 'typeorm';
import Documento from '../domain/documento.entity';
import { DocumentoRepository } from '../repository/documento.repository';
import { User } from '../domain/user.entity';
import { UserRepository } from '../repository/user.repository';

const relationshipNames = [];
relationshipNames.push('solicitud');
// relationshipNames.push('gestion');

@Injectable()
export class DocumentoService {
  logger = new Logger('DocumentoService');

  constructor(
    @InjectRepository(DocumentoRepository) private documentoRepository: DocumentoRepository,
    private userRepository: UserRepository
  ) { }

  async findById(id: string): Promise<Documento | undefined> {
    const options = { relations: relationshipNames };
    const documento = await this.documentoRepository.findOne(id, options);
    return documento;
  }

  async findByfields(options: FindOneOptions<Documento>): Promise<Documento | undefined> {
    return await this.documentoRepository.findOne(options);
  }

  async findAndCount(options: FindManyOptions<Documento>): Promise<[Documento[], number]> {
    options.relations = relationshipNames;
    return await this.documentoRepository.findAndCount(options);
  }

  async findAll(options: FindManyOptions<Documento>, user: User): Promise<Documento[]> {
    console.log(user);
    options.relations = relationshipNames;
    const result = await this.userRepository.findOne({ where: { id: user.id }, relations: ['authorities'] });
    console.log(result);
    const userResp = this.flatAuthorities(result);
    console.log(userResp);
    console.log(userResp.authorities);
    const documentos = await this.documentoRepository.find(options);

    const docuFiltered = [];
    documentos.forEach(docu => {
      if (docu.privado === false) {
        docuFiltered.push(docu);
      }
      if (userResp.authorities.filter(auth => auth === 'ROLE_ADMIN').length > 0 && docu.privado === true) {
        docuFiltered.push(docu);
      }
    });
    return docuFiltered;
  }

  async findByDocumentoType(options: FindManyOptions<Documento>, user: User): Promise<Documento[]> {
    options.relations = relationshipNames;
    //const result = await this.userRepository.findOne({ where: { id: tipo } });
    const result = await this.userRepository.findOne({ where: { id: user.id }, relations: ['authorities'] });

    const userResp = this.flatAuthorities(result);
    const documentos = await this.documentoRepository.find(options);

    const docuFiltered = [];
    documentos.forEach(docu => {
      if (docu.privado === false) {
        docuFiltered.push(docu);
      }
      if (userResp.authorities.filter(auth => auth === 'ROLE_ADMIN').length > 0 && docu.privado === true) {
        docuFiltered.push(docu);
      }
    });
    return docuFiltered;
  }

  async save(documento: Documento): Promise<Documento | undefined> {
    return await this.documentoRepository.save(documento);
  }

  async update(documento: Documento): Promise<Documento | undefined> {
    return await this.save(documento);
  }

  async delete(documento: Documento): Promise<Documento | undefined> {
    return await this.documentoRepository.remove(documento);
  }

  private flatAuthorities(user: any): User {
    if (user && user.authorities) {
      const authorities: string[] = [];
      user.authorities.forEach(authority => authorities.push(authority.name));
      user.authorities = authorities;
    }
    return user;
  }
}
