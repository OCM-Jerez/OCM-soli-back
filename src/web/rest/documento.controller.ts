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
import Documento from '../../domain/documento.entity';
import { DocumentoService } from '../../service/documento.service';
import { PageRequest, Page } from '../../domain/base/pagination.entity';
import { AuthGuard, Roles, RoleType } from '../../security';
import { HeaderUtil } from '../../client/header-util';
import { LoggingInterceptor } from '../../client/interceptors/logging.interceptor';
import { SolicitudService } from '../../service/solicitud.service';
import { UserService } from '../../service/user.service';

@Controller('api/documentos')
@UseGuards(AuthGuard)
@UseInterceptors(LoggingInterceptor)
@ApiBearerAuth()
@ApiUseTags('documentos')
export class DocumentoController {
  logger = new Logger('DocumentoController');

  constructor(
    private readonly documentoService: DocumentoService,
    private readonly solicitudService: SolicitudService,
    private readonly userService: UserService
  ) {
  }

  @Get('/')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Get all documentos' })
  @ApiResponse({
    status: 200,
    description: 'List all records',
    type: Documento
  })
  async getAll(@Req() req: Request): Promise<Documento[]> {
    const pageRequest: PageRequest = new PageRequest(req.query.page, req.query.size, req.query.sort);
    const [results, count] = await this.documentoService.findAndCount({
      skip: +pageRequest.page * pageRequest.size,
      take: +pageRequest.size,
      order: pageRequest.sort.asOrder()
    });
    //TODO! restaurar esto
    // results.forEach(documento => {
      // const blob = documento.documento.toString('hex');
      // var result = '';
      // for (var i = 0; i < blob.length; i = i + 2) {
      //   var decval = parseInt(blob.substr(i, 2), 16);
      //   result = result + String.fromCharCode(decval);
      // }
      // documento.documento = result;
    // });
    HeaderUtil.addPaginationHeaders(req.res, new Page(results, count, pageRequest));
    return results;
  }

  @Get('/solicitud/:idsolicitud')
  @ApiOperation({ title: 'Get all documentos of a solicitud' })
  @Roles(RoleType.USER)
  @ApiResponse({
    status: 200,
    description: 'List all records',
    type: Documento
  })
  async getAllBySolicitud(@Req() req: Request, @Param('idsolicitud') idsolicitud: string): Promise<Documento[]> {
    const solicitud = await this.solicitudService.findById(idsolicitud);
    console.log(solicitud);
    const pageRequest: PageRequest = new PageRequest(req.query.page, req.query.size, req.query.sort);
    const [results, count] = await this.documentoService.findAndCount({
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
  @ApiOperation({ title: 'Get all solicitudes of a user' })
  @ApiResponse({
    status: 200,
    description: 'List all records',
    type: Documento
  })
  async getAllBySolicitudFilteredByUser(@Req() req: Request, @Param('idsolicitud') idsolicitud: string,
                                        @Param('id') id: string): Promise<Documento[]> {
    const user = await this.userService.findById(id);

    const solicitud = await this.solicitudService.findById(idsolicitud);
    console.log(solicitud);
    const pageRequest: PageRequest = new PageRequest(req.query.page, req.query.size, req.query.sort);
    const results = await this.documentoService.findAll({where: { solicitud: solicitud } }, user);
    return results;
  }

  @Get('/:id')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Get a documento by id' })
  @ApiResponse({
    status: 200,
    description: 'The found record',
    type: Documento
  })
  async getOne(@Param('id') id: string): Promise<Documento> {
    return await this.documentoService.findById(id);
  }

  @PostMethod('/')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Create documento' })
  @ApiResponse({
    status: 201,
    description: 'The record has been successfully created.',
    type: Documento
  })
  @ApiResponse({ status: 403, description: 'Forbidden.' })
  async post(@Req() req: Request, @Body() documento: Documento): Promise<Documento> {
    console.log(documento);
    const solicitud = await this.solicitudService.findById(documento.solicitudId);
    documento.solicitud = solicitud;
    const created = await this.documentoService.save(documento);
    HeaderUtil.addEntityCreatedHeaders(req.res, 'Documento', created.id);
    return created;
  }

  @Put('/')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Update documento' })
  @ApiResponse({
    status: 200,
    description: 'The record has been successfully updated.',
    type: Documento
  })
  async put(@Req() req: Request, @Body() documento: Documento): Promise<Documento> {
    HeaderUtil.addEntityCreatedHeaders(req.res, 'Documento', documento.id);
    console.log(documento);
    const solicitud = await this.solicitudService.findById(documento.solicitudId);
    documento.solicitud = solicitud;
    return await this.documentoService.update(documento);
  }

  @Delete('/:id')
  @Roles(RoleType.USER)
  @ApiOperation({ title: 'Delete documento' })
  @ApiResponse({
    status: 204,
    description: 'The record has been successfully deleted.'
  })
  async remove(@Req() req: Request, @Param('id') id: string): Promise<Documento> {
    HeaderUtil.addEntityDeletedHeaders(req.res, 'Documento', id);
    const toDelete = await this.documentoService.findById(id);
    return await this.documentoService.delete(toDelete);
  }
}
