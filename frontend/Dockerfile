FROM node:16.14.0 
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json /app/package.json

RUN npm install -g npm@8.19.2 
RUN npm i react react-dom --legacy-peer-deps
RUN npm install react-scripts@5.0.1 -g npm@8.19.2
RUN npm install axios --legacy-peer-deps
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache

CMD ["npm", "start"]