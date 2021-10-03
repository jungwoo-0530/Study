# [Spring](./Spring/Readme.md)



# Spring Boot

###  

- ## 의존성 관리



- ## 자동 설정
  - #### [Starter, AutoConfigure]()

  - #### [ConfigurationProperties]()

  
  
- ## 내장 웹 서버
  - #### [컨테이너와 포트](./Spring_Boot/AnotherTomcat/Readme.md)

  - #### [HTTPS, HTTP2](./Spring_Boot/HTTPS/Readme.md)



- ### SpringApplication

  - #### [로그레벨설정과 배너](./Spring_Boot/SpringApplication1/Readme.md)

    - https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-spring-application.html#boot-features-spring-application

  - #### [ApplicationEvent, WebApplicationType, ApplicationArguments, ApplicationRunner](./Spring_Boot/SpringApplication2/Readme.md)

    - https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-spring-application.html#boot-features-application-events-and-listeners

  

- ## [외부 설정(property)](./Spring_Boot/properties/Readme.md)



- ## [프로 파일(profile)](./Spring_Boot/profile/Readme.md)



- ## [로깅]()



- ## [테스트]()



- ## [MVC]()



- ## [스프링 데이터]()



- ## [스프링 시큐리티]()



- ## [스프링 REST 클라이언트]()



# Spring Mvc



- ## [Spring Mvc 기본 중 기본](./Spring_Mvc/demospringmvc/Readme.md)



- ### [서블릿, ApplicaitonContext, DispatcherServlet](./Spring_Mvc/Servlet/Readme.md)



# [Web Study](./Web_Basic/Readme.md)



## 프로젝트 셋팅

### 1. 인텔리 J

1. SDK 설정 : file - project Structure - project 

2. 사용중인 dependency : file - project Structure - Module - Dependency

3. 실행하는 두가지 방법

   1. IDE에서 메인 애플리케이션 실행 : 메인 메소드를 실행, 주로 main - java - org.springframework~에 있음.

      - @SpringBootApplication 어노테이션이 붙어 있고 메인 메소드가 붙어 있는 class가 spring boot에서 메인이 되는 애플리케이션

   2. mvn spring boot:run : Maven - name - Plugins - spring-boot - spring-boot:run

      플러그인  : less 파일들을 컴파일해서 css파일을 작성하고 자바스크립트를 작성



