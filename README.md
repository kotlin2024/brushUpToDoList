# ToDoList 복습과제

기존의 CRUD를 복습하는데 초점을 둔 프로젝트입니다.
Spring Framework를 사용했으며 사용한 언어는 Kotlin 입니다.

## 목차

- [설명](#설명)
- [사용 기술](#사용-기술)
- [사용법](#사용법)
- [정책](#POLICY)
- [기능 추가 설명](#기능-추가-설명)
- [미완성 부분](#미완성-부분)

## 설명

이 프로젝트는 ToDoList 애플리케이션을 개발하면서 CRUD(Create, Read, Update, Delete) 기능을 복습하는 것을 목표로 합니다. 사용자는 할 일과 댓글 을 추가하고, 목록을 확인하고, 수정하고, 삭제할 수 있으며 좋아요 기능을 이용할 수 있습니다. 

또한 회원가입 시 이메일 인증을 통해 인증된 사용자만이 해당 기능을 사용할 수 있습니다.

## 사용 기술

- SPRING SECURITY
- POSTGRE SQL
- JPA
- SWAGGER
- REDIS
- SMTP
- AOP

## 사용법

1. 브라우저에서 `http://localhost:8080`으로 접속합니다.
2. ToDo 항목 및 댓글, 회원가입 로그인 기능을 사용할 수 있습니다.
   ![swagger](https://github.com/kotlin2024/brushUpToDoList/blob/brushUp/swagger.png)

**예제 사용법**
---
### post-controller

- **할 일 목록 전체 조회**: GET (/api) 페이지네이션이 적용되어 updatedAt,desc , createdAt,ASC 등으로 정렬 기능을 사용하여 목록 조회
- **할 일 단건 조회**: GET (/api/{postId})  조회하고자 하는 postId를 입력하여 조회
- **할 일 생성**: POST (/api)  title과 description을 입력하여 포스트 생성,
- **할 일 수정**: PUT (/api/{postId}) postId를 입력하고  해당 post의 title과 description 을 수정, 해당 포스트는 본인이 작성한 post만 수정이 가능함
- **할 일 삭제**: DELETE (/api/{postId}) postId를 입력하여 해당 post를 삭제, 해당 포스트는 본인이 작성한 post만 삭제가 가능함
- **할 일 목록 좋아요 기능**: PUT (/api/{postId}/like)  postId를 입력하여 해당 post에 좋아요 표시 가능, a라는 유저가 좋아요를 누르고 다시 좋아요를 누르면 좋아요 횟수가 다시 차감됨, 즉 한명의 유저가 좋아요를 여러번 눌러서 좋아요 횟수를 증가시킬 수 없음

---
### comment-controller

- **할 일 목록 댓글 조회**: GET (/comment/{postId}) postId를 입력하여 해당 post의 댓글에 페이지네이션이 적용되어 updatedAt,desc , createdAt,ASC 등으로 정렬 기능을 사용하여 전체 댓글 조회
- **할 일 목록 댓글 생성**: POST (/comment/{postId}) postId를 입력하여 해당 post에 commentDescription을 입력하여 댓글 생성
- **할 일 목록 댓글 수정**: PUT (/comment/{postId}/{commentId}) postId와 commentId를 입력하여 해당 포스트의 댓글에 commentDescription을 수정, 본인이 작성한 댓글만 수정할 수 있음
- **할 일 목록 댓글 삭제**: DELETE (/comment/{postId}/{commentId}) postId와 commentId를 입력하여 해당 포스트의 댓글 삭제, 본인이 작성한 댓글만 삭제 가능

---
### sign-up-and-login-controller

- **회원 가입**: POST (/authentication/sign-up)  userName, userEmail, userPassword를 입력받아 회원가입, checkingPassword를 통해 본인이 설정한 비밀번호를 다시 한번 입력받도록 함, 인증이 되지않은 상태이기 때문에 verification은 default로 false 되있는 상태
- **유저 별명 중복 체크**: POST (/authentication/check-duplicated-userName) userName을 입력하여 이미 생성된 userName인지 확인
- **이메일 인증 검증**: POST(/authentication/check-verify) 회원가입시 이메일로 발송된 VerificationCode와 emial을 입력하여 이메일 인증, 이메일 인증이 완료되면 verification이 true로 변경
- **로그인**: POST (/authentication/login) userEmail과 userPassword를 입력받아 토큰 생성, DB에 저장된 userEmail과 userPassword가 일치해야하며 verification이 true일때만 로그인이 가능



## POLICY

- 회원 가입시 비밀번호는 이메일과 동일해서는 안되며 최소 4자 이상이어야 한다.
- 회원 가입시 이메일은 @가 포함된 이메일 형식이어야만 한다.
- 회원 가입시 유저 닉네임은 최소 3자 이상이어야만 하며  알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성해야한다.
- 회원 가입시 유저 닉네임이 중복되서는 안된다.
- 로그인을 한 유저가 한 게시글에 좋아요를 여러번 누를수 없고 이미 좋아요를 누른 게시글에 다시 좋아요를 누를경우 본인이 누른 좋아요가 사라진다.
- post생성 시 제목이나 내용에 공백이 있으면 안된다.
- post의 제목은 500자를 넘어서는 안된다.
- post의 내용은 5000자를 넘어서는 안된다.

## 기능 추가 설명
---
### Sign-Up

회원가입시 passwordEncoder.encode를 통해 사용자의 비밀번호가 **암호화** 된 상태로 DB에 저장된다.

로그인시 사용자의 이메일, 닉네임, 역할에 대한 정보가 claims에 담겨져 토큰이 생성된다.

---
### Login

OncePerRequestFilter()를 구현한 JwtAuthenticationFilter를 통해서 클라이언트가 요청을 할때마다 토큰을 파싱하여 사용자의 이메일,닉네임,역할의 정보를 **SecurityContextHolder에 저장**하여 
현재 기능을 사용하는 사용자의 로그인 정보를 확인 할 수 있다.


---
### Redis

POST (/authentication/sign-up) 를 통해 회원가입 진행시  verificationService.saveVerificationCode 를 통해서 인증코드를 **REDIS 인메모리 저장소에 5분동안 저장**한다. 

POST(/authentication/check-verify) 를 통해 인증코드를 입력 받으면  verificationService.getVerificationCode 를 통해 REDIS가 저장하고 있는 해당 이메일의 인증코드와 사용자로부터 입력받은 인증 코드를 
비교하여 일치한다면  user.verification = true 를 실행하여 사용자가 로그인을 할 수 있게 한다.

 POST (/authentication/login) 를 통해 사용자가 로그인을 시도할 때 loginUser.verification = true 일 경우만 로그인이 가능하다.

---
### SMTP

POST (/authentication/sign-up) 를 통해 회원가입 진행시 emailService.sendVerificationCode 를 통해서 인증코드를 해당 이메일로 발송한다.

SMTP는  **Google -Gmail**을 사용하였다.

---
### Validation

회원가입시 이메일 양식과 비밀번호 양식, 유저 이름에 대한 간단한 Validation은 jakarta.validation.constraints를 통해서 @field 어노테이션으로 작성했으며
유저 비밀번호가 이메일과 동일해서는 안된다는 규칙 등의 validation을 적용하기 위해서 별도로  @ValidationPassword라는 커스텀 어노테이션 **AOP**를 만들어 구현하였다.

Post와 관련된 Validation은 @ValidatonPost **AOP**를 만들어 구현하였다.


## 미완성 부분

**${\textsf{\color{red} 테스트 코드 작성 미완료}}$**
