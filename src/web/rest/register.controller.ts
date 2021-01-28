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
@ApiBearerAuth()
@ApiUseTags('register-resource')
export class RegisterController {
  logger = new Logger('AccountController');

  constructor(private readonly authService: AuthService, private readonly userService: UserService) {}

  @Post('/register')
  @ApiOperation({ title: 'Register user' })
  @ApiResponse({
    status: 201,
    description: 'Registered user',
    type: User
  })
  async registerAccount(@Req() req: Request, @Body() user: User): Promise<User | any> {
    // return res.sendStatus(201);
    console.log('registerAccount');
    const created = await this.userService.save(user);
    console.log('created');
    console.log(created);
    HeaderUtil.addEntityCreatedHeaders(req.res, 'user', created.id);
    return created;
  }
}
