FROM openjdk:11-jdk
ARG WAR_FILE=./target/springJPA-0.0.1-SNAPSHOT.war
ADD ${WAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]

# docker build -t study .  이미지 생성
# docker run -d -p 8080:8080 study  컨테이너 실행

# 세팅
# docker run -d --name="tomcat-test" -p 8080:8080 tomcat:9.0    // 도커에 톰캣 세팅
# docker cp ~/ROOT.war tomcat-test:/usr/local/tomcat/webapps/   // 컨테이너 webapps 폴더 안에 ROOT.war 등록
# docker exec -it tomcat-test /bin/bash // 으로 확인해보기
# docker cp ./setenv.sh 9058:/usr/local/tomcat/bin/  // 으로 톰캣 기본 설정

# 프로젝트 war 빌드
# 프로젝트 우클릭 -> Export -> War File -> Select Option Export source file / Overwrite existing file -> Finish
# 만든 war 파일을 컨테이너 내의 webapps 파일에 저장