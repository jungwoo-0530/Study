# 프로파일

스프링 프레임워크에서 제공해주는 기능으로 어떤 특정한 프로파일에서만 어떤 특정한 빈을 등록하고 싶다거나 애플리케이션 동작을 어떤 프로파일일 때 빈 설정을 다르게 하고 싶을 때 사용한다.



#### 1.@Profile 애노테이션은 어디에? 

- @Configuration
- @Component

```java
//BaseConfiguration.java
package boot.jungwoo.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class BaseConfiguration {

    @Bean
    public String hello(){
        return "hello";
    }
}

```

```java
//SpringRunner.java
package boot.jungwoo.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SpringRunner implements ApplicationRunner {

    @Autowired
    private String hello;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=======================");
        System.out.println(hello);
        System.out.println("=======================");

    }
}

```



#### 2.어떤 프로파일을 활성화 할 것인가?

- spring.profiles.active

```java
//application.properties		
spring.profiles.active=prod
```

**이것도 외부설정에서 배웠듯이 우선순위를 따른다.**

<img src="/Users/jungwoo/Library/Application Support/typora-user-images/image-20211003015754313.png" alt="image-20211003015754313" style="width:33%;" />

이렇게 두개의 properties가 있다면 application-prod.properties가 application.properties를 오버라이딩한다.

#### 3.어떤 프로파일을 추가할 것인가?

- spring.profiles.include

<img src="/Users/jungwoo/Library/Application Support/typora-user-images/image-20211003020034281.png" alt="image-20211003020034281" style="width:33%;" />

이 상황에서 application-db.properties를 추가하고 싶다면

```java
//application-prod.properties
spring.profiles.include=db	
```

이렇게 적용하면 application-db.properties 프로파일을 추가한다. 



#### 4.프로파일용 프로퍼티

- application-{profile}.properties