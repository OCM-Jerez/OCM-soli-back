import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { GestionController } from '../web/rest/gestion.controller';
import { GestionRepository } from '../repository/gestion.repository';
import { GestionService } from '../service/gestion.service';
import { SolicitudModule } from './solicitud.module';
import { UserModule } from './user.module';
import { UserRepository } from '../repository/user.repository';

@Module({
  imports: [UserModule, TypeOrmModule.forFeature([GestionRepository, UserRepository]), SolicitudModule],
  controllers: [GestionController],
  providers: [GestionService],
  exports: [GestionService]
})
export class GestionModule {}
