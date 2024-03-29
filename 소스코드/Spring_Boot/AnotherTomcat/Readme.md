# 내장 웹 서버





### 다른 서블릿 컨테이너 사용하기



<img src="./img/image-20211001023628586.png" alt="image-20211001023628586" style="width:50%;" />



위 스크린샷 처럼 spring-boot-starter-web이 dependency가 자동적으로 추의적으로 가져오는 tomcat dependency가 있다.

다른 서블릿 컨테이너로 사용하기 위해서는 저 추의적으로 가져오는 tomcat dependency를 예외시켜야 한다. 그 방법은 아래와 같다.



```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-start-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```



이제 jetty로 바꿔보자 간단하게 의존성을 추가해주면 된다.



```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```



<img src="./img/image-20211001024834101.png" alt="image-20211001024834101" style="width:33%;" />



그 결과 jetty 의존성이 추가되고 tomcat 의존성이 사라진 것을 알 수 있다.



jetty에서 undertow로 변경

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-undertow</artifactId>
        </dependency>
```

<img src="./img/image-20211001025207021.png" alt="image-20211001025207021" style="width:33%;" />



### 웹서버 사용하지 않기

- 기본적으로 의존성에 웹 관련된 기술이 들어가 있으면 웹 어플리케이션으로 만들려고 한다.



1. property로 웹서버 사용하지 않기

```application.properties
spring.main.web-application-type=none
```



### 포트번호 바꾸기

1. property에서 변경 가능하다

```application.properties
server.port=7070
```



2. 랜덤 port 번호

```application.properties
server.port=0
```



웹서버가 초기화(생성)되면 이 이벤트 리스너가 호출이 된다.

여기서 포트번호를 알아 내는 방법.

```java
package boot.jungwoo.tomcat;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PortListener implements ApplicationListener<ServletWebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent servletWebServerInitializedEvent) {
        //이벤트에서 웹어플리케이션 컨텍스트를 꺼낸다.
        ServletWebServerApplicationContext applicationContext = servletWebServerInitializedEvent.getApplicationContext();
        System.out.println(applicationContext.getWebServer().getPort());
    }
}
```



<img src="./img/image-20211001030555283.png" alt="image-20211001030555283" style="width:70%;" />



제대로 찍힌 것을 알 수 있고 undertow가 실행되고 바로 출력이 저 이벤트가 실행 된 것을 알 수 있다.


