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



- ## [Spring Mvc 기본 중 기본](./SpringMvc/demospringmvc/Readme.md)



- ### [서블릿, ApplicaitonContext, DispatcherServlet](./SpringMvc/Servlet/Readme.md)



- ### [Bean 설정 방법(@EnableWebMvc)](./Bean설정방법.md)

## 1. WebMvcConfigurer



- ### [WebMvcConfigurer, JSP 사용하기, WAR 배포하기](./SpringMvc/demo-jsp/Readme.md)



- ### [Formatter, HandlerInterceptor, resource handler, HTTP message converter, 그 밖에 WebMvcConfig 설정](./SpringMvc/demobootweb/Readme.md)



## 2. Mvc



- ### HTTP 요청 맵핑하기
  - #### [요청 메소드](./SpringMvc/demo-web-mvc/Readme.md)

  - #### [URI 패턴 맵핑](./SpringMvc/demo-web-mvc/UriPattern.md)

  - #### [미디어 타입 맵핑](./SpringMvc/demo-web-mvc/Media.md)

  - #### [헤더와 매개변수](./SpringMvc/demo-web-mvc/HeaderAndParams.md)

  - #### [HEAD와 OPTIONS 요청 처리](./SpringMvc/demo-web-mvc/HeadAndOptions.md)

  - #### [커스텀 애노테이션](./SpringMvc/demo-web-mvc/Custom.md)

  - #### [연습문제](./SpringMvc/practice1-web-mvc/Readme.md)

- ### 핸들러 메소드

  - #### [지원하는 메소드 아규먼트와 리턴 타입](./SpringMvc/Args_return.md)

  - #### [URI 패턴](./SpringMvc/UriPattern.md)

  - #### [@RequestMapping](./SpringMvc/RequestMapping.md)

  - #### [폼 서브밋(타임리프)](./SpringMvc/form_submit.md)

  - #### [@ModelAttribute](./SpringMvc/ModelAttribute.md)

  - #### [@Validated](./SpringMvc/Validated.md)

  - #### [폼 서브밋 (에러 처리)](./SpringMvc/form_submit_error.md)

  - #### [@SessionAttributes](./SpringMvc/SessionAttributes.md)

  - #### [멀티 폼 서브밋](./SpringMvc/multi_form_submit.md)

  - #### [@SessionAttribute](./SpringMvc/SessionAttribute.md) - 세션설정

  - #### [RedirectAttributes](./SpringMvc/RedirectAttributes.md) - 리다이렉트

  - #### [Flash Attribute](./SpringMvc/Flash_Attributes.md) - 리다이렉트

  - #### [MultipartFile](./SpringMvc/MultipartFile.md) - 파일 업로드

  - #### [ResponseEntity](./SpringMvc/ResponseEntity.md) - 파일 다운로드

  - #### [@RequestBody & HttpEntity](./SpringMvc/RequestBody_HttpEntity.md) - 요청 본문(body)에 들어있는 데이터 변환

  - #### [@Response & ResponseEntity](./SpringMvc/ResponseBody_ResponseEntity.md) 
  
  - #### @JsonView
  
  - #### [@ModelAttribute의 또 다른 사용법](./SpringMvc/@ModelAttribute2.md)
  
  - #### [DataBinder - @InitBinder](./SpringMvc/@initBiner.md)
  
  - #### [에외 처리 핸들러 - @ExceptionHandler](./SpringMvc/@ExceptionHandler.md)
  
  - #### [전역 컨트롤러 - @(Rest)ControllerAdvice](./SpringMvc/@ControllerAdvice.md)



# [Web Study](./Web_Basic/Readme.md)

- ### 정규 표현식



# [Error](./Error/Readme.md)





# [PetClinic 분석](./SpringMvc/PetClinic_Controller.md)





## 프로젝트 셋팅

### 1. 인텔리 J

1. SDK 설정 : file - project Structure - project 

2. 사용중인 dependency : file - project Structure - Module - Dependency

3. 실행하는 두가지 방법

   1. IDE에서 메인 애플리케이션 실행 : 메인 메소드를 실행, 주로 main - java - org.springframework~에 있음.

      - @SpringBootApplication 어노테이션이 붙어 있고 메인 메소드가 붙어 있는 class가 spring boot에서 메인이 되는 애플리케이션

   2. mvn spring boot:run : Maven - name - Plugins - spring-boot - spring-boot:run

      플러그인  : less 파일들을 컴파일해서 css파일을 작성하고 자바스크립트를 작성



