# Spring Session

- `Spring-Session`은 **필터로 동작**하기 때문에 서블릿 컨테이너에서 필터가 어떤 방식으로 동작하는지 알아야함.
  - Spring Boot는 내장된 톰캣을 가지고 있기에 기본적으로 다른 Redis나 다른 서버를 가지고 있지 않다면 기본적으로 톰캣의 세션을 사용함.
    - 기본적인 톰캣의 세션을 사용하면 편리하지만 서버 확장시 큰 문제가 생긴다. 서버마다 동기화가 안되어있기 때문이다. 또한 was의 메모리를 직접 할당해 세션을 관리하므로 공간적 제약도 고려해야 한다.
- **요청이 들어오면 톰캣에서 필터체인(FilterChain)에게 필터들을 순서대로 호출하고 마지막 서블릿(디스패쳐서블릿)을 호출**

- Spring-Session Filter가 가장 먼저 실행되도록하는 것이 매우 중요하다. 



## 기본적인 HttpSession 사용하기

- ```java
  //세션이 있으면 세션 반환, 없으면 신규 세션 생성
  HttpSession session = request.getSession();
  //세션에 로그인 회원 정보 보관
  session.setAttribute("key", "value");
  ```

### 세션 생성과 조회

- `request.getSession(true)`
  - 세션이 있으면 기존 세션 반환
  - 세션이 없으면 새로운 세션을 생성해서 반환
- `request.getSession(false)`
  - 세션이 있으면 기존 세션 반환
  - 세션이 없으면 새로운 세션을 생성하지 않는다. null을 반환

### @SessionAttribute

- 스프링은 세션을 더 편리하게 사용할 수 있도록 지원하는 어노테이션
- 이미 로그인된 사용자를 찾을 때는 아래와 같이 사용하면 된다. 참고로 이 기능은 세션을 생성하지 않는다.
  - `@SessionAttribute(name = "loginId", required = false) Member member`



### 세션 정보

- `sessionId` : 세션 Id, JSESSIONID의 값
- `maxInactiveInterval` : 세션의 유효 시간
- `createTime` : 세션 생성 일시
- `lastAccessedTime` 
  - 세션과 연결된 사용자가 최근에 서버에 접근한 시간
  - 클라이언트에서 서버로 sessionID(JSESSIONID)를 요청한 경우에 갱신된다.
- `isNew` : 새로 생성된 세션인지 아니면 이미 과거에 만들어졌고 클라이언트에서 서버로 sessionId(JSESSIONID)를 요청해서 조회된 세션인지 여부



### 세션 타임 아웃 설정

- 세션은 사용자가 로그아웃을 직접 호출해서 `session.invalidate()`가 호출되는 경우에 삭제된다.
- 그런데 대부분의 사용자는 로그아웃을 선택하지 않고 그냥 웹 브라우저를 종료한다.
- 문제는 HTTP가 비연결성이므로 서버 입장에서는 해당 사용자가 웹 브라우저를 종료한 것인지 아닌지 인식 X
- 따라서 서버에서 세션 데이터를 언제 삭제해야 하는지 판단하기 어렵다.
- 남아있는 세션을 무한정 보관하면 생기는 문제점
  - 세션과 관련된 쿠키(JSESSIONID)를 탈취 당했을 경우 오랜 시간이 지나도 해당 쿠키를 악의적인 요청을 할 수 있따.
  - 세션은 기본적으로 메모리에 생성되므로 필요한 경우에 만들고 삭제해야한다.
- **세션의 종료 시점**
  - 제일 좋은 방법은 사용자가 서버에 최근에 요청한 시간을 기준으로 30분정도 유지해주는 것이다.
  - 이렇게하면 사용자가 서비스를 사용하고 있으면 **세션의 생존시간이 30분으로 계속 늘어난다**.
  - 따라서 30분마다 로그인해야 하는 번거로움이 사라진다.
  - **HttpSession은 이 방식을 사용한다.**

#### 스프링 부트 세션 타임 아웃 설정

- `application.properites`

  - ```yaml
    server.servlet.session.timeout = 1800(초) 
    ```

- 특정 세션 단위로 시간 설정

  - ```java
    session.setMaxInactiveInterval(1800);
    ```

#### 세션 타임 아웃 발생

