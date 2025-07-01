# minu-service-coffee

## 개요
- 커피 서비스 도메인 비즈니스 로직 및 API 구현

## 주요 기술 스택
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- QueryDSL
- Lombok

## 주요 기능
- 상품, 카테고리, 가격 등 도메인 서비스 및 API
- 인증/인가(Spring Security)
- 예외 처리(GlobalExceptionHandler)
- API 응답 표준화(ApiResponse)

## 의존성
- minu-core-coffee (공통 모듈)
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- querydsl
- h2, mysql 등 DB 드라이버