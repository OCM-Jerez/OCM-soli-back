import { EntityRepository, Repository } from 'typeorm';
import Documento from '../domain/documento.entity';

@EntityRepository(Documento)
export class DocumentoRepository extends Repository<Documento> {}