- 세션의 타임 아웃 시간은 해당 세션과 관련된 JSESSIONID를 전달하는 HTTP 요청이 있으면 현재 시간으로 다시 초기화 된다.

- 이렇게 초기화되면 세션 타임아웃으로 설정한 시간동안 세션을 추가로 사용할 수 있다.

  - ```java
    session.getLastAccessedTime() : 
    ```

    



- `org.springframework.session:spring-session-data-redis` 의존성 추가

  - `SessionRepositoryFilter` 가 의존성 추가만으로 자동으로 추가된다. 프러퍼티 설정을 해야함.

    1. `HttpServletRequest`와 `HttpServletResponse`를 `SessionRepositoryRequestWrapper`와 `SessionRepositoryResponseWrapper`로 만들어줌.
       - 즉 한번 감싸준다.(wrapper)
       - `SessionRepositoryRequestWrapper`로 만드는 이유는 세션관리 api를 오버라이딩해서 동일한 메서드를 호출했을 때, 커스터마이징한 결과를 응답할 수 있게 해준다. 또한 내부적으로  session을 상속한 제네릭스타일인 `SessionRepository`를 갖고있는데 이를 이용해 세션의 CRUD를 관리하게된다.

    2. 또한 하는 역할이 한가지 더있다.

       - 필터이므로 callstack으로 필터가 쌓인다. 즉, 필터가 종료되는 시점은 호출되는 시점의 역순이다.

       - `SessionRepositoryFilter`는 Spring security의 Filter가 처리한 뒤 그 결과를 `SessionRepositoryFilter`가 종료되기 직전에 `commitSession()`을 호출하여 redis 저장소에 저장한다.

       - 여기서 왜 요청을 Wrapper 클래스로 감싼 형태로 다음 Filter로 넘기는 이유를 알 수 있다.
       - 요청이 세션 정보를 얻을 때 마다 `getSession()`이 호출되는데 Wrapper에서 이 `getSession()`을 오버라이딩함으로써 커스터마이징한 세션 저장소에서(redis) 세션정보를 가져올 수 있게끔 하기 위함이다. 즉, **was에서 기본으로 제공하는 세션 관리 방식을 커스터마이징한 것.**



## JSESSIONID vs SESSION

- Spring Session을 사용한다면 톰캣 서버의 기본 JSESSIONID 쿠키는, 스프링에서 임의로 디폴트로 지정해주는 `SESSION`이라는 쿠키로 replace된다.

- 세션 쿠키의 이름을 변경하고 싶다면 `Property`에서 설정할 수 있다.

- ```yaml
  server:
  	servlet:
  		session:
  			cookie:
  				name: JUNGWOOSESSION
  ```

- <img src="img/Redis/Screenshot of Google Chrome (2022-12-20 10-12-48 AM).png" alt="Screenshot of Google Chrome (2022-12-20 10-12-48 AM)" style="zoom:50%;" />

- `server.servlet.session.cookie`의 설정값들

  - ```yaml
    server:
      servlet:
        session:
          timeout: 43200m      # 60m * 24h * 30d
          cookie:
            max-age: 43200m    # 60m * 24h * 30d
            name: SID
            http-only: true
            secure: true
    ```

    



# Redis

## Redis란?





## Spring에서 Redis 다루기

- spring boot에서는 Spring Data Redis를 통해 `Lettuce, Jedis`라는 두 가지 오픈 소스 Java 라이브러리를 사용할 수 있습니다.

- 기본적으로 spring boot는 `Lettuce`를 사용하고 있으므로 별도의 설정없이 사용 가능하다.

- Spring Data Redis는 Redis에 두 가지 접근 방식을 제공합니다.

  1. `RedisTemplate`
  2. `RedisRepsoitory`

- ```java
  @RequiredArgsConstructor
  @Configuration
  @EnableRedisRepositories
  public class RedisRepositoryConfig {
  
      private final RedisProperties redisProperties;
  
      // lettuce
      @Bean
      public RedisConnectionFactory redisConnectionFactory() {
          return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
      }
  
      @Bean
      public RedisTemplate<String, Object> redisTemplate() {
          RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
          redisTemplate.setConnectionFactory(redisConnectionFactory());
          redisTemplate.setKeySerializer(new StringRedisSerializer());
          redisTemplate.setValueSerializer(new StringRedisSerializer());
          return redisTemplate;
      }
  }
  ```

  - `RedisConnectionFactory` 인터페이스를 통해 `LettuceConnectionFactory`를 생성하여 반환합니다.

