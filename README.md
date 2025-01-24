# 🍴  SNS 맛집 노트(1인 프로젝트)

### 💻프로젝트 개요
#### 사용자가 관심있는 SNS(인스타그램)의 인플루언서 게시물을 바탕으로 맛집을 탐색할 수 있는 플랫폼으로, 자신만의 맛집을 기록하고 다른 사용자와 공유할 수 있는 웹사이트입니다.

### 🎯서비스 핵심기능
#### 1. 인스타그램 기반 맛집 조회
#### 2. 자신의 맛집, 가고싶은 식당 기록 및 공유

### 🛠기술 스택
OS | Windows 10
--- | --- |
Language | ![Java](https://img.shields.io/badge/JAVA-000?style=for-the-badge&logo=java&logoColor=white)
Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) ![JPA](https://img.shields.io/badge/JPA-326690?style=for-the-badge&logo=hibernate&logoColor=white)
Build Tool | ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
Database | ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) ![Redis](https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
Frontend | ![HTML5](https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black) ![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white) ![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
Server | ![Apache Tomcat 9.0](https://img.shields.io/badge/Apache%20Tomcat%20-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black) 
Library | ![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
API | 
Version Control | ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)

### 🚧시스템 아키텍처

### 📖ERD

### 로그인 구현 과정
Spring Security와 Spring Session을 사용하여 Redis를 기반으로 인증 및 세션 관리를 처리하는 구조
사용자가 로그인 요청을 보내면 Spring Security는 CustomUsernamePasswordAuthenticationFilter를 통해 JSON 기반 요청에서 userId와 userPw를 추출하고 AuthenticationManager에 전달하여 인증을 수행하며, 인증에 성공하면 사용자 정보를 SecurityContext에 저장한 뒤 세션을 통해 Redis에 저장하며, 성공적인 인증 응답으로 JSON 형식의 성공 메시지와 리디렉션 URL을 반환
로그인 이후 다른 페이지에 접근할 경우 SecurityContext가 Redis에 저장된 세션 정보를 기반으로 인증된 사용자 정보를 가져와 권한을 확인한 뒤 요청된 페이지에 접근할 수 있도록 처리