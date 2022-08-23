import { Injectable, Logger } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { FindManyOptions, FindOneOptions } from 'typeorm';
import Gestion from '../domain/gestion.entity';
import { GestionRepository } from '../repository/gestion.repository';
import { UserRepository } from '../repository/user.repository';
import { User } from '../domain/user.entity';

const relationshipNames = [];
relationshipNames.push('solicitud');

@Injectable()
export class GestionService {
  logger = new Logger('GestionService');

  constructor(
    @InjectRepository(GestionRepository) private gestionRepository: GestionRepository,
    private userRepository: UserRepository
  ) { }

  async findById(id: string): Promise<Gestion | undefined> {
    const options = { relations: relationshipNames };
    const gestion = await this.gestionRepository.findOne(id, options);
    return gestion;
  }

  async findByfields(options: FindOneOptions<Gestion>): Promise<Gestion | undefined> {
    return await this.gestionRepository.findOne(options);
  }

  async findAndCount(options: FindManyOptions<Gestion>): Promise<[Gestion[], number]> {
    options.relations = relationshipNames;
    return await this.gestionRepository.findAndCount(options);
  }

  async save(gestion: Gestion): Promise<Gestion | undefined> {
    return await this.gestionRepository.save(gestion);
  }

  async update(gestion: Gestion): Promise<Gestion | undefined> {
    return await this.save(gestion);
  }

  async delete(gestion: Gestion): Promise<Gestion | undefined> {
    return await this.gestionRepository.remove(gestion);
  }

  async findAllMAM(): Promise<Gestion[]> {
    const options = { relations: relationshipNames };
    return await this.gestionRepository.find(options);
  }

  async findAll(options: FindManyOptions<Gestion>, user: User): Promise<Gestion[]> {
    options.relations = relationshipNames;
    const result = await this.userRepository.findOne({ where: { id: user.id }, relations: ['authorities'] });
    const userResp = this.flatAuthorities(result);
    const gestions = await this.gestionRepository.find(options);
    const docuFiltered = [];
    gestions.forEach(gestion => {
      if (gestion.privado === false) {
        docuFiltered.push(gestion);
      }
      if (userResp.authorities.filter(auth => auth === 'ROLE_ADMIN').length > 0 && gestion.privado === true) {
        docuFiltered.push(gestion);
      }
    });
    return docuFiltered;
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