- ```yaml
  spring:
  	redis:
  		lettuce:
  			pool:
  				max-active: 10
  				max-idle: 10
  				min-idle: 2
  		host: localhost
  		port: 6379
  		
  ```

  - <img src="img/Redis/Screenshot of Typora (2022-12-19 5-18-49 PM).png" alt="Screenshot of Typora (2022-12-19 5-18-49 PM)" style="zoom:50%;" />

### RedisTemplate

- ```java
  String refreshToken = jwtTokenProvider.createRefreshToken();
  
  ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
  valueOperations.set(rRTKey + user.getIdx(), refreshToken);
  log.info("redis RT : {}", valueOperations.get(rRTKey + user.getIdx()));
  ```

  - RedisTemplate을 주입받아서 사용한다
  - 위 코드는 유저가 로그인하면 refreshToken을 cache 서버인 Redis에 저장할 용도로 사용.
  - <img src="img/Redis/Screenshot of iTerm2 (2022-12-19 5-56-12 PM).png" alt="Screenshot of iTerm2 (2022-12-19 5-56-12 PM)" style="zoom:50%;" />



### RedisRepository를 사용한 객체 저장방법

- ```java
  @Getter
  @Setter
  @RedisHash("member")
  public class Member {
  
      @Id
      private String id;
      private String name;
      private int age;
  
      public Member(String name, int age) {
          this.name = name;
          this.age = age;
      }
  }
  ```

  - `@RedisHash()`어노테이션을 통해 설정한 값을 Redis의 key 값 prefix로 사용합니다.
  - `@Id`는 JPA와 동일한 역할을 수행합니다. `member:{id}`의 위치에 자동으로 generate값이 들어갑니다.

- ```java
  public interface MemberRedisRepository extends CrudRepository<Member, String> {
  }
  ```

  - CrudRepository를 extend한 Repository를 생성합니다.

- ```java
  @RequiredArgsConstructor
  @Service
  public class MemberService {
  
      private final MemberRedisRepository memberRedisRepository;
  
      public void addMember() {
          Member member = new Member("jan", 99);
          memberRedisRepository.save(member);
      }
  }
  ```

  - repsoitroy의 save메서드를 사용하면 `hmset, hset`이 호출되어 해당 객체가 저장됩니다.

- <img src="img/Redis/Screenshot of Typora (2022-12-19 5-25-25 PM).png" alt="Screenshot of Typora (2022-12-19 5-25-25 PM)" style="zoom:50%;" />

  - 저장된 값을 확인할 수 있다.



## Spring에서 Redis를 이용한 Session 사용법

### 1. 의존성 추가

```xml
// pom.xml 
<dependency> 
  <groupId>org.springframework.session</groupId> 
  <artifactId>spring-session-data-redis</artifactId> 
</dependency> 

// build.gradle 
implementation 'org.springframework.session:spring-session-core' 
implementation 'org.springframework.session:spring-session-data-redis'
```

### 2. Redis 설정

- ```yaml
  spring:
  	session:
  		store-type : redis
  ```

  - 이 설정은 **Filter를 구현한 springSessionRepositoryFilter이라는 Bean을 생성한다.**
  - 이 필터는 **Spring Session에 의해 생성되는 HttpSession의 구현체를 대신하는 역할을 한다.**

