

# SOP(Same origin policy)

- 같은 출처에 대한 HTTP요청만을 허락한다는 정책.
- SOP를 사용함으로써 좋은 예
  - 해커가 http://hacker.com 페이지를 생성한다.
  - 어떤 유저가 http://hacker.com에 접속을 하였고
  - 해커가 그 유저의 쿠키를 이용해 http://hacker.com에서 http://mail.google.com으로 HTTP요청을 보내 공격한다.



# CORS(Cross-Origin Resource sharing)

- 보안을 위해서 SOP가 좋지만 클라이이언트-서버 관계에서는 다른 출처로 요청을 보내고 자원을 얻어야 하는 상황이 있다.
- **CORS가 이런 상황에서 다른 출처의 자원을 접근할 수 있는 권한을 부여하는 체제이다.**

### origin

- 특정 페이지에 접근할 때 사용되는 URL의 Schema(프로토콜), host(도메인), 포트를 말한다.
- 만약 이 3가지가 하나라도 다르다면 다른 출처, 다른 origin인 것이다.

### CORS란?

- Cross-Origin Resource Sharing(교차 출처 리소스 공유)
- Cross-Origin : 다른 출처
- 브라우저에서 실행 중인 스크립트에서 시작되는 cross-origin HTTP 요청을 제한하는 브라우저 보안 기능
- 다른 Origin에서 내 리소스(데이터)에 함부로 접근하지 못하게 하기 위해 사용됨.
- **즉, 내가 허용한 origin들만 요청할 수 있도록 하기 위해서 사용.**
- cross-origin 종류
  - 다른 도메인 (ex: `example.com`에서 `test.com`으로)
  - 다른 하위 도메인 (ex: `example.com`에서 `store.example.com`으로)
  - 다른 포트 (ex: `example.com`에서 `example.com:81`로)
  - 다른 프로토콜 (ex: `https://example.com`에서 `http://example.com`으로)

- CORS는 어떤 방법으로 동작하는가?
  - 브라우저가 리소스(어떠한 데이터)를 요청할 때 추가적인 헤더에 정보를 담습니다.
  - 



**#XMLHttpRequest(XHR)**

- XHR 객체는 **서버와 상호작용**하기 위해 사용
- 전체 페이지의 **새로고침없이도** **URL로부터 데이터**를 받아올 수 있음
- `AJAX`프로그래밍에 주로 사용됨
- **XML**를 받아오는 것만이 아니라 **모든 데이터**를 받아올 수 있음.
- https://developer.mozilla.org/ko/docs/Web/API/XMLHttpRequest



### 1. Preflight Reqeust

- **CORS 처리 방식 중 가장 보편적인 방법이다.**
- 본 요청을 보내기 전 preflight에 해당하는 **사전 요청을 미리 보내 서버에 어떤 요청이 전달될 것임을 알리고**, **서버에서 허용한 정책을 확인**하여 **브라우저 스스로 이 요청을 보내는 것이 안전한지 확인**하는 방법.

- 서버에서 어떤 메서드와 어떤 header를 허용하는지 확인하는 과정을 Preflight에서 수행
- **서버의 응답으로 어떤 `origin`과 어떤 `method`를 허용하는지 브라우저에게 알려주는 역할.**
- Preflight에서 보내는 **아래 3가지 헤더를 갖고 OPTIONS 요청**을 보내게 된다.
  - `Access-Control-Request-Method`
    - 실제 요청에서 어떤 메서드를 사용할 것인지 서버에게 알리기 위해

  - `Access-Control-Request-Headers`
    - 실제 요청에서 어떤 Header를 사용할 것인지 서버에게 알리기 위해

  - `Origin`

- 확인하는 과정
  - 특정 HTTP Method로 요청을 **보내기 전**(프론트(브라우저)에서 서버)
  - 해당 서버로 **OPTIONS를 미리 보냄**
  - 서버로 부터 온 응답을 확인 한 후, HTTP Method가 지원하면 실제 요청이 이뤄지게 되는 것

- 그렇다면 매 요청마다 Preflight 요청을 전송하는가?
  - 서버 설정을 통하여 preflight 결과를 캐시를 일정 기간 동안 저장한다.
  - 이 캐시 정보가 살아있는 동안 `cross-origin`요청에 대해서 preflight를 생략
  - `maxAge`

- 예

  - ```HTTP
    //Preflight request
    OPTIONS /members/login/ HTTP/1.1
    host: localhost:8080
    
    access-control-request-method: POST
    access-control-request-headers: content-type
    origin: http://localhost:3000
    ```

  - ```HTTP
    //Preflight response
    
    Access-Control-Allow-Headers: authorization
    Access-Control-Allow-Methods: GET,HEAD,POST //허용한 메서드 
    Access-Control-Allow-Origin: http://localhost:3000 //허용한 출처
    Access-Control-Expose-Headers: Authorization
    Access-Control-Max-Age: 1800 //캐시시간
    ```

    





### 2. Spring boot CORS 허용 방법

Spring boot에서 CORS 허용 방법으로는 대표적으로 두 가지가 존재.

- `WebConfig` 설정

  - ```java
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
    
        /*
            http://localhost:8080 에서 들어오는 모든 요청 CORS 허용
        */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8080")
              			.maxAge(3600);//3600초 동안 preflight결과를 캐시에 저장
        }
    }
    ```

  - `addMapping()`
    
    - CORS를 적용할 
    
  - `allowedMethods()`
    
    - response의 메서드 요청을 설정해 주는 것.
    
  - `allowedOriginPattern()`
    
    - 허용 origin을 정해주는 것. 
    - Response 헤더에 `Access-Control-Allow-Origin: `에 붙임.
    - 





- `@CrossOrigin`

  - Controller 단에서 `@CrossOrigin` 어노테이션을 사용하여 CORS 허용하는 방법

  - ```java
    @RestController
    public class ApiController {
    
        /*
            @CrossOrigin(origins = “허용주소:포트”)
            모든 origin 허용은 @CrossOrigin(origins="*") 설정
        */
            
        // http://localhost:8080 에서 들어오는 요청만 CORS 허용
        @CrossOrigin(origins = "http://localhost:8080", maxAge=3600)
        @PostMapping("/")
        public String postSuccess() {
            return "REST API 호출 성공~!!";
        }
    }
    ```

    

