# 📎 myStory ( https://mystorynews.com )
> 웹 서버의 기본 소양이 되는 게시판 프로젝트입니다.

![img1](https://user-images.githubusercontent.com/49367338/196038573-aee974a6-edba-4118-9c29-e71a518ce945.png)

<h2>Version</h2>

- V 1.0.0 - 서비스 배포 ( React.js + Node.js )
- V 1.0.1 - 반응형 웹 버그 수정
- V 1.1.0 - Node.js 서버를 Spring FrameWork 로 수정
- V 1.2.0 - Spring FrameWork 를 Spring Boot 서버로 수정

<h2>Project Introduce</h2>
백엔드의 기초를 다지기 위한 개인 프로젝트이며 첨부 파일 , 댓글 , 알림 , 포스트 CRUD , 페이지네이션 , 해시 태그 등 인터넷 게시판의 주요 기능들을 취합하여 만든 프로젝트입니다. <br/>

각 기술이 어떻게 동작하는지 이해하고, 활용하는 목표를 갖고 프로젝트를 진행했습니다. 프로젝트 진행 도중 많은 시행착오가 있었지만, 문제를 해결능력과 구현능력을 키울 수 있었습니다.<br/>

<h3># 주요 기능</h3>
프로젝트 주요 기능은 다음과 같습니다.<br/><br/>

- 게시판 : CRUD 기능, 조회수 , 추천 ( 좋아요 ) , 페이징 및 검색 , 댓글 CRUD
- 사용자 : 회원가입 및 로그인 ( JWT ) , OAuth 2.0 (구글, 네이버 , 카카오) , 회원정보 수정, 회원가입시 중복 검사 , 알림

<h2>Project Structure</h2>

> React ( SPA ) + Spring Boot ( Gradle ) 의 어플리케이션 구조

- React ( SPA )
- Spring Boot
- Spring Security ( Security )
- Gradle
- HTTP API ( Rest API )
- MySQL ( RDBMS )
- JPA Hibernate & QueryDSL ( ORM )
- OAuth 2.0 & JWT
- Docker ( Container )
- AWS EC2 ( Infra )
- AWS S3 ( Storage )
- AWS ELB ( Load Balancer )

<h2>React</h2>

> React ( SPA ) 구조로 하나의 페이지에 담아 동적으로 화면을 바꿔 사용자에게 출력했습니다.

- Route path를 설정해서 동적으로 페이지를 출력합니다.

- /login : 로그인페이지
- /register : 회원가입페이지
- /findid : 계정 찾기
- /* : 이외 링크는 `<NoticeFrame />` 컴포넌트에 연결합니다.
  - /noticelist : 게시판의 목록을 보여줍니다.
  - /newpost : 새 글을 작성합니다.
  - /modified : 작성된 글을 수정합니다
  - /viewpost : 작성된 글을 조회합니다.
  - /profile : 프로필 화면으로 이동합니다.

<h2>Spring Security</h2>

> Security 설정을 추가해 인가된 사용자만 GET을 제외한 모든 API에 접근할 수 있도록 제한합니다. 

프로젝트 구조는 다음과 같습니다.</br>

![image](https://github.com/5tr1ker/mystory/assets/49367338/df7cec92-a205-4bb3-aaee-cf1ab68c0453)

- CSRF : disable
- password encryption : BCryptPasswordEncoder
- Session Creation Policy : STATELESS
- JwtAuthenticationFilter : before UsernamePasswordAuthenticationFilter.class
</br>
- GET 을 제외한 모든 Methods 는 인증이 필요하며 특수한 경우 ( 로그인 , 회원가입 ) 만 인증을 제외합니다. </br>
- 인가가 필요한 API는 .hasRole(UserRole.USER.name()) 으로 접근 지정하고 그 외 모든 USER 가 접근할 수 있는 API는 .permitAll() 로 설정했습니다.</br>

<h2>JPA & QueryDSL</h2>

> 객체 중심 설계와 반복적인 CRUD를 Spring Data JPA 로 최소화해 비즈니스 로직에 집중합니다.

- QueryDSL : 복잡한 JPQL 작성시 발생할 수 있는 문법오류를 컴파일 시점에 빠르게 찾아냅니다.</br>
- Spring Data JPA 를 이용해 반복적인 CRUD를 최소화 하고 , 페이징을 이용해 성능을 높혔습니다.
- QueryDSL 을 이용해 복잡한 JPQL 작성시 발생할 수 있는 문법오류를 최소화 합니다.

JPA & QueryDSL 패키지 구조는 다음과 같습니다.</br>

- domain ( Entity class )</br>
- repository ( Spring Data JPA interface )</br>
- CustomRepositoryImpl ( QueryDSL )</br>

<h2>OAuth2.0 & JWT</h2>

> 구글 & 네이버 & 카카오 소셜 서버를 이용해 불필요한 회원가입을 줄이고 , JWT을 이용해 사용자 인증 정보를 저장합니다.

- Access Token과 Refresh Token은 브라우저 쿠키에 저장되며 httpOnly , Secure 옵션으로 보안처리 했습니다

<h2>Docker</h2>

> 여러 컨테이너를 단일 객체로 안정적으로 확보했습니다.

![도커 다이어그램](https://user-images.githubusercontent.com/49367338/196452012-d1ac40b4-987f-4bb3-8717-33255ce338e9.png)

- 각 컨테이너의 환경을 개발 환경과 동일화합니다.
- volumes 을 이용해 로컬과 서버간의 데이터를 공유합니다.
- depends_on 설정을 이용해 각 컨테이너 끼리 통신할 수 있게 지정했습니다.

<h2>AWS EC2 & ELB </h2>

> 전체 프로젝트 인프라 구성

![image](https://github.com/5tr1ker/mystory/assets/49367338/9c3cacb3-7723-42e4-9a76-ee9105450e1f)

- Route 53 도메인으로 오는 요청을 ELB 에게 전달합니다.
- SSL 인증서를 이용해 https 보안설정을 했습니다.
- ELB ( Application Load Balance ) 를 활용해 OAuth2.0 을 처리하기 위해 경로 기반 포워딩을 했습니다.
  - OAuth2.0 요청으로 오는 /oauth2/authorization/* 과 /login/oauth2/code/* 로 오는 요청은 직접 백엔드 서버와 연결됩니다.
  - 그 외 요청은 모두 클라이언트 서버 로 연결됩니다. [상세히](https://velog.io/@tjseocld/AWS-AWS-EC2-ALB-%ED%99%9C%EC%9A%A9)
<h2>1 . 로그인 / 회원가입</h2>

> 구글 , 네이버 계정으로 회원가입을 하거나 , 별도로 가입할 수 있습니다.

![image](https://user-images.githubusercontent.com/49367338/197676731-9463999d-921d-4f14-8926-702996662002.png)

- Oauth 2.0 를 통해 회원가입 과정을 생략할 수 있습니다.
- 로그인 후 발급되는 Access Token 과 Refresh Token은 클라이언트에서 다음과 같이 보관합니다. 
  - AccessToken과 RefreshToken은 쿠키에 보관하나 Security , HTTPOnly 옵션을 추가해서 서버와 클라이언트 간 https 통신 및 자바스크립트로 쿠키 접근을 제한합니다. 
  - 로그아웃시 Access Token, Refresh Token 쿠키 삭제합니다.
  - OAuth 2.0 로그인 시 사용자 이름을 이용해 회원가입 및 로그인을 진행합니다.
  
<h2>2 . 메인 화면</h2>

> 전체 게시물을 페이지로 나눠서 출력했습니다.

![image](https://user-images.githubusercontent.com/49367338/197678129-d36ca987-ebf2-4803-ab50-3af49db5d28b.png)

- 각 게시글에 작성되어 있는 댓글 수를 제목 오른쪽에 출력합니다.

<h2>3 . 게시물 조회</h2>

> 사용자가 작성한 게시글을 조회합니다. 

![image](https://user-images.githubusercontent.com/49367338/197679483-236fd600-419f-468a-a0af-1af995aa6ae9.png)

- 하단 textbox를 이용하여 댓글을 작성 및 추가할 수 있고, 본인이 작성한 댓글만 삭제할 수 있습니다. 또한 작성자가 등록한 태그 및 첨부파일을 이용할 수 있습니다.

<h2>4 . 게시물 수정 / 작성</h2>

> 태그와 첨부파일을 첨부할 수 있고, 에디터를 이용해 글을 작성 및 수정할 수 있습니다.

![image](https://user-images.githubusercontent.com/49367338/197680769-2354229c-01e5-42f4-935e-f27f3c20df7f.png)

- 수정 시 DB에 있는 Data를 그대로 가져오기 때문에 작성되어 있는 데이터에 값을 이어쓸 수 있습니다.
- 쿼리 스트링을 변조하는 불법적인 침입을 금지했습니다.
- 게시글 수정 시 추가된 첨부 파일은 단순히 S3에 저장되지만 , 삭제된 첨부파일의 Id값을 따로 요청하여 S3 파일을 제거합니다.

<h2>5 . 프로필 조회</h2>

> 통계 함수를 이용해 데이터를 조회하고 , 계정 정보 수정 및 탈퇴를 할 수 있습니다.

![image](https://user-images.githubusercontent.com/49367338/197685138-6754e6d7-bdc7-4a5e-857b-40652d4ba761.png)

- 계정 이름이 바뀌었을 경우 보안을 위해 모든 Token을 삭제하며 로그아웃을 합니다.

<h2>6 . 알림창</h2>

> 내가 작성된 게시물에 등록된 댓글을 알림으로 표현하고, 버튼을 누르면 해당 게시물로 이동합니다.

![image](https://user-images.githubusercontent.com/49367338/197687034-459491f7-7616-463b-af4c-695a7992875b.png)