- ```yaml
  spring:
    redis:
      lettuce:
        pool:
          min-idle: 2
          max-active: 10
          max-idle: 10
      host: localhost
      port: 6379
  	session:
  		redis:
  			flush-mode: on_save //1), @EnableRedisHttpSession에서 설정해야함.
  			namespace: spring:session //2)
  server:
  	servlet:
  		session: 
  			timeout: 30 // 3)
  ```

  - (1) `flush_mode`
    - flush_mode는 on_save와 immediate 두 가지 모드가 존재.
    - on_save
      - 호출 될 때만 Redis에 기록된다.
      - 즉, **SessionRepository.save가 호출될 때 Session Store에 저장된다., Web환경에서는 HTTP response할 때 기록된다.**
    - immediate
      - 즉시 Redis에 기록된다.
      - 즉, 가능한 빨리 Session Store에 기록된다. **HTTP Response 전 createSession이나 setAttribute가 사용될 때 기록된다.**
  - (2) `namespace`
    - redis key값 앞에 붙여지는 prefix, default값은 `sprint:session`
  - (3) `timeout` : 이 설정은 임베디드 톰캣을 세션으로 사용할 때 적용되는 설정이다.
    - server내 session timeout를 설정한다. default는 초 단위이며 뒤에 suffix 단위를 붙여 따로 단위를 설정할 수 있다.
  - 위의 설정을 한다고 해도 redis 세션에 적용이 되지 않는다.
    - `@EnableRedisHttpSession(redisNamespace = "${spring.redis.namespace}"` 이런식으로 적용해야 한다.

- ```yaml
  spring:
  	redis:
  		host: localhost
  		port:
  		pasword
  ```

  - <img src="img/Redis/Screenshot of Safari (2022-12-19 3-51-47 PM).png" alt="Screenshot of Safari (2022-12-19 3-51-47 PM)" style="zoom:50%;" />

- ```java
  @EnableRedisHttpSession //추가.
  @SpringBootApplication
  public class JustApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(JustApplication.class, args);
      }
  
  }
  ```

- `Configure`

  - ```java
    @RequiredArgsConstructor
    @Configuration
    @EnableRedisRepositories
    public class RedisConfig {
    
      private final RedisProperties redisProperties;
    
      @Bean
      public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
      }
    
      @Bean
      public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
      }
    }
    ```

- ```java
  @GetMapping("/TestRedisSession")
  public String testRedisSession(HttpSession session) {
  	
    session.setAttribute("role", "admin");//key: 권한, value: 유저의 권한  
    return "session";
  }
  
  //또는
  @GetMapping("/TestRedisSession")
  public String testRedisSession(HttpServletRequest request) {
  	
    request.getSession.setAttribute("role", "admin");//key: 권한, value: 유저의 권한  
    return "session";
  }
  
  //
  
  @PostMapping("/login")
  public ResponseEntity<? extends BasicResponse> login(@Validated @RequestBody loginRequest loginReq, HttpSession session) {
  
    Member member = memberService.findByLoginId(loginReq.getLoginId());
  
    memberService.loginPasswordMatches(loginReq.getPassword(), member.getPassword());
  
    String token = jwtAuthenticationProvider.createToken(member.getLoginId());
    Date date = jwtAuthenticationProvider.getTokenExpired(token);
  
    session.setAttribute("role", member.getRole; // 멤버의 권한 세션에 저장.
  
    return ResponseEntity.ok().body(new CommonResponse<>(TokenResponse.builder().
                                                         accessToken(token).
                                                         tokenExpired(date).
                                                         tokenType("Bearer").build()));
  }
  ```

- <img src="img/Redis/Screenshot of iTerm2 (2022-12-19 6-31-00 PM).png" alt="Screenshot of iTerm2 (2022-12-19 6-31-00 PM)" style="zoom:50%;" />

  - type <key>를 명령하면 해당 key의 value의 타입이 나온다.
  - yml파일에서 prefix한 `spring:session`이 앞에 붙은것을 알 수 있다.
  - `expirations`  : set
    - `expire time`에 삭제될 세션 정보들을 담고있습니다. 해당 time이 되면 저장된 데이터를 조회하여 해당 세션을 모두 삭제합니다.
    - 기본적으로 이 값은 
  - `sessions:expires` : string
    - 해당 세션의 만료 키로 사용합니다.
  - `sessions` : hash, 세션 정보가 저장됨.
    - 세션의 생성 시간, 마지막 세션 조회 시간, 최대 타임아웃 허용 시간과 해당 세션에 저장한 데이터를 저장합니다.
    - **이것을 통해 사용자가 언제 접속했는지, 언제 마지막으로 세션이 끝났는지, 어떤 데이터를 저장했는지 확인가능하다.** 

