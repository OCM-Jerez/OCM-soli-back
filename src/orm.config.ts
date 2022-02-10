import { TypeOrmModuleOptions } from '@nestjs/typeorm';

const commonConf = {
  SYNCRONIZE: false,
  ENTITIES: [__dirname + '/domain/*.entity{.ts,.js}'],
  MIGRATIONS: [__dirname + '/migrations/**/*{.ts,.js}'],
  CLI: {
    migrationsDir: 'src/migrations'
  },
  MIGRATIONS_RUN: true
};

let ormconfig: TypeOrmModuleOptions = {
  name: 'default',
  type: 'mysql',
  database: 'ocmsoliserver',
  url: process.env.DATABASE_URL,
  logging: true,
  synchronize: commonConf.SYNCRONIZE,
  entities: commonConf.ENTITIES,
  migrations: commonConf.MIGRATIONS,
  cli: commonConf.CLI,
  migrationsRun: commonConf.MIGRATIONS_RUN
};

export { ormconfig };
