# 📎 myStory ( https://mystorynews.net )
> 웹 서버의 기본 소양이 되는 게시판 프로젝트입니다.

<h2>Version</h2>

- V 1.0.0 - 서비스 배포 ( React.js + Node.js )
- V 1.0.1 - 반응형 웹 버그 수정
- V 1.1.0 - Node.js 서버를 Spring FrameWork 로 수정
- V 1.2.0 - Spring FrameWork 를 Spring Boot 서버로 수정
- V 1.3.0 - 사용자 프로필 이미지 추가 , 모임 , 모임 예약 , 채팅 등 신규 기능 구현

<h2>Project Introduce</h2>
백엔드의 기초를 다지기 위한 개인 프로젝트이며 첨부 파일 , 댓글 , 알림 , 포스트 CRUD , 페이지네이션 , 해시 태그 등 자주 보는 기능들을 취합하여 만든 프로젝트입니다. <br/>

그 외 기존 코드에 다양한 라이브러리나 새로운 로직을 병합 및 확장하여 버전관리를 하였고 , 기존 코드를 리팩토링하여 레거시 코드를 깨끗하게 관리하는 능력을 키울 수 있었습니다. <br/>
또한 각 기술이 어떻게 동작하는지 이해하고, 활용하는 목표를 갖고 프로젝트를 진행했습니다. 프로젝트 진행 도중 많은 시행착오가 있었지만, 문제를 해결능력과 구현능력을 키울 수 있었습니다.<br/>

<h2>프로젝트 개요 & 주요 기능</h2>

경쟁을 통한 학습보다 서로 공부한 내용을 공유하거나, 누군가를 가르칠 때 효율이 더 높다는 연구 결과에 따라서 함께 만나서 공부할 수 있게 도와주는 웹 어플리케이션을 개발했습니다.
모임을 개설하고 만남 날짜를 예약해 활동할 수 있으며, 게시글을 통해 배웠던 내용을 공유하거나 소통할 수 있는 웹 어플리케이션입니다.

- 사용자
    - 회원 가입, 로그인, 계정 찾기 ( 비밀번호 초기화 )
    - 계정 찾기, 회원 가입 시 이메일 인증
    - OAuth2.0 소설 계정 로그인 ( Google, Naver, Kakao )
- 소모임
    - 소모임 개설 및 참여
    - 소모임 모임 예약 ( 카카오 API 로 모임 지역 설정 )
    - 소모임 내 실시간 채팅 방
- 커뮤니티
    - 게시글 작성, 수정, 조회, 삭제
    - 댓글 등록, 삭제
    - 해시 태그, 첨부 파일, 검색, 조회수, 추천 기능
- 어드민 페이지
    - 일일 접속자 통계
    - 버그 신고 대응
    - 컨텐츠 신고 대응
    - 사용자 관리 ( 권한 설정, 이용 제한 )

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

<h2>ERD</h2>