- <img src="img/Redis/Screenshot of iTerm2 (2022-12-19 6-33-16 PM).png" alt="Screenshot of iTerm2 (2022-12-19 6-33-16 PM)" style="zoom:50%;" />

  - 값
    - `lastAccessedTime` : 마지막 세션 조회 시간
    - `sessionAttr` : 세션에 저장한 데이터
    - `createTime` : 세션 생성 시간
    - `maxInactiveInterval` : 만료시간
  - hash타입이므로 `hkeys <key>`로 조회할 수 있다.

- <img src="img/Redis/Screenshot of iTerm2 (2022-12-19 6-42-00 PM).png" alt="Screenshot of iTerm2 (2022-12-19 6-42-00 PM)" style="zoom:50%;" />

  - admin이 잘 들어가 있는 것을 확인할 수 있다.

- ```java
  @PostMapping("/logout")
  public ResponseEntity<? extends BasicResponse> logout(HttpSession session) {
  
    session.invalidate();
    log.info("로그아웃 성공");
  
    return ResponseEntity.ok().body(new CommonResponse<>("로그아웃에 성공했습니다."));
  }
  ```

  - `session.invalidate()`실행시 
  - <img src="img/Redis/Screenshot of iTerm2 (2022-12-19 9-38-04 PM).png" alt="Screenshot of iTerm2 (2022-12-19 9-38-04 PM)" style="zoom:50%;" />
    - 위와 같이 2개가 redis는 삭제되지 않고 아직 남아있다.
      - 즉, `session:expires`만  삭제되었다. 
      - 처음부터 데이터가 들어갈 때, 이미 5분(300초)가 +되서 `expirations, sessions`가 들어간다.
      - 만약 500초가 남은 상태에서 만약 `session.invalidate()`를 해도 다시 5분(300초)으로 설정돼서 대기한다.
      - 그 이유는 세션이 만료될 때, 세션에 어떤 값을 사용하기 위해 요청과 접근이 올 수 있는 상황이 올 수 있으므로 5분이란 시간을 자체적으로 더한 것이다.



### 기본적인 플로우

- 사용자가 로그인
  - 이 때 컨트롤러에서  `HttpSession`을 파라미터로 받아온다.
  -   `session.setAttribute("Key", "Value")` 실행하여 redis에 저장하고 싶은 정보를 저장함. 
    - 예로 권한을 저장.
  - 응답에 자동으로 헤더에 SESSIONID값을 추가해서 전송.
- 사용자가 세션의 정보가 필요한(권한같은 것들) 요청을 헤더에 SESSIONID값을 추가해서 보냄. 
  - 필터에서 하는 것이 좋다.
  - `request.getSession()`으로 `HttpSession`객체를 가져와서
  - `session.getAttribute("key")`을 통해서 내가 저장한 값을 redis에서 가져옴.
  - 인증, 인가.



### 4. 동작 원리





#### @EnableRedisHttpSession

- 해당 어노테이션은 `RedisConnectionFactory`를 필수적으로 설정해야한다.

  - `LettuceConnectionFactory`를 빈으로 설정해야함.

  - ```java
     @Configuration(proxyBeanMethods = false)
     @EnableRedisHttpSession
     public class RedisHttpSessionConfig {
    
         @Bean
         public LettuceConnectionFactory redisConnectionFactory() {
             return new LettuceConnectionFactory();
         }
    
     }
    ```

- 해당 어노테이션을 사용시 다음과 같은 빈이 등록됨.
  - `SessionRepositoryFilter`
    - `springSessionRepositoryFilter` 이름으로 빈이 등록됨
  - 



## Spring에서 사용 용도

### 1. 회원 권한 인증

- 기본 로직

  - 회원이 로그인 후, 권한 체크가 필요한 요청을 한다.

  - Spring에서 회원 요청에서 매번 DB에 쿼리를 날려 체크하여 권한을 체크한다.
  - 체크 후, 응답.

- Redis 도입 로직

  - 회원이 로그인 후, Redis 세션 스토리지에 회원 정보를 저장한다.
  - 회원이 권한 체크가 필요한 요청을 한다.
  - Spring에서 회원 요청이 들어 올 때, Redis 세션 스토리지에서 회원의 권한을 체크한다.
  - 체크 후, 응답.

- 즉, DB에 쿼리를 매번 안 날리고 빠르게 캐시하여 회원 정보(권한)를 알 수 있다.







-------------------

