



### 1. 간략한 웹 MVC

```java
//UserController.java
package boot.jungwoo.demospringmvc.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;//웹 mvc 테스트를 만들 떄 주로 사용하는 객체 주입. 이 객체는 @WebMvcTest를 사용하면 자동으로 빈으로 만들어줌.

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

}

```

MockMvc는 웹 mvc 테스트를 만들 떄 주로 사용하는 객체 주입. 이 객체는 @WebMvcTest를 사용하면 자동으로 빈으로 만들어줌.



```java
//UserController.java
package boot.jungwoo.demospringmvc.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//핸들러
public class UserController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}

```

이렇게 아무런 설정파일을 작성하지 않아도 스프링 mvc 개발을 바로 시작할 수 있다. 이건 스프링 부트가 제공하는 기본 설정때문에 가능하다.

![image-20211003023725363](/Users/jungwoo/Library/Application Support/typora-user-images/image-20211003023725363.png)

spring.factories에 있는 WebMvcAutoConfiguration.class에 자동 설정 파일이 있기 때문에 바로 시작할 수 있다.



### 2. 스프링 MVC 확장

- @Configuration + WebMvcConfigurer(interface)

스프링 부트가 제공해주는 것에 추가로 내가 더 설정을 더 하고 싶다면 아래와 같은 설정 파일을 만들어야한다.

```java
//WebConfig
package boot.jungwoo.demospringmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

}

```

WebMvcConfigurer 인터페이스에서 콜백 메서드를 사용해서 커스텀마이징을 한다.