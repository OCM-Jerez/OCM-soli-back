import {
  Body,
  Controller,
  Delete,
  Get,
  Logger,
  Param,
  Post as PostMethod,
  Put,
  UseGuards,
  Req,
  UseInterceptors
} from '@nestjs/common';
import { ApiBearerAuth, ApiUseTags, ApiResponse, ApiOperation } from '@nestjs/swagger';
import { Request } from 'express';
import Gestion from '../../domain/gestion.entity';
import { GestionService } from '../../service/gestion.service';
import { PageRequest, Page } from '../../domain/base/pagination.entity';
import { AuthGuard, Roles, RolesGuard, RoleType } from '../../security';
import { HeaderUtil } from '../../client/header-util';
import { LoggingInterceptor } from '../../client/interceptors/logging.interceptor';
import { SolicitudService } from '../../service/solicitud.service';
import Documento from '../../domain/documento.entity';
import { UserService } from '../../service/user.service';

@Controller('api/gestiones')
@UseGuards(AuthGuard, RolesGuard)
@UseInterceptors(LoggingInterceptor)
@ApiBearerAuth()
@ApiUseTags('gestiones')
export class GestionController {
  logger = new Logger('GestionController');

  constructor(
    private readonly gestionService: GestionService,
    private readonly solicitudService: SolicitudService,
    private readonly userService: UserService
  ) {
  }

  @Get('/')
  @Roles(RoleType.USER)
  @ApiResponse({
    status: 200,
    description: 'List all records',
    type: Gestion
  })
  async getAll(@Req() req: Request): Promise<Gestion[]> {
    const pageRequest: PageRequest = new PageRequest(req.query.page, req.query.size, req.query.sort);
    const [results, count] = await this.gestionService.findAndCount({
      skip: +pageRequest.page * pageRequest.size,
      take: +pageRequest.size,
      order: pageRequest.sort.asOrder()
    });
    results.forEach(gestion => {
      const blob = gestion.documento.toString('hex');
      var result = '';
      for (var i = 0; i < blob.length; i = i + 2) {
        var decval = parseInt(blob.substr(i, 2), 16);
        result = result + String.fromCharCode(decval);
      }
      gestion.documento = result;
    });
    HeaderUtil.addPaginationHeaders(req.res, new Page(results, count, pageRequest));
    return results;
  }

  @Get('/solicitud/:idsolicitud')
  @Roles(RoleType.USER)
  @ApiResponse({
    status: 200,
    description: 'List all records',
    type: Documento
  })
  async getAllBySolicitud(@Req() req: Request, @Param('idsolicitud') idsolicitud: string): Promise<Gestion[]> {
    const solicitud = await this.solicitudService.findById(idsolicitud);
    const pageRequest: PageRequest = new PageRequest(req.query.page, req.query.size, req.query.sort);
    const [results, count] = await this.gestionService.findAndCount({
      skip: +pageRequest.page * pageRequest.size,
      take: +pageRequest.size,
      order: pageRequest.sort.asOrder(),
      where: { solicitud: solicitud }
    });
    HeaderUtil.addPaginationHeaders(req.res, new Page(results, count, pageRequest));
    return results;
  }

  @Get('/solicitud/:idsolicitud/usuario/:id')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Get all gestiones' })
  @ApiResponse({
    status: 200,
    description: 'List all records',
    type: Documento
  })
  async getAllBySolicitudFilteredByUser(@Req() req: Request, @Param('idsolicitud') idsolicitud: string,
                                        @Param('id') id: string): Promise<Gestion[]> {
    const user = await this.userService.findById(id);
    const solicitud = await this.solicitudService.findById(idsolicitud);
    const results = await this.gestionService.findAll({
      where: { solicitud: solicitud }
    }, user);
    return results;
  }

  @Get('/:id')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Get a gestion by id' })
  @ApiResponse({
    status: 200,
    description: 'The found record',
    type: Gestion
  })
  async getOne(@Param('id') id: string): Promise<Gestion> {
    return await this.gestionService.findById(id);
  }

  @PostMethod('/')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Create gestion' })
  @ApiResponse({
    status: 201,
    description: 'The record has been successfully created.',
    type: Gestion
  })
  @ApiResponse({ status: 403, description: 'Forbidden.' })
  async post(@Req() req: Request, @Body() gestion: Gestion): Promise<Gestion> {
    const solicitud = await this.solicitudService.findById(gestion.solicitudId);
    gestion.solicitud = solicitud;
    const created = await this.gestionService.save(gestion);
    HeaderUtil.addEntityCreatedHeaders(req.res, 'Gestion', created.id);
    return created;
  }

  @Put('/')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Update gestion' })
  @ApiResponse({
    status: 200,
    description: 'The record has been successfully updated.',
    type: Gestion
  })
  async put(@Req() req: Request, @Body() gestion: Gestion): Promise<Gestion> {
    HeaderUtil.addEntityCreatedHeaders(req.res, 'Gestion', gestion.id);
    const solicitud = await this.solicitudService.findById(gestion.solicitudId);
    gestion.solicitud = solicitud;
    return await this.gestionService.update(gestion);
  }

  @Delete('/:id')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Delete gestion' })
  @ApiResponse({
    status: 204,
    description: 'The record has been successfully deleted.'
  })
  async remove(@Req() req: Request, @Param('id') id: string): Promise<Gestion> {
    HeaderUtil.addEntityDeletedHeaders(req.res, 'Gestion', id);
    const toDelete = await this.gestionService.findById(id);
    return await this.gestionService.delete(toDelete);
  }
}
