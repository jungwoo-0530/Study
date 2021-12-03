# 웹(Web)
## environment
* IDE : IntelliJ
* Database : H2
* Build Tool : Gradle
* Java.ver : 8
* Framework : spring - boot 2.5.6





## function

1. 회원
   1. 회원 등록
   
   2. 로그인
   
   3. 회원 목록 - Admin 전용
   
   4. 회원 추방 -  Admin 전용
   
   5. 회원 탈퇴
   
      
2. 상품
   1. 상품 등록 - Provider 전용
   2. 상품 제거 - Provider, Admin 전용
   3. 상품 목록
   4. 상품 장바구니
   5. 상품 관리 - Provider, Admin 전용
3. 주문
   1. 주문 목록
   2. 상품 주문
   3. 주문 취소
   4. 주문 반품
   5. 상품 주문 목록 - 누가 몇개 샀는지(Provider 전용)
4. 기타
   1. QnA 게시판 - Comment로 답변
   2. 이메일 질문(1:1) - Admin 이메일로 전송.





## Dependency
```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-devtools'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.h2database:h2'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```



## H2 DB 생성

1. https://www.h2database.com/로 접속 후 자신에게 맞는 버전 설치.
2. 압축 풀고 터미널로 h2 실행
   - ./h2.sh
3. 실행하기 전에 h2.sh의 권한을 사용할 수 있게 풀어 준다
   - chmod +x h2.sh
4. DB 생성
   - <img src="img/image-20211120052906801.png" alt="image-20211120052906801" style="width:50%;" />
   - 위에 스샷에서 second가 DB 이름이다.
5. DB 접속 
   - JDBC URL에 jdbc:h2:tcp://localhost/~/second 입력 후 연결.





## 1 - 1. 회원 등록

- 구현 안한 것
  - 시큐리티
  - 에러 처리(validated)

- User Entity

  ```java
  package com.example.secondproject.domain.user;
  
  import lombok.AllArgsConstructor;
  import lombok.Getter;
  import lombok.NoArgsConstructor;
  import lombok.Setter;
  
  import javax.persistence.*;
  
  @Entity
  @Getter
  @Setter
  @NoArgsConstructor
  public class User {
  
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Column(name = "USER_ID")
      private Long id;
  
      private String loginid;
  
      private String name;
  
      private String password;
  
      private String email;
  
      public User(String loginid, String name, String password, String email) {
          this.loginid = loginid;
          this.name = name;
          this.password = password;
          this.email = email;
      }
  
      //주소는 api사용
  
  }
  
  ```

  

