#FROM node:alpine
# 어떤 base Image를 쓸것인가?

#WORKDIR /app
# 어떤 dir의 application을 복사할 것인가?

#COPY package.json package-lock.json ./
#RUN npm install --save --legacy-peer-deps
# ci를 이용하면 우리 개발한 버전을 그대로 사용한다.
# install의 경우 버전을 올려서 설치할 수 있다.

#CMD ["npm","start"]
# 팁 : Dockerfile에서 레이어시스템이여서 빈번한 파일은 코드 후반부에 작성하자.
# 이미지를 다시 만들때 효율성을 높일 수 있다.

# 이미지 파일 생성 : docker build -f Dockerfile -t fun-docker .
# .은 현재 경로를 지정 -f로 Dockerfile 명시한다. (다른이름으로 적을 수 있다.) -t를 통해 Image이름을 부여할 수 있다.

# 실행 : docker run -d -p 3000:3000 fun-docker
# -d 는 detached를 의미한다. 백그라운드에서 docker가 계속 동작할 수 있어야한다. ( 터미널이 해당 요청을 기다리지 않고 다른 요청을 할 수 있게함 )
# -p 포트 지정 host포트:container포트 , container와 host는 고립되어 있기 때문에 연결해주어야함


FROM nginx
RUN mkdir /app
WORKDIR /app
RUN mkdir ./build
ADD ./build ./build
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]


# docker stop [id] 도커 이미지 중지
# docker rmi [id] 도커 이미지 삭제
# docker logs [id] 도커의 로그 확인
# docker tag 레파지토리:태그 레파지토리2:태그  // 해당 이름으로 새로 복사
# docker images -> 생성된 이미지 조회
# docker ps // container의 id 조회

# docker Push 하는 방법
# 1 . docker login 으로 로그인 진행
# 2 . docker push 레파지토리:태그 // 업로드 