![image](https://github.com/5tr1ker/mystory/assets/49367338/9627b77c-db57-4655-91b2-386cb042880c)


<h2>System Configuration Diagram</h2>

![image](https://github.com/5tr1ker/mystory/assets/49367338/fbdca961-a621-419d-8bef-17bb2f8f7f41)


<h2>React</h2>

> React ( SPA ) 구조로 하나의 페이지에 담아 동적으로 화면을 바꿔 사용자에게 출력했습니다.

- Route path를 설정해서 동적으로 페이지를 출력합니다.

- /login : 로그인페이지
- /register : 회원가입페이지
- /findid : 계정 찾기
- /admin : 어드민 페이지
- /admin/login : 어드민 페이지 로그인 창
- /* : 이외 링크는 `<NoticeFrame />` 컴포넌트에 연결합니다.
  - /noticelist : 게시판의 목록을 보여줍니다.
  - /newpost : 새 글을 작성합니다.
  - /modified : 작성된 글을 수정합니다
  - /viewpost : 작성된 글을 조회합니다.
  - /profile : 프로필 화면으로 이동합니다.
  - /meeting : 미팅 리스트 정보
  - /newmeeting : 미팅 개설
  - /modify/meeting/0 : 미팅 수정
  - /meeting : 미팅 자세히 보기
  - /newmeeting/0 : 미팅 예약하기

<h2>Spring Security</h2>

> Security 설정을 추가해 인가된 사용자만 GET을 제외한 모든 API에 접근할 수 있도록 제한합니다. 

프로젝트 구조는 다음과 같습니다.</br>

![image](https://github.com/5tr1ker/mystory/assets/49367338/0e86f87e-1a95-422b-bbc9-5a9ac4070b69)

- CSRF : disable
- password encryption : BCryptPasswordEncoder
- Session Creation Policy : STATELESS
- JwtAuthenticationFilter : before UsernamePasswordAuthenticationFilter.class
</br>

- GET 을 제외한 모든 Methods 는 인증이 필요하며 특수한 경우 ( 로그인 , 회원가입 , 로그아웃 ) 만 인증을 제외합니다. </br>
- 인가가 필요한 API는 .hasRole() .hasAnyRole 으로 접근 지정하고 그 외 모든 USER 가 접근할 수 있는 API는 .permitAll() 로 설정했습니다.</br>

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

- Access Token과 Refresh Token은 브라우저 쿠키에 저장되며 httpOnly , Secure 옵션으로 보안처리 했습니다.

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
- ELB ( Application Load Balance ) 를 활용해 OAuth2.0 와 Socket.io 를 처리하기 위한 경로 기반 포워딩을 했습니다.
  - OAuth2.0 요청으로 오는 /oauth2/authorization/* 과 /login/oauth2/code/* 요청은 직접 백엔드 서버와 연결됩니다.
  - 소켓 서버인 /chat 도 백엔드 서버에 연결됩니다.
  - 그 외 요청은 모두 클라이언트 서버 로 연결됩니다. [상세히](https://velog.io/@tjseocld/AWS-AWS-EC2-ALB-%ED%99%9C%EC%9A%A9)
<h2>1 . 로그인 / 회원가입</h2>

> 구글 , 네이버 계정으로 회원가입을 하거나 , 별도로 가입할 수 있습니다.

![로그인](https://github.com/5tr1ker/mystory/assets/49367338/6e487fc2-79ec-4de2-b17b-e9990ea3e6fa)

- OAuth 2.0 를 통해 회원가입 과정을 생략할 수 있습니다.
- 로그인 후 발급되는 Access Token 과 Refresh Token은 클라이언트에서 다음과 같이 보관합니다. 
  - AccessToken과 RefreshToken은 쿠키에 보관하나 Security , HTTPOnly 옵션을 추가해서 서버와 클라이언트 간 https 통신 및 자바스크립트로 쿠키 접근을 제한합니다. 
  - 로그아웃시 Access Token, Refresh Token 쿠키 삭제합니다.
  - OAuth 2.0 로그인 시 사용자 이메일을 이용해 회원가입 및 로그인을 진행합니다.
- SMTP를 활용하여 회원가입, 비밀번호 탐색 시 가입한 이메일로 인증 코드를 보내 인증합니다.
  
<h2>2 . 메인 화면</h2>

> 전체 게시물을 페이지로 나눠서 출력했습니다.

![게시판 메인](https://github.com/5tr1ker/mystory/assets/49367338/5e2ed2ca-7565-4838-98c6-77baa68506e1)

- 각 게시글에 작성되어 있는 댓글 수를 제목 오른쪽에 출력합니다.

<h2>3 . 게시물 조회</h2>

> 사용자가 작성한 게시글을 조회합니다.

![게시판 상세](https://github.com/5tr1ker/mystory/assets/49367338/9951c41a-5aa8-497d-8d41-011071f18161)

- 하단 textbox를 이용하여 댓글을 작성 및 추가할 수 있고, 본인이 작성한 댓글만 삭제할 수 있습니다. 또한 작성자가 등록한 태그 및 첨부파일을 이용할 수 있습니다.
- 게시글을 삭제했을 경우 Database 에서는 남아있지만 서비스에서는 보여지지 않습니다.

<h2>4 . 게시물 수정 / 작성</h2>

> 태그와 첨부파일을 첨부할 수 있고, 에디터를 이용해 글을 작성 및 수정할 수 있습니다.

![게시판 작성](https://github.com/5tr1ker/mystory/assets/49367338/dbf09a5b-9c01-4421-bf4c-b0b29b471540)

- 수정 시 작성되어 있는 데이터에 값을 이어쓸 수 있습니다.
- 쿼리 스트링을 변조하는 불법적인 침입을 금지했습니다.
- 게시글 수정 시 추가된 첨부 파일은 단순히 S3에 저장되지만 , 삭제된 첨부파일의 Id값을 따로 요청하여 S3 파일을 제거합니다.

<h2>5 . 프로필 조회</h2>

> 통계 함수를 이용해 데이터를 조회하고 , 계정 정보 수정 및 탈퇴를 할 수 있습니다.

![프로필](https://github.com/5tr1ker/mystory/assets/49367338/215bd9ea-5c8e-4d13-8ee6-a8cc5dcde2b4)

- 이미지 사진을 눌러 프로필 이미지를 변경할 수 있습니다.
- 계정 이름이 바뀌었을 경우 보안을 위해 모든 Token을 삭제하며 로그아웃을 합니다.

<h2>6 . 알림창</h2>

> 내가 작성한 게시물에 등록된 댓글을 알림으로 표현하고, 버튼을 누르면 해당 게시물로 이동합니다.

![알림](https://github.com/5tr1ker/mystory/assets/49367338/9eb15c69-22cf-499d-b3fb-ca88a6620f73)

<h2>7 . 모임 리스트 / 내가 속해있는 모임</h2>

> 내가 속해있거나 , 최근에 등록된 모임을 노출합니다.

![모임 리스트](https://github.com/5tr1ker/mystory/assets/49367338/1bb691c8-d3da-4565-a389-549cc8b37d50)

- DB 의 Like 문을 통해 검색어와 최대한 가까운 모임 방을 탐색합니다.

<h2>8 . 모임 상세 보기</h2>

> 리스트에서 볼 수 없었던 모임의 상세 정보를 표기합니다.

![모임 상세 보기](https://github.com/5tr1ker/mystory/assets/49367338/98a0309a-96d5-4fc8-8543-243de64d7549)

- Path Parameter 로 인한 불법적인 침입은 금지됩니다.

<h2>9 . 모임 참여 화면</h2>

> 모임의 참여자와 모임 위치 , 일정을 노출합니다.

![모임 정보](https://github.com/5tr1ker/mystory/assets/49367338/59a736a0-d567-44a4-aad8-39244b30d67b)

<h2>10 . 모임 개설 및 수정</h2>

>  카카오맵을 통해 정확한 위치를 선택할 수 있습니다.

![모임 생성](https://github.com/5tr1ker/mystory/assets/49367338/5c741963-ec82-49bb-b1c8-dc207a474bc6)

- 카카오 Map API 를 활용하여 지도를 구현했습니다.

<h2>11 . 모임 내 예악되어 있는 만남</h2>

> 참여한 인원에 대한 정보와 만남 목적과 만남 시간을 노출합니다.

![모임 예약된 일정](https://github.com/5tr1ker/mystory/assets/49367338/dc48b25d-f503-4ac9-b2b6-a77d36be0697)


<h2>12 . 모임 내 만남 예약</h2>

> 카카오맵을 통해 정확한 위치를 선택할 수 있습니다.

![모임 예약](https://github.com/5tr1ker/mystory/assets/49367338/4a8f64b8-0d45-44a3-b1e3-7749ecba4d90)

- 카카오 Map API 를 활용하여 지도를 구현했습니다.
- 시간과 달력 API 를 통해 정확한 만남 날짜를 지정할 수 있습니다.

<h2>13 . 채팅 방 리스트</h2>

> 내가 속한 모임 방에 대한 채팅 방 리스트가 노출됩니다.

![모임 채팅](https://github.com/5tr1ker/mystory/assets/49367338/013d9397-fddd-473f-bccc-cca8701c8b38)

- 채팅 방은 초기 모임을 개설할 경우에 같이 생성됩니다.

<h2>14 . 채팅</h2>

> 실시간 채팅을 통해 같은 모임에 속한 사람과 함께 채팅할 수 있습니다.

![모임 채팅 상세](https://github.com/5tr1ker/mystory/assets/49367338/1f81f981-9241-4af9-80b2-707e62c44c65)

- Socket을 이용하여 실시간 채팅을 구현했습니다.
- 각 채팅방의 세션은 List<Map<>> 자료구조를 이용하여 관리했습니다.

<h2>15 . 어드민 페이지</h2>

![image](https://github.com/5tr1ker/mystory/assets/49367338/c32b69d7-ab44-4974-9a60-878f08ea0fd6)

- Redis를 활용해 일일 접속자 통계를 구현했습니다.
- 버그 신고 대응
- 컨텐츠 신고 대응
- 사용자 관리 ( 권한 설정, 이용 제한 )
- 해당 페이지는 어드민만 접근할 수 있습니다. ( 삭제된 페이지는 어드민만 볼 수 있습니다. )
