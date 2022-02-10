require('dotenv').config({ path: '.env' });
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { setupSwagger } from './swagger';
import { config } from './config';
import { Logger, ValidationPipe, BadRequestException } from '@nestjs/common';
import * as express from 'express';
import * as path from 'path';
import * as fs from 'fs';
import * as bodyParser from 'body-parser';

const logger: Logger = new Logger('Main');
const port = process.env.NODE_SERVER_PORT || config.get('server.port');

async function bootstrap(): Promise<void> {

  const appOptions = { cors: true };
  const app = await NestFactory.create(AppModule, appOptions);
  app.use(bodyParser.json({ limit: '50mb' }));
  app.use(bodyParser.urlencoded({ limit: '50mb', extended: true }));
  app.useGlobalPipes(
    new ValidationPipe({
      exceptionFactory: (): BadRequestException => new BadRequestException('Validation error')
    })
  );

  const staticClientPath = path.join(__dirname, '../dist/classes/static');
  if (fs.existsSync(staticClientPath)) {
    app.use(express.static(staticClientPath));
    logger.log(`Serving static client resources on ${staticClientPath}`);
  } else {
    logger.log(`No client it has been found`);
  }

  setupSwagger(app);

  await app.listen(port);
  logger.log(`Application listening on port ${port}`);
}

bootstrap();