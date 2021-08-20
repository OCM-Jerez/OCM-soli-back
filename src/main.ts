import * as https from 'https';

require('dotenv').config({ path: '.env' });
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { setupSwagger } from './swagger';
import { config } from './config';
import { Logger, ValidationPipe, BadRequestException } from '@nestjs/common';
import express from 'express';
import * as path from 'path';
import * as fs from 'fs';
import * as bodyParser from 'body-parser';
import * as http from 'http';
import { ExpressAdapter } from '@nestjs/platform-express';

const logger: Logger = new Logger('Main');
const port = process.env.NODE_SERVER_PORT || config.get('server.port');

async function bootstrap(): Promise<void> {
  console.log(process.env.KEY_PATH);
  console.log(process.env.CERT_PATH);
  console.log(process.env.IP);
  const httpsOptions = {
    key: fs.readFileSync(process.env.KEY_PATH),
    cert: fs.readFileSync(process.env.CERT_PATH),
  };
  const appOptions = {
    cors: false
  };
  const server = express();
  const app = await NestFactory.create(AppModule, new ExpressAdapter(server),);
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

  // await app.listen(port, process.env.IP);
  await app.init();

  http.createServer(server).listen(80);
  https.createServer(httpsOptions, server).listen(443);
  logger.log(`Application listening on port ${port}`);
}

bootstrap();
