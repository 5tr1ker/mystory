# 📎myStory ( https://mystorynews.com )
> 웹 서버의 기본 소양이 되는 게시판 프로젝트입니다.

![img1](https://user-images.githubusercontent.com/49367338/196038573-aee974a6-edba-4118-9c29-e71a518ce945.png)

<h2>Project Introduce</h2>
인터넷에서 자주보는 게시판의 주요 기능들을 취합하여 만든 프로젝트입니다. <br/>
주로 첨부 파일 , 댓글 , 알림 , 포스트 CRUD , 페이지네이션 , 해시 태그 등 게시판에서 자주 쓰이는 기능을 갖고 있습니다. <br/>

백엔드의 기초를 다지기 위해 시작한 개인 프로젝트입니다.<br/>
관련 기술들을 학습하고 활용한 결과물이며, 각 기술이 어떻게 동작하고 어디에 활용되는지 공부할 수 있었던 프로젝트입니다.<br/>
프로젝트 진행 도중 많은 시행착오가 있었지만, 문제를 해결하는 능력과 구현 능력을 키울 수 있었습니다.<br/>

<h3># 주요 기능</h3>
프로젝트 주요 기능은 다음과 같습니다.<br/><br/>

- 게시판 : CRUD 기능, 조회수 , 추천 , 페이징 및 검색 처리
- 사용자 : Security 회원가입 및 로그인 ( JWT ) , OAuth 2.0 (구글, 네이버 , 카카오) , 회원정보 수정, 회원가입시 중복 검사 , 알림
- 댓글 : CRUD 기능
- 보안 : SSL 인증서를 이용해 https 적용 , 쿠키 접근 제한 ( Security , Samesite ('none') , AccessToken 과 RefreshToken 를 활용해 API 요청에 대한 권한 확인
- 최적화 : 수정할 수 없는 데이터는 읽기 전용 모드를 이용해 메모리 최적화 , 페이징을 활용해 데이터 조회 속도 개선

<h2>Project Structure</h2>

> React ( SPA ) + Spring ( API ) 구조로 개발했습니다.

- React ( SPA )
- Spring Boot ( Server )
- Spring Security ( Security )
- Gradle
- HTTP API ( Rest API )
- MySQL ( RDBMS )
- JPA Hibernate & QueryDSL ( ORM )
- OAuth 2.0 & JWT ( Login )
- Docker ( Container )
- AWS EC2 ( Infra )
- AWS S3 ( Storage )

<h2>React</h2>

> React ( SPA ) 구조로 하나의 페이지에 담아 동적으로 화면을 바꿔 사용자에게 출력했습니다.

- Route path를 설정해서 동적으로 페이지를 출력합니다.

- /login : 로그인페이지
- /register : 회원가입페이지
- /findid : 아이디 찾는 페이지
- /* : 이외 링크는 `<NoticeFrame />` 컴포넌트에 연결합니다.
  - /noticelist : 게시판의 목록을 보여줍니다.
  - /newpost : 새 글을 작성합니다.
  - /modified : 작성된 글을 수정합니다
  - /viewpost : 작성된 글을 조회합니다.
  - /profile : 프로필 화면으로 이동합니다.

<h2>Spring Security</h2>

> Security 설정을 추가해 인가된 사용자만 특정 API에 접근할 수 있도록 제한합니다. 

프로젝트 구조는 다음과 같습니다.</br>

![image](https://github.com/5tr1ker/mystory/assets/49367338/df7cec92-a205-4bb3-aaee-cf1ab68c0453)

- CSRF : disable
- Session Creation Policy : STATELESS
- JwtAuthenticationFilter : before UsernamePasswordAuthenticationFilter.class
</br>
- GET 을 제외한 모든 Methods 는 인증이 필요하며 특수한 경우 ( 로그인 , 회원가입 ) 만 인증을 제외합니다. </br>
- 인가가 필요한 API는 .hasRole(UserRole.USER.name()) 으로 접근 지정하고 그 외 모든 USER 가 접근할 수 있는 API는 .permitAll() 로 설정했습니다.</br>

<h2>JPA & QueryDSL</h2>

> 객체 중심 설계와 반복적인 CRUD를 Spring Data JPA 로 최소화해 비즈니스 로직에 집중합니다.

- QueryDSL : 복잡한 JPQL 작성시 발생할 수 있는 문법오류를 컴파일 시점에 빠르게 찾아냅니다.</br>

JPA & QueryDSL 패키지 구조는 다음과 같습니다.</br>

- domain ( Entity class )</br>
- repository ( Spring Data JPA interface )</br>
- CustomRepositoryImpl ( QueryDSL )</br>

<h2>OAuth2.0 & JWT</h2>

> 구글 & 네이버 OAuth provider를 이용해 불필요한 회원가입을 줄이고 , JWT Token을 이용해 Authorization 인증 시스템을 구현했습니다.

- OAuth & JWT 구조는 다음과 같습니다.
![image](https://github.com/5tr1ker/mystory/assets/49367338/8efff265-cfb9-499a-b98d-6a3b9ff40ffd)

- Access Token과 Refresh Token은 클라이언트에 httpOnly , Secure 옵션으로 보안처리 했습니다

<h2>Docker</h2>

> 여러 컨테이너를 단일 객체로 안정적으로 확보했습니다.

![도커 다이어그램](https://user-images.githubusercontent.com/49367338/196452012-d1ac40b4-987f-4bb3-8717-33255ce338e9.png)

- 각 컨테이너의 환경을 개발 환경과 동일화합니다.
- volumes 을 이용해 로컬과 서버간의 데이터를 공유합니다.
- depends_on 설정을 이용해 각 컨테이너 끼리 통신할 수 있게 지정했습니다.

<h2>AWS EC2</h2>

> 전체 프로젝트 인프라 구성

![image](https://github.com/5tr1ker/mystory/assets/49367338/9c3cacb3-7723-42e4-9a76-ee9105450e1f)

- AWS에 SSL 인증서를 이용해 https 보안설정을 했습니다.
- ALB ( Application Load Balance ) 를 활용해 OAuth2.0 을 처리하기 위해 경로 기반 포워딩을 했습니다.
<h2>1 . 로그인 / 회원가입</h2>

> 구글 , 네이버 계정으로 회원가입을 하거나 , 별도로 가입할 수 있습니다.

![image](https://user-images.githubusercontent.com/49367338/197676731-9463999d-921d-4f14-8926-702996662002.png)

- Oauth 2.0 를 통해 불필요한 회원가입을 단축시켰습니다.
- 로그인 후 발급되는 Access Token 과 Refresh Token은 클라이언트에서 다음과 같이 보관합니다. 
  - AccessToken과 RefreshToken은 쿠키에 보관하나 Security , HTTPOnly 옵션을 추가해서 서버와 클라이언트 간 https 통신 및 자바스크립트로 쿠키 접근을 제한합니다. 
  - 로그아웃시 Access Token, Refresh Token 쿠키 삭제합니다.
  - OAuth 2.0 로그인 시 사용자 이름을 이용해 회원가입 및 로그인을 진행합니다.
  
<h2>2 . 메인 화면</h2>

> 전체 게시물을 페이지로 나눠서 출력했습니다.

![image](https://user-images.githubusercontent.com/49367338/197678129-d36ca987-ebf2-4803-ab50-3af49db5d28b.png)

- Spring Data JPA 를 이용해 반복적인 CRUD 코드를 최소화 하고 , 페이징을 이용해 성능을 높혔습니다.
- QueryDSL 을 이용해 복잡한 JPQL 작성시 발생할 수 있는 문법오류를 컴파일 시점에 빠르게 찾아냈습니다.

<h2>3 . 게시물 조회</h2>

> 사용자가 작성한 게시글을 조회합니다. 하단에 textbox에 댓글을 추가할 수 있고, 본인이 작성한 댓글만 삭제할 수 있습니다. 또한 작성자가 등록한 태그 및 첨부파일을 이용할 수 있습니다.

![image](https://user-images.githubusercontent.com/49367338/197679483-236fd600-419f-468a-a0af-1af995aa6ae9.png)

- 조회는 데이터가 수정되지 않기 때문에 읽기 전용 모드인 ReadOnly를 이용해 영속성 컨텍스트에 있는 변경감지 스냅샷 관리를 제외해 메모리 최적화를 했습니다.

<h2>4 . 게시물 수정 / 작성</h2>

> 태그와 첨부파일을 첨부할 수 있고, 에디터를 이용해 글을 작성 및 수정할 수 있습니다.

![image](https://user-images.githubusercontent.com/49367338/197680769-2354229c-01e5-42f4-935e-f27f3c20df7f.png)

- 수정 시 DB에 있는 Entity를 그대로 가져오기 때문에 작성되어 있는 데이터에 값을 이어쓸 수 있습니다.
- 쿼리 스트링을 변조하는 불법적인 침입을 금지했습니다.

<h2>5 . 프로필 조회</h2>

> 통계 함수를 이용해 데이터를 조회하고 , 계정 정보 수정 및 탈퇴를 할 수 있습니다.

![image](https://user-images.githubusercontent.com/49367338/197685138-6754e6d7-bdc7-4a5e-857b-40652d4ba761.png)

- 계정이 바뀌었을 경우 보안을 위해 모든 Token을 삭제합니다.

<h2>6 . 알림창</h2>

> 내가 작성된 게시물에 등록된 댓글을 알림으로 표현하고, 버튼을 누르면 해당 게시물로 이동합니다.

![image](https://user-images.githubusercontent.com/49367338/197687034-459491f7-7616-463b-af4c-695a7992875b.png)
