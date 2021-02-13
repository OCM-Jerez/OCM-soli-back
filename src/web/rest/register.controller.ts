import { Body, Param, Post, Res, UseGuards, Controller, Get, Logger, Req, UseInterceptors } from '@nestjs/common';
import { Response, Request } from 'express';

import { AuthGuard, RolesGuard } from '../../security';
import { User } from '../../domain/user.entity';
import { LoggingInterceptor } from '../../client/interceptors/logging.interceptor';
import { ApiBearerAuth, ApiUseTags, ApiResponse, ApiOperation } from '@nestjs/swagger';
import { AuthService } from '../../service/auth.service';
import { UserService } from '../../service/user.service';
import { HeaderUtil } from '../../client/header-util';

@Controller('api')
@UseInterceptors(LoggingInterceptor)
//@ApiBearerAuth()
@ApiUseTags('user register')
export class RegisterController {
  logger = new Logger('AccountController');

  constructor(private readonly authService: AuthService, private readonly userService: UserService) { }

  @Post('/register')
  @ApiOperation({ title: 'Register user' })
  @ApiResponse({
    status: 201,
    description: 'Registered user',
    type: User
  })

  async registerAccount(@Req() req: Request, @Body() user: User): Promise<boolean> {
    // Buscar usuario en la base de datos por el login para comprobar que no existe
    const loginExist = await this.userService.findByLogin(user.login);

    if (loginExist) {
      console.log(loginExist);
      console.log("El login ya existe");
      return true
    } else {
      console.log(loginExist);
      const created = await this.userService.save(user);
      HeaderUtil.addEntityCreatedHeaders(req.res, 'user', created.id);
      return false;
    }
  }
}
