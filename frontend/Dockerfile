FROM node:16.14.0 
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json .

RUN npm install -g npm@9.6.7
RUN npm install --legacy-peer-deps
RUN npm install axios --legacy-peer-deps
RUN npm install react-kakao-maps-sdk --legacy-peer-deps

COPY . .
EXPOSE 3000

CMD ["npm", "start"]