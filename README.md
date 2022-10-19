# 📎myStory ( https://mystorynews.com )
> 웹 서버의 기본 소양이 되는 게시판 프로젝트입니다.

![img1](https://user-images.githubusercontent.com/49367338/196038573-aee974a6-edba-4118-9c29-e71a518ce945.png)

<h2>Project Introduce</h2>
백엔드의 기초를 다지기 위해 시작한 개인 프로젝트입니다.<br/>
관련 기술들을 학습하고 활용한 결과물이며, 각 기술이 어떻게 동작하고 어디에 활용되는지 공부할 수 있었던 프로젝트입니다.<br/>
프로젝트 진행 도중 많은 시행착오가 있었지만, 문제를 해결하는 능력과 구현 능력을 키울 수 있었습니다.<br/>

<h3># 주요 기능</h3>
프로젝트 주요 기능은 다음과 같습니다.<br/><br/>

- 게시판 : CRUD 기능, 조회수 , 추천 , 페이징 및 검색 처리
- 사용자 : Security 회원가입 및 로그인 ( JWT ) , OAuth 2.0 (구글, 네이버) , 회원정보 수정, 회원가입시 중복 검사 , 알림
- 댓글 : CRUD 기능

<h2>Project Structure</h2>

> React ( SPA ) + Spring ( API ) 구조로 개발했습니다.

- React ( SPA )
- Spring ( API Server )
- Spring Security ( Security )
- MySQL ( RDBMS )
- JPA Hibernate & QueryDSL ( ORM )
- OAuth 2.0 & JWT ( Login )
- Docker ( Container )
- AWS EC2 ( Infra )

<h2>React</h2>

> React ( SPA ) 구조로 하나의 페이지에 담아 동적으로 화면을 바꿔 사용자에게 출력했습니다.

- Route path를 설정해서 동적으로 페이지를 출력합니다.

- /login : 로그인페이지
- /register : 회원가입페이지
- /findid : 아이디 찾는 페이지
- /* : 이외 링크는 <NoticeFrame /> 컴포넌트에 연결합니다.
  - /noticelist : 게시판의 목록을 보여줍니다.
  - /newpost : 새 글을 작성합니다.
  - /modified : 작성된 글을 수정합니다
  - /viewpost : 작성된 글을 조회합니다.
  - /profile : 프로필 화면으로 이동합니다.

<h2>Spring API Server</h2>

> React와 Spring간의 통신은 JSON 형태로 Response 합니다.

프로젝트 구조는 다음과 같습니다.<br/>
- controller : API를 관리합니다.<br/>
- entity<br/>
  - DTO : DTO 파일을 관리합니다.<br/>
  - freeboard : 게시판의 entity를 관리 합니다.<br/>
  - userdata : 사용자 정보 entity를 관리합니다.<br/>
- preferences : Spring Config 와 Security Config를 관리합니다. <br/>
- repository<br/>
  - custom : JPA , QueryDSL 를 관리합니다.<br/>
- security<br/>
  - oauth  : OAuth2 에 관한 기능을 관리합니다.<br/>
  - repository : Security , JWT , OAuth 의 JPA , QueryDSL 를 관리합니다.<br/>
  - service : Security , JWT , OAuth 로직 호출을 관리합니다.<br/>
  - table : JWT 토큰을 관리하는 Entity 입니다.<br/>
- service : 비즈니스 로직 호출을 관리합니다.<br/>

<h2>Spring Security</h2>

> Security 설정을 추가해 인가된 사용자만 특정 API에 접근할 수 있도록 제한합니다. 

프로젝트 구조는 다음과 같습니다.</br>
- CSRF : disable
- Session Creation Policy : STATELESS
- JwtAuthenticationFilter : UsernamePasswordAuthenticationFilter.class
</br>
- 인가가 필요한 API는 .hasRole("ADMIN") 으로 접근 지정하고 그 외 모든 USER 가 접근할 수 있는 API는 .permitAll() 로 설정했습니다.

<h2>JPA & QueryDSL</h2>

> 객체 중심 설계와 반복적인 CRUD를 Spring Data JPA 로 최소화해 비즈니스 로직에 집중합니다.

- JPA : 반복적인 CRUD 작업을 Spring Data JPA 를 이용해서 DB에서 간단하게 데이터를 조회합니다.
- QueryDSL : 복잡한 JPQL 작성시 발생할 수 있는 문법오류를 컴파일 시점에 빠르게 찾아냅니다.
- JPQL : 단순한 정적 쿼리는 JPQL를 이용해 빠르게 구현했습니다. 
</br>
JPA & QueryDSL 패키지 구조는 다음과 같습니다.</br>

- entity ( Entity 테이블 )</br>
- repository ( Spring Data JPA 인터페이스 )</br>
- repository.custom ( JPQL , QueryDSL )</br>

<h2>OAuth2.0 & JWT</h2>

> 구글 & 네이버 OAuth provider를 이용해 불필요한 회원가입을 줄이고 , JWT Token을 이용해 Authorization 인증 시스템을 구현했습니다.

- OAuth & JWT 구조는 다음과 같습니다.
![OAUTH 다이어그램 이미지](https://user-images.githubusercontent.com/49367338/196445612-29a83b05-44be-4628-a8f3-2223b551d826.png)

- Refresh Token은 클라이언트에 httpOnly , Secure 옵션으로 보안처리 했습니다
- Access Token은 클라이언트 로컬 변수에 저장하여, 외부 접근을 차단했습니다.

<h2>Docker</h2>

> 여러 컨테이너를 단일 객체로 안정적으로 확보했습니다.

![도커 다이어그램](https://user-images.githubusercontent.com/49367338/196452012-d1ac40b4-987f-4bb3-8717-33255ce338e9.png)

- 각 컨테이너의 환경을 개발 환경과 동일화합니다.
- volumes 을 이용해 로컬 파일에 미리 생성해둔 환경 설정 파일을 컨테이너와 공유합니다.
- environment 를 이용해 서버에 환경 설정 변수를 지정했습니다.
- links 설정을 이용해 각 컨테이너 끼리 통신할 수 있게 지정했습니다.

<h2>AWS EC2</h2>

> 전체 프로젝트 인프라 구성

![aws 다이어그램](https://user-images.githubusercontent.com/49367338/196463615-0289247c-9a73-4550-b894-e32d97ed8fec.png)

- AWS에 SSL 인증서를 이용해 https 보안설정을 했습니다.
