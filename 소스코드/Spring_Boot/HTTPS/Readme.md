# HTTPS, HTTP2



#### 1. 용어 정리

1. HTTP?

- HTTP는 **클라이언트와 서버 사이에 이루어지는 요청/응답(request/response) 프로토콜**이다. 예를 들면, 클라이언트인 웹 브라우저가 HTTP를 통하여 서버로부터 웹페이지나 그림 정보를 요청하면, 서버는 이 요청에 응답하여 필요한 정보를 해당 사용자에게 전달하게 된다. 이 정보가 모니터와 같은 출력 장치를 통해 사용자에게 나타나는 것이다.



2. HTTPS

- **HTTPS**(Hypertext Transfer Protocol over Secure Socket Layer, HTTP over TLS, **HTTP over SSL**,**HTTP Secure**는 월드 와이드 웹 통신 프로토콜인 HTTP의 보안이 강화된 버전이다. HTTPS는 통신의 인증과 암호화를 위해 넷스케이프 커뮤니케이션즈 코퍼레이션이 개발했으며, 전자 상거래에서 널리 쓰인다.

  HTTPS는 소켓 통신에서 일반 텍스트를 이용하는 대신에, SSL이나 TLS 프로토콜을 통해 세션 데이터를 암호화한다. 따라서 데이터의 적절한 보호를 보장한다. HTTPS의 기본 TCP/IP 포트는 443이다.

  보호의 수준은 웹 브라우저에서의 구현 정확도와 서버 소프트웨어, 지원하는 암호화 알고리즘에 달려있다.

  HTTPS를 사용하는 웹페이지의 URI은 'http://'대신 'https://'로 시작한다. 

  

3. HTTP2

- **HTTP/2**(Hypertext Transfer Protocol Version 2)는 월드 와이드 웹에서 쓰이는 HTTP 프로토콜의 두 번째 버전이다. SPDY에 기반하고 있으며, 국제 인터넷 표준화 기구(IETF)에서 개발되고 있다. 1997년 RFC 2068로 표준이 된 HTTP 1.1을 개선한 것으로, 2014년 12월 표준안 제안(Proposed Standard)으로 고려되어, 2015년 2월 17일 IESG에서 제안안으로 승인되었다. 2015년 5월, RFC 7540로 공개되었다.



### 2. HTTPS



1. ssl key(인증서, 공인인증서x) 생성

   

<img src="./img/image-20211002040648128.png" alt="image-20211002040648128" style="width:20%;" />

2. 프로젝트에 ssl key 적용

```java
//application.properties
server.ssl.key-store=keystore.p12
server.ssl.key-store-password= 123456
server.ssl.key-store-type= PKCS12
server.ssl.key-alias= tomcat
```

 

3. 프로젝트 실행 후 확인

<img src="./img/image-20211002041115115.png" alt="image-20211002041115115" style="width:40%;" />



https://localhost:8080으로 접속하면 위와 같은 화면이 나온다. 이유는 브라우저가 내가 로컬에서 만든 인증서는 브라우저가 모르는 인증서이기 때문에 이런 화면이 나오는 것이다. 



HTTPS로 설정했기 때문에 http 커넥터가 없기 때문에 http를 사용할 수가 없다. http 커넥터를 설정할 수 있는 방법이 있다.



```java
//HttpsApplication Class

package boot.jungwoo.https;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HttpsApplication {


    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring";
    }

    public static void main(String[] args) {
        SpringApplication.run(HttpsApplication.class, args);
    }

  //////////여기 아래부터 http 커넥터 구현.
    @Bean
    public ServletWebServerFactory servletFactory(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
     }

     private Connector createStandardConnector(){
         Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
         connector.setPort(8080);//8080 port로 http를 연결.
         return connector;
     }
   
}
```



```java
//application.properties
server.ssl.key-store=keystore.p12
server.ssl.key-store-password= 123456
server.ssl.key-store-type= PKCS12
server.ssl.key-alias= tomcat

server.port = 8443
```



**8080 port는 http, 8443 port는 https.**

그 결과 아래와 같이 정상적으로 된다. 브라우저에서는 인증서 문제로 접속 불가.

<img src="./img/image-20211002043143082.png" alt="image-20211002043143082" style="width:40%;" />



### 3. HTTP2(undertow)



 의존성에서 tomcat을 제외시키고 undertow 의존성을 추가한다. tomcat은 추가적인 작업을 해야하기에..



```java
server.ssl.key-store=keystore.p12
server.ssl.key-store-password= 123456
server.ssl.key-store-type= PKCS12
server.ssl.key-alias= tomcat

server.port=8443

server.http2.enabled=true//추가

```



<img src="./img/image-20211002044920689.png" alt="S" style="width:50%;" />

HTTP2로 적용이 된다.



### 4. 톰캣 HTTP2

[spring web](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.webserver)

Spring Boot ships by default with Tomcat 9.0.x which supports `h2c` out of the box and `h2` out of the box when using JDK 9 or later. Alternatively, `h2` can be used on JDK 8 if the `libtcnative` library and its dependencies are installed on the host operating system.

톰캣으로 HTTP2를 사용하기 위해서는 자바 9버전, 톰캣 9버전 이상을 사용해야한다.

Project Structure에서 SDK를 자바 9버전으로 변경 후 모듈에서 의존성탭으로 들어간 후 Module SDK를 자바 9버전으로 변경하면 된다.

<img src="./img/image-20211002051135283.png" alt="image-20211002051135283" style="width:50%;" />

정상적으로 되는 모습.

