FROM node:18.7

WORKDIR /usr/src/app

COPY ./front/package.json ./
COPY ./front/yarn.lock ./

RUN yarn install

COPY ./front/ ./

RUN yarn build