- User Dto(//RegisterForm.class)

- ```java
  package com.example.secondproject.dto;
  
  import lombok.Data;
  import lombok.Getter;
  
  import javax.validation.constraints.Email;
  import javax.validation.constraints.NotBlank;
  import javax.validation.constraints.Size;
  
  @Data
  public class RegisterForm {
  
      @NotBlank(message = "id를 꼭 입력해 주세요")
      @Size(min = 5, max = 15, message = "id는 최소 5, 최대 15글자를 입력해주세요")
      private String loginId;
  
      @NotBlank
      private String password;
  
      @NotBlank
      private String name;
  
      @Email(message = "이메일 양식에 맞춰서 입력해주세요")
      @NotBlank(message = "이메일을 입력해주세요")//@Email이 null도 허용
      private String email;
  
      //@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
      //핸드폰 번호
  
  }
  
  ```

  - 우선 NotBlank로 항상 비어있어서는 안된다는 조건을 단다. NotBlank는 "" " "도 캐치하므로 NotBlank를 사용하는 것이 좋다.
  - Email은 javax에서 email형식을 맞춰서 제약조건을 걸어준다.

- User Repository

```java
package com.example.secondproject.repository;

import com.example.secondproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByLoginid(String loginid);

}

```

- User Service

```java
package com.example.secondproject.service;

import com.example.secondproject.domain.user.User;
import com.example.secondproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(User user) {
        //password 암호화.
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    //password가 일치하지 않을 경우, null 반환.
    //findByLoginId의 반환이 optional이므로 null 반환 가능
    public boolean validationLogin(String loginId, String password) {
        User loginUser = userRepository.findByLoginid(loginId);

        if(loginUser == null){
            System.out.println("해당 아이디의 유저가 존재하지 않습니다");
            return false;
        }

        if (!passwordEncoder.matches(password, loginUser.getPassword())) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }


    @Transactional
    @PostConstruct
    public void initUserDb() {
        User user = new User("test", "김정우",
                "test", "test@naver.com");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }


    public List<User> findAllUsers() {return userRepository.findAll();}

}
```



- Join Form (회원 등록할 때 사용하는 Dto)



```java
package com.example.secondproject.dto;

import lombok.Data;

@Data
public class LoginForm {
    private String loginid;
    private String password;
}

```



- 쿠키, 세션
  - 

- 아이디, 이메일 중복 체크

```

```



- 암호화

  - 암화화는 아래와 같이 config class를 만들어서 WebSecurityConfigurerAdapter를 상속받으면 PasswordEncoder 객체를 만들 수 있음. 객체를 사용하여 BCryptPasswordEncoder()객체를 받음.

  - ```java
    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().disable()
                    .csrf().disable()
                    .formLogin().disable()
                    .headers().frameOptions().disable();
        }
    
    
    }
    ```

    

  <img src="img/image-20211120062958394.png" alt="image-20211120062958394" style="width:80%;" />



## 1 - 2. 로그인

1. 구현 안한 것
   1. 시큐리티
   2. 에러 처리(validated)
   3. 세션, 쿠키







우선 시큐리티를 적용하기 전에 간단하게 용어 정리 먼저 해야한다.

- Authentication
  - 인증
  - 애플리케이션에서는 인증에서 사용되는 객체



위에서 만들었던 회원등록도 post 방식으로 form 전송을 하므로 csrf를 사용한다. 그래서 현재 상태에서는 정상적으로 작동을 하지 않는다. 그렇기에 disable을 하여 csrf를 작동하지 못하게 한다.

csrf는 데이터 변조 공격이므로 csrf을 적용 후에는 모든 POST방식의 데이터 전송에 토큰 값이 있어야 한다. GET 방식을 제외한 POST, PATCH, DELETE 메서드에만 적용됩니다.

토큰 값을 세션에 지정된 이름으로 저장돼있기 때문에 아래와 같이 Form 태그 안에 삽입해주면 된다.

```html
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
```



sec의 태그를 사용하기 위해서는 

```
implementation group: 'org.thymeleaf.extras', name: 'thymeleaf-extras-springsecurity5', version: '3.0.4.RELEASE'
```

```java
@Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
```

를 추가하고 html파일에 

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org/" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

```

을 작성하면 sec태그를 사용할 수 있다.

버튼에 아래와 같은 코드를 추가로 입력할 하면 권한이나 인증된 상태에 따라서 해당 버튼이 보이거나 안 보이게 할 수 있다.

```html
sec:authorize="isAnonymous()"
sec:authorize="isAuthenticated()"
sec:authorize="hasRole()"
```

- isAnonymous() : 익명의 사용자
- isAuthenticated() : 인증된 사용자(로그인된 회원)
- hasRole() : 부여된 특정 롤에 따라서 보여짐.
  - sec:authorize="hasRole("ROLE_ADMIN")" : admin 롤을 가진 회원에게 보여짐.

```html
<a sec:authorize="isAnonymous()" class="btn btn-secondary mr-2 my-2 my-sm-0" th:href="@{/register}">회원가입</a>
        <a sec:authorize="isAnonymous()" class="btn btn-secondary my-2 my-sm-0" th:href="@{/login}">로그인</a> <!-- 로그인이 안된 경우  -->
        <a sec:authorize="isAuthenticated()" class="btn btn-secondary my-2 my-sm-0" th:href="@{/logout}">로그아웃</a> <!-- 로그인이 안된 경우  -->
```

로그인 전에는 모든 사용자가 볼 수 있도록 설정한 상태, 로그인 후에는 로그아웃만 보여지게 만든 것.



WebSecurityConfig.java

```java
package com.example.secondproject.config;

import com.example.secondproject.login.CustomUserDetailsService;
import com.example.secondproject.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/users/denied");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

}