- 스프링에서 제공하는 spring-session

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 1-59-44 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 1-59-44 PM)" style="zoom:50%;" />

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 2-00-43 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 2-00-43 PM)" style="zoom:50%;" />

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 2-00-59 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 2-00-59 PM)" style="zoom:50%;" />

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 2-01-58 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 2-01-58 PM)" style="zoom:50%;" />



- ```gradle
  implementation 'org.springframework.session:spring-session-data-redis'
  ```

  - 위 의존성을 사용하면 `@EnableRedisHttpSession`을 사용할 수 있음.

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 1-58-55 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 1-58-55 PM)" style="zoom:50%;" />

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 1-59-11 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 1-59-11 PM)" style="zoom:50%;" />

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 2-02-39 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 2-02-39 PM)" style="zoom:50%;" />



<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 2-05-38 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 2-05-38 PM)" style="zoom:50%;" />

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 2-04-53 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 2-04-53 PM)" style="zoom:50%;" />

<img src="img/Redis/Screenshot of IntelliJ IDEA (2022-12-20 2-09-52 PM).png" alt="Screenshot of IntelliJ IDEA (2022-12-20 2-09-52 PM)" style="zoom:50%;" />

-  `@EnableRedisHttpSession`을 사용하면 **@Import(RedisHttpSessionConfiguration.class)**가 추가된다.
- `RedisHttpSessionConfiguration`는 `SpringHttpSessionConfiguration`을 extends한 것이다.
  - `SpringHttpSessionConfiguration`에는 `SessionRepositoryFilter`를 반환하는 `springSessionRepsoitoryFilter` 빈을 등록한다.
  - 이것으로 `@EnableRedisHttpSession` 어노테이션을 사용하고 `RedisConnectionFactory`만 제공한다면 Redis 세션의 기능을 사용할 수 있다.
    - `RedisConnectionFactory`의 예로 `Lettuce`가 존재.
  - 또한 `RedisHttpSessionConfiguration`에서는 `SessionRepository`로 `RedisIndexedSessionRepository`를 반환한다.
    - <img src="img/Redis/Screenshot of Typora (2022-12-20 2-45-29 PM).png" alt="Screenshot of Typora (2022-12-20 2-45-29 PM)" style="zoom:50%;" />

- 즉 `@EnableRedisHttpSession`을 사용하면 `SessionRepositoryFilter`를 반환하는 `springSessionRepsoitoryFilter` 빈을 사용할 수 있다.

  - `SessionRepositoryFilter`의 기능

    1. `HttpServletRequest`와 `HttpServletResponse`를 `SessionRepositoryRequestWrapper`와 `SessionRepositoryResponseWrapper`로 만들어줌.

       - 즉 한번 감싸준다.(wrapper)
       - `SessionRepositoryRequestWrapper`로 만드는 이유는 세션관리 api를 오버라이딩해서 동일한 메서드를 호출했을 때, 커스터마이징한 결과를 응답할 수 있게 해준다. 또한 내부적으로  session을 상속한 제네릭스타일인 `SessionRepository`를 갖고있는데 이를 이용해 세션의 CRUD를 관리하게된다.

    2. 또한 하는 역할이 한가지 더있다.

       - 필터이므로 callstack으로 필터가 쌓인다. 즉, 필터가 종료되는 시점은 호출되는 시점의 역순이다.

       - `SessionRepositoryFilter`는 Spring security의 Filter가 처리한 뒤 그 결과를 `SessionRepositoryFilter`가 종료되기 직전에 `commitSession()`을 호출하여 redis 저장소에 저장한다.

       - 여기서 왜 요청을 Wrapper 클래스로 감싼 형태로 다음 Filter로 넘기는 이유를 알 수 있다.
       - 요청이 세션 정보를 얻을 때 마다 `getSession()`이 호출되는데 Wrapper에서 이 `getSession()`을 오버라이딩함으로써 커스터마이징한 세션 저장소에서(redis) 세션정보를 가져올 수 있게끔 하기 위함이다. 즉, **was에서 기본으로 제공하는 세션 관리 방식을 커스터마이징한 것.**

    



### 참고

- `@EnableRedisHttpSession` AutoConfiguration을 사용하면 `RedisIndexedSessionRepository`를 생성하여 생성한다.
- 

































