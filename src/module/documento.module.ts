import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DocumentoController } from '../web/rest/documento.controller';
import { DocumentoRepository } from '../repository/documento.repository';
import { DocumentoService } from '../service/documento.service';
import { SolicitudModule } from './solicitud.module';
import { UserModule } from './user.module';
import { UserRepository } from '../repository/user.repository';

@Module({
  imports: [UserModule, SolicitudModule, TypeOrmModule.forFeature([DocumentoRepository, UserRepository])],
  controllers: [DocumentoController],
  providers: [DocumentoService],
  exports: [DocumentoService]
})
export class DocumentoModule {}
