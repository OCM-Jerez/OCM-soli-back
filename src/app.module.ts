import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthModule } from './module/auth.module';
import { ormconfig } from './orm.config';
import { SolicitudModule } from './module/solicitud.module';
import { DocumentoModule } from './module/documento.module';
import { GestionModule } from './module/gestion.module';

@Module({
  imports: [
    TypeOrmModule.forRoot(ormconfig),
    AuthModule,
    SolicitudModule,
    GestionModule,
    DocumentoModule,
    SolicitudModule
  ],
  controllers: [
  ],
  providers: [
  ]
})
export class AppModule {}
