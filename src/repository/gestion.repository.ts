import { EntityRepository, Repository } from 'typeorm';
import Gestion from '../domain/gestion.entity';

@EntityRepository(Gestion)
export class GestionRepository extends Repository<Gestion> {}
