FROM node:12-alpine

WORKDIR /home/node/app

COPY keys/privkey.pem /etc/letsencrypt/live/ocmapi.org/privkey.pem
COPY keys/fullchain.pem /etc/letsencrypt/live/ocmapi.org/fullchain.pem

COPY package*.json ./

RUN npm install

COPY  . .

RUN chown -R node:node /home/node/app

EXPOSE 8081

ENTRYPOINT ["npm", "run", "start" ]


# FROM node:12-alpine

# # RUN mkdir -p /home/node/app/node_modules && chown -R node:node /home/node/app

# WORKDIR /home/node/app

# COPY package*.json ./

# # USER node

# RUN npm install

# # COPY --chown=node:node . .
# COPY . .

# COPY keys/LightsailDefaultKey-eu-central-1.pem /etc/letsencrypt/live/ocmapi.org/privkey.pem
# COPY keys/LightsailDefaultKey-eu-central-1.pem /etc/letsencrypt/live/ocmapi.org/fullchain.pem

# EXPOSE 8081

# ENTRYPOINT ["npm", "run", "start" ]