```

Member.java

```java
package com.example.secondproject.domain.user;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String loginid;

    private String name;

    private String password;

    private String email;


    @Builder
    public Member(Long id, String loginid, String name, String password, String email) {
        this.id = id;
        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public Member(String loginid, String name, String password, String email) {

        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    //주소는 api사용

}

```



MemberRole.java

```java
package com.example.secondproject.domain.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private String value;
}

```



MemberDto.java

```java
package com.example.secondproject.domain.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String loginid;
    private String password;
    private String name;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .loginid(loginid)
                .name(name)
                .password(password)
                .email(email)
                .build();

    }

    @Builder
    public MemberDto(Long id, String loginid, String name, String password, String email) {
        this.id = id;
        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
    }

}

```



MemberController.java

```java
package com.example.secondproject.controller;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.RegisterForm;
import com.example.secondproject.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    //회원가입
    @GetMapping("/register")
    public String joinForm(@ModelAttribute RegisterForm registerForm) {
        return "users/register";
    }

    @PostMapping("/register")
    public String join(@ModelAttribute @Validated RegisterForm registerForm,
                       BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return "users/register";
        }

        Member newMember = new Member();
        newMember.setLoginid(registerForm.getLoginid());
        newMember.setName(registerForm.getName());
        newMember.setPassword(registerForm.getPassword());
        newMember.setEmail(registerForm.getEmail());

        memberService.createUser(newMember);

        return "redirect:/";
    }

//로그인
    @GetMapping("/login")
    public String memberLogin() {
        return "users/loginForm1";
    }

    @GetMapping("/login/denied")
    public String memberLoginDenied(){
        return "/users/denied";
    }

    @GetMapping("/logout")
    public String memberLogout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }



}

```



CustomUserDetailsService.java

```java
package com.example.secondproject.login;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.domain.user.MemberRole;
import com.example.secondproject.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> memberEntityWrapper = memberRepository.findByLoginid(loginId);
        Member member = memberEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin").equals(loginId)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        }

        return new User(member.getLoginid(), member.getPassword(), authorities);
    }
}

```



loginForm1.html

```html
<!doctype html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="fragments/common ::head('Second')">

</head>

<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top" th:replace="fragments/common :: menu('board')">
</nav>

<div class="container">

    <div class="py-5 text-center">
        <h2>로그인</h2>
    </div>
    <form role="form" action="/login" method="post">
        <div>
            <label>로그인 ID</label>
            <input type="text" name="username">
            <!--            <div class="field-error" th:errors="*{loginid}" />-->
        </div>
        <div>
            <label>비밀번호</label>
            <input type="password" name="password">
            <!--            <div class="field-error" th:errors="*{password}" />-->
        </div>

        <hr class="my-4">

        <div class="row">
            <div class="col">
                <button class="w-50 btn btn-primary btn-lg" type="submit">로그인</button>
            </div>
            <div class="col">
                <button class="w-50 btn btn-secondary btn-lg" onclick="location.href='items.html'"
                        th:onclick="|location.href='@{/}'|"
                        type="button">취소</button>
            </div>
        </div>

    </form>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="/docs/4.5/assets/js/vendor/jquery.slim.min.js"><\/script>')</script><script src="/docs/4.5/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
    <script src="form-validation.js"></script>
</body>

</html>
```



## 1 - 3. 회원 목록





## 1 - 4. 회원 추방





## 1 - 5. 회원 탈퇴





<details> 
  <summary>접기/펼치기</summary> 
  <div markdown="1">  
  ```java
  public class hello {     public static void main(String[] args) {         System.out.println("hello java");     } }  
  ```  
  </div> </details>

