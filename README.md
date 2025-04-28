# Outsourcing Project
## 프로젝트 소개
배달 애플리케이션 아웃소싱 프로젝트<br>
배달의 민족, 쿠팡이츠, 요기요 등의 배달앱에 기반한 프로젝트입니다.


<br>

## 역할 분담
**이윤혁**<br>
메뉴 도메인<br>
필터 구현<br>

**김채진**<br>
유저 도메인<br>

**김하정**<br>
주문 도메인<br>

**박민규**<br>
리뷰 도메인<br>

**윤소현**<br>
가게 도메인<br>

<br>


## ERD
![image](https://github.com/user-attachments/assets/07835046-1d34-40ec-87af-803ac7c6487f)

<br>

## 와이어 프레임
[🔗MockUp URL](https://www.figma.com/proto/Xmwxkm7gUuWpcIYEmv7zqY/delivery-app-project?node-id=0-1&t=RwPegibJGhaCY9fK-1)

<br>

## API 명세서
[🔗API URL](https://www.notion.so/Outsourcing-Project-1e3290bb7298805b9628d849e879ba83)

<br>

## 기능 소개
### 유저 도메인
- 유저 회원가입, 로그인, 회원 조회, 회원정보 수정, 회원 탈퇴
- 회원가입 api를 자동 로그인으로 구현하여, 회원가입 또는 로그인 api를 실행하면 JWT 토큰을 발급 받아 로그인 상태를 유지할 수 있습니다.<br>
### 가게 도메인
- 가게 생성, 가게 조회, 가게 수정, 가게 삭제
- JWT에서 로그인한 유저의 권한(일반 사용자 권한, 가게 운영자 권한)을 추출하여, 가게 운영자 권한을 가진 유저만 가게 생성, 가게 수정, 가게 삭제를 할 수 있습니다.<br>
### 메뉴 도메인
- 메뉴 생성, 메뉴 조회, 메뉴 수정, 메뉴 삭제
- JWT에서 로그인한 유저의 권한(일반 사용자 권한, 가게 운영자 권한)을 추출하여, 가게 운영자 권한을 가진 유저만 메뉴 생성, 메뉴 수정, 메뉴 삭제를 할 수 있습니다.<br>
### 주문 도메인
- 주문 요청, 주문 조회, 주문 상태 변경, 주문 삭제
- JWT에서 로그인한 유저의 권한(일반 사용자 권한, 가게 운영자 권한)을 추출하여, 가게 운영자 권한을 가진 유저는 주문 조회, 주문 수정, 주문 삭제를 할 수 있습니다.
- 일반 사용자 권한을 가진 유저는 주문 요청, 주문 조회, 주문 삭제를 할 수 있습니다.<br>
### 리뷰 도메인
- 리뷰 생성, 리뷰 답글(사장님 권한), 리뷰 조회, 리뷰 삭제
- JWT에서 로그인한 유저의 권한(일반 사용자 권한, 가게 운영자 권한)을 추출하여, 일반 사용자 권한을 가진 유저 중에 주문을 생성한 유저만 리뷰 생성, 리뷰 수정, 리뷰 삭제를 할 수 있습니다.
- 가게 운영자 권한을 가진 유저만 주문 조회, 주문 수정, 주문 삭제를 할 수 있습니다.<br>

<br>

## 기술 스택
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> 

<br>

## 프로젝트 구조
```bas      
📁 OutsourcingProject
            └─ common
                   ├─aop
                   ├─config
                   ├─dto
                   ├─entity
                   ├─enums
                   ├─exception
                   ├─filter
                   └─util
            └─ domain
                   ├─menu
                   │  └─controller, dto, entity, repository, service
                   ├─order
                   │  └─controller, dto, entity, repository, service
                   ├─review
                   │  └─controller, dto, entity, repository, service
                   ├─store
                   │  └─controller, dto, entity, repository, service
                   └─user
                      └─controller, dto, entity, repository, service
```
<br>
<br>
