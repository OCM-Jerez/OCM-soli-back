import yaml from 'js-yaml';
import * as fs from 'fs';
import * as path from 'path';
import { Logger } from '@nestjs/common';

const logger = new Logger('Config');

export class Config {
  debugLogging = 'debug';

  'server.port' = '8081';
  'jhipster.clientApp.name' = 'OCMSoliServer';
  'jhipster.registry.password' = 'admin';
  'jhipster.security.authentication.jwt.base64-secret' =
    'NDhjNjk1NTM3NWRlNjc1NDMwZjllNWFiMmVlYjQ4NzViYzY4MmY5ZWY2MzZhMzNiMTYxYmNlYjJkMWYwNDk0NDBlNDYwZThjMmFmNzAyNTQyOWYxMDhkM2QxYTQ3ZDFjM2I5YWU4YWVjOGRhNDc3MWE5OTExMzUyMjI3MDlmZWM=';
  'jhipster.security.authentication.jwt.token-validity-in-seconds' = 86400;
  'jhipster.security.authentication.jwt.token-validity-in-seconds-for-remember-me' = 2592000;
  'jhipster.mail.base-url' = 'http://127.0.0.1:${server.port}';
  'jhipster.mail.from' = 'OCMSoliServer@localhost';
  'jhipster.swagger.default-include-pattern' = '/api/.*';
  'jhipster.swagger.title' = 'OCMSoliServer API';
  'jhipster.swagger.description' = 'OCMSoliServer API documentation';
  'jhipster.swagger.version' = '0.0.1';
  'jhipster.swagger.path' = '/api/v2/api-docs';
  'crypto.key' = '3772c1cdbd27c225735d116d1e4c5421a3aec26c919cc7ab457f21a4d16a1821';
  'crypto.iv' = '54f3ad979d9262d3a2dd4489531daf34';

  constructor(properties) {
    this.addAll(properties);
  }

  public get(key: string): any {
    return this[key];
  }

  public addAll(properties): any {
    properties = objectToArray(properties);
    for (const property in properties) {
      if (properties.hasOwnProperty(property)) {
        this[property] = properties[property];
      }
    }
    this.postProcess();
  }

  public postProcess(): any {
    const variables = { ...this, ...process.env };
    for (const property in this) {
      if (this.hasOwnProperty(property)) {
        const value = this[property];
        const processedValue = this.processTemplate(value, variables);
        this[property] = processedValue;
      }
    }
  }

  private processTemplate(template, variables): any {
    // console.log(template);
    if (typeof template === 'string') {
      return template.replace(new RegExp('\\${[^{]+}', 'g'), name => variables[name.substring(2, name.length - 1)]);
    }
    return template;
  }
}

const yamlConfigPath = path.join(__dirname, 'config', 'application.yml');
const envYamlConfigPath = path.join(__dirname, 'config', `application-${process.env.NODE_ENV}.yml`);

const yamlConfig = yaml.safeLoad(fs.readFileSync(yamlConfigPath, 'utf8'));
logger.log(`Actual process.env.NODE_ENV value: ${process.env.NODE_ENV}`);
logger.log('Standard allowed values are: dev, test or prod');
if (!fs.existsSync(envYamlConfigPath)) {
  logger.error(
    'does not exist under your config folder an application-{process.env.NODE_ENV}.yml file with your process.env.NODE_ENV value'
  );
}
const envYamlConfig = yaml.safeLoad(fs.readFileSync(envYamlConfigPath, 'utf8'));

const config = new Config({ ...objectToArray(yamlConfig), ...objectToArray(envYamlConfig), ipAddress: ipAddress() });

export { config };

function objectToArray(source, currentKey?, target?): any {
  target = target || {};
  for (const property in source) {
    if (source.hasOwnProperty(property)) {
      const newKey = currentKey ? currentKey + '.' + property : property;
      const newVal = source[property];

      if (typeof newVal === 'object') {
        objectToArray(newVal, newKey, target);
      } else {
        target[newKey] = newVal;
      }
    }
  }
  return target;
}

function ipAddress(): any {
  const interfaces = require('os').networkInterfaces();
  for (const dev in interfaces) {
    if (interfaces.hasOwnProperty(dev)) {
      const iface = interfaces[dev];
      for (const alias of iface) {
        if (alias.family === 'IPv4' && alias.address !== '127.0.0.1' && !alias.internal) {
          return alias.address;
        }
      }
    }
  }

  return null;
}
