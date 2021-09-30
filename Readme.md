# Spring





## 프로젝트 셋팅

### 1. 인텔리 J

1. SDK 설정 : file - project Structure - project 

2. 사용중인 dependency : file - project Structure - Module - Dependency

3. 실행하는 두가지 방법

   1. IDE에서 메인 애플리케이션 실행 : 메인 메소드를 실행, 주로 main - java - org.springframework~에 있음.

      - @SpringBootApplication 어노테이션이 붙어 있고 메인 메소드가 붙어 있는 class가 spring boot에서 메인이 되는 애플리케이션

   2. mvn spring boot:run : Maven - name - Plugins - spring-boot - spring-boot:run

      플러그인  : less 파일들을 컴파일해서 css파일을 작성하고 자바스크립트를 작성





## IOC(Inversion of Control)







IOC 컨테이너 : 컨테이너 내부에 만든 객체(빈)들의 의존성을 관리해준다. 

	- owner는 빈이 아니지만 OnwerController, OwnerRepositroy는 빈이다.
	- 어노테이션으로 빈인지 확인(@controller, @Repository, @component ...)
	- BeanFactory
	- 애플리케이션 컴포넌트의 중앙 저장소
	- **빈 설정 소스로 부터 빈 정의를 읽어 들이고, 빈을 구성하고 제공한다**





## Bean

 #### #스프링 IoC 컨테이너가 관리하는 객체!!#

- 에노테이션 자체에는 기능이 없다. 에노테이션을 마커로 사용해서 에노테이션을 처리하는 프로세서가 있는 것.
- 그 처리하는 것이 컴포넌트스캔.
- 아무 에노테이션을 붙이지 않았다면 기본적으로 싱글톤 스코프.
- 싱글톤 스코프일때는 항상 같은 객체 -> 메모리적으로 장점, 런타임 성능 시간에서 좋다.



- 장점 
  - 의존성 관리를 할 수 있다.
  - 스코프 적용 가능
    - 싱글톤 : 하나의 객체를 사용
    - 프로토타입 : 매번 다른 객체를 사용
  - 라이프사이클 인터페이스
    - 





## 의존성 주입(Dependency Injection)



- 생성자 : 어떤 빈이 되는 클래스에 생성자가 하나만 있고 그 매개변수타입이 빈으로 등록되어있다면 @Autowired가 없더라도 빈을 주입 해준다.(OwnerController 48 line)
- 필드
- Setter



- OwnerRepository는 아무런 에노테이션이 없지만 왜 빈으로?
  - 애플리케이션 컨텍스트에는 수 많은 라이플 사이클, 인터페이스가 있다. 

- 생성자가 하나라면 생성자만 사용
- setter가 있다면 setter에 붙이고 setter가 없다면 필드에 붙여라.





## AOP

