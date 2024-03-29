# 서블릿

### 서블릿 (Servlet)

- 자바 엔터프라이즈 에디션은 웹 애플리케이션 개발용 스팩과 API 제공. 
- 요청 당 쓰레드 (만들거나, 풀에서 가져다가) 사용
- 그 중에 가장 중요한 클래스중 하나가 HttpServlet.

### 서블릿 등장 이전에 사용하던 기술인 CGI (Common Gateway Interface) 

● 요청 당 프로세스를 만들어 사용

### 서블릿의 장점 (CGI에 비해)

- 빠르다.

- 플랫폼 독립적
- 보안
- 이식성

### 서블릿 엔진 또는 서블릿 컨테이너 (톰캣, 제티, 언더토, ...)

- 세션 관리

- 네트워크 서비스
- MIME 기반 메시지 인코딩 디코딩
- 서블릿 생명주기 관리
- ...

### 서블릿 생명주기

 ● **서블릿 컨테이너**가 **서블릿 인스턴스의 init() 메소드**를 호출하여 **초기화** 한다. 

○ 최초 요청을 받았을 때 **한번 초기화 하고 나면 그 다음 요청부터는 이 과정을 생략**한다. 

● 서블릿이 초기화 된 다음부터 클라이언트의 요청을 처리할 수 있다. **각 요청은 별도의 쓰레드로 처리**하고 이때 **서블릿 인스턴스의 service() 메소드**를 호출한다.

○ 이 안에서 **HTTP 요청**을 받고 클라이언트로 보낼 **HTTP 응답**을 만든다. 

○ **service()**는 보통 HTTP Method에 따라 **doGet(), doPost()** 등으로 처리를 **위임**한다. 

○ 따라서 보통 **doGet() 또는 doPost()**를 구현한다.

● **서블릿 컨테이너** 판단에 따라 **해당 서블릿을 메모리에서 내려야 할 시점에 destroy()**를 호출한다.



## 서블릿 애플리케이션

```java
//HelloServelt.java
package me.whiteship;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println("<body>");
        resp.getWriter().println("<h1>Hello Servlet</h1>");
        resp.getWriter().println("</body>");
        resp.getWriter().println("</head>");
        resp.getWriter().println("</html>");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}

```

서블릿을 만들고 서블릿 독자적으로 실행할 수 없으므로 톰캣(서블릿 컨테이너)에 배포하는 방식으로 실행해야한다. 

톰캣에 배포를 마친 후에 서블릿을 web.xml에 등록해야한다.

 <img src="../img/image-20211003104636507.png" alt="image-20211003104636507" style="width:50%;" />



실행하면 결과는 실행하면 init()한번되고 계속 접속을 하면 doget()만 실행된다. 서버를 끄면 destory()가 호출되며 종료된다.



## 서블릿 리스너와 서블릿 필터

#### 서블릿 리스너

- 서블릿 컨테이너에서 발생하는 이벤트. 서블릿 라이프사이클 변화, 세션 변화, 애트리뷰트 변화 등.. 여러가지 이벤트에 특정한 코드를 실행해야한다라는 개념.

- 웹 애플리케이션에서 발생하는 주요 이벤트를 감지하고 각 이벤트에 특별한 작업이 필요한 경우에 사용할 수 있다.
  - 서블릿 컨텍스트 수준의 이벤트
    - 컨텍스트 라이프사이클 이벤트
    - 컨텍스트 애트리뷰트 변경 이벤트
  - 세션 수준의 이벤트
    - 세션 라이프사이클 이벤트
    - 세션 애트리뷰트 변경 이벤트

아래의 코드는 예로 컨텍스트 라이프사이클 이벤트에 대한 코드이다.

```java
package me.whiteship;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context Initialized");
        sce.getServletContext().setAttribute("name", "jungwoo");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context Destroyed");
    }
}
```

이러한 리스너 등록은 web.xml에 등록한다.

<img src="../img/image-20211003105551421.png" alt="image-20211003105551421" style="width:50%;" />



이제 리스너를 활용한 코드는 아래와 같다.

```java
package me.whiteship;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println("<body>");
        resp.getWriter().println("<h1>Hello, " + getName() + "</h1>");
        resp.getWriter().println("</body>");
        resp.getWriter().println("</head>");
        resp.getWriter().println("</html>");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
```



<img src="../img/image-20211003105727263.png" alt="image-20211003105727263" style="width:30%;" />





<img src="../img/image-20211003105758145.png" alt="image-20211003105758145" style="width:30%;" />



그 결과 리스너가 init되고 서블릿 init, doGet 실행 후 서블릿 destory되고 리스너가 destroy된 것을 알 수 있다.





#### 서블릿 필터

- 어떤 요청이 서블릿 doget() 오기 전에 어떠한 처리, 또한 응답하기 전에 추가적인 작업을 하고 싶을 때...

- 들어온 요청을 서블릿으로 보내고, 또 서블릿이 작성한 응답을 클라이언트로 보내기 전에 특별한 처리가 필요한 경우에 사용할 수 있다. 

- 체인 형태의 구조

  - web.xml에서 정의한대로

    <img src="../img/image-20211003053943752.png" alt="image-20211003053943752" style="width:33%;" />







--------------------------------------------------------------------------------------

다음은 Context에 대해서 많이 헷갈리는 관계로 구글링하여 찾아서 이해한 결과이다. 다만 아직 완벽한 이해가 아니라 앞으로 더 공부해서 수정할 예정.

스프링에서 말하는 "애플리케이션 컨텍스트"는 스프링이 관리하는 빈들이 담겨 있는 컨테이너.

스프링 안에는 여러 종류의 애플리케이션 컨텍스트 구현체가 있는데, ApplicationContext라는 인터페이스를 구현한 객체들이 다 이 애플리케이션 컨텍스트이다. 

웹 애플리케이션 컨텍스트는 ApplicationContext를 확장한 WebApplicationContext 인터페이스의 구현체를 말한다. WebApplicationContext는 ApplicationContext에 getServletContext() 메서드가 추가된 인터페이스입니다. 이 메서드를 호출하면 서블릿 컨텍스트를 반환됩니다. 결국 웹 애플리케이션 컨텍스트는 스프링 애플리케이션 컨텍스트의 변종이면서 서블릿 컨텍스트와 연관 관계에 있다는 정도로 정리가 됩니다. 이 메서드가 추가됨과 동시에 서블릿 컨텍스트를 이용한 몇가지 빈 생애 주기 스코프(애플리케이션, 리퀘스트, 세션 등)가 추가되기도 합니다.

스프링에서, 필수는 아니지만, 웹 애플리케이션을 구성할 때, 도메인과 관련된 빈들은 루트 애플리케이션 컨텍스트에 등록하고 웹과 관련된 UI 성 빈(주로 Controller)은 웹 애플리케이션 컨텍스트라는 별도의 애플리케인션 컨텍스트에 등록해서 사용합니다.

이렇게 분리를 하면 루트 애플리케이션 컨텍스트와 웹 애플리케이션 컨텍스트과 부모 자식 관계가 되는데 웹 애플리케이션 컨텍스트의 빈들은 루트 애플리케이션 컨텍스트의 빈들 주입 받을 수 있지만 거꾸로 루트 애플리케이션 컨텍스트의 빈은 웹 애플리케이션 컨텍스트 같은 자식 애플리케이션 컨텍스트의 빈을 주입 받을 수 없습니다.

이렇게 분리함으로써 Web 기술과 도메인이 분리되는 장점도 있지만, 의존 방향이 역전되거나(도메인이 웹에 의존하는 상황) 상호 의존하는 상황을 막아기도 하고, AOP가 적용되는 범위를 제한하는 등의 이점이 있습니다.



궁금해서 조금 찾아보니 servletContext는 스프링에 관계없이 servlet에 기반한 java web application이 가지고 있는것이고,

반대로 applicationContext(WebApplicationcontext가 상속한)는 spring의 것이고 spring bean들을 가지고 있는 컨테이너 라는 설명이 나왔습니다.(https://stackoverflow.com/questions/31931848/applicationcontext-and-servletcontext)



제가 이해한게 맞다면 둘은 완전히 다른것인데

contextLoaderListener에서 servletContext에 WebApplicationcontext를 등록하는 이유는 WebApplicationContext에 등록된 bean들을 servlet들이 사용할수있게 하는 것이 맞나요?

--------------------------------------------------------------------------------------------------------------

## 스프링 IoC 컨테이너 연동



<img src="../img/image-20211003053856343.png" alt="image-20211003053856343" style="width:50%;" />

#### 서블릿 애플리케이션에 스프링 연동하기

- 서블릿에서 스프링이 제공하는 **IoC** 컨테이너 활용하는 방법 

- 스프링이 제공하는 서블릿 구현체 DispatcherServlet 사용하기





####  ContextLoaderListener

- 서블릿 리스너 구현체
- ApplicationContext를 만들어 준다.
- ApplicationContext를 서블릿 컨텍스트 라이프사이클에 따라 등록하고 소멸시켜준다.
- 서블릿에서 IoC 컨테이너를 ServletContext를 통해 꺼내 사용할 수 있다.





서블릿에서 스프링이 제공하는 **IoC** 컨테이너 활용하는 방법을 먼저 본다면

스프링 IoC컨테이너 즉, 애플리케이션컨텍스트를 서블릿 생명주기에 맞춰서 바인딩해준다.

쉽게 이야기 해서 **ContextLoaderListener는 웹 애플리케이션 컨텍스트에 등록되어있는 서블릿들을 사용할 수 있도록 애플리케이션 컨텍스트를 만들어서 서블릿 컨텍스트에 등록해준다. 그리고 서블릿이 종료될 시점에 애플리케이션 컨텍스트를 제거한다. **

서블릿 컨텍스트의 라이프사이클에 맞춰서 스프링이 제공해주는 애플리케이션 컨텍스트를 이제 연동해주는 가장 핵심적인 리스너라고 보면된다.

즉, 먼저 애플리케이션 컨텍스트를 만들어야 한다.

기본으로는 xml기반에 애플리케이션 컨텍스트를 사용하는데 요즘은 자바 설정 파일을 사용한다.

```xml
<web-app>

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

  	<context-param>
      	<param-name>contextClass</param-name>
      	<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
  	</context-param>
  
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>me.whiteship.AppConfig</param-value>
    </context-param>

</web-app>
```

<img src="../img/image-20211003113053909.png" alt="image-20211003113053909" style="width:40%;" />

web.xml이다. 파라미터를 보면 AppConfig라는 자바 설정 파일을 만든다. 

<img src="../img/image-20211003113838140.png" alt="image-20211003113838140" style="width:40%;" />

이제 컴포넌트 스캔을 사용하여 빈을 하나 만들어서 등록을 한다. 

```java
package me.whiteship;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getName() {
        return "jungwoo";
    }
}

```



```java
package me.whiteship;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        HelloService helloService = context.getBean(HelloService.class);
      //서블릿에서 IoC 컨테이너를 ServletContext를 통해 꺼내 사용
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println("<body>");
        resp.getWriter().println("<h1>Hello, " + helloService.getName() + "</h1>");
        resp.getWriter().println("</body>");
        resp.getWriter().println("</head>");
        resp.getWriter().println("</html>");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}

```



이렇게 사용한다면 서블릿을 만들 때마다 불편한 점은 요청하나를 처리할 때마다 web.xml에 계속 추가를 해야한다. 그러기엔 자원 낭비가 너무 심하므로 그래서 나온 패턴이 Front Controller 패턴이 있다. 그 패턴은 모든 요청을 하나의 컨트롤러가 받아서 해당 요청을 처리할 핸들러에게 분배(Dispatch)한다. 스프링이 그러한 역할을 하는 서블릿을 구현을 해놨는데, 그것은 스프링 MVC에 가장 핵심적인 Class인 DispatcherServlet(서블릿 그 자체)이다. 

(Servlet) Context에 등록되어있는 에플리케이션 컨텍스트가 Root WebApplicationContext(부모)를 상속받는 Servlet WebApplicationContext를 DispatcherServlet이 만든다.

이렇게 상속관계를 만드는 이유는 ContextLoaderListener가 만들어준 루트 웹에플리케이션컨텍스트는 여러 다른 서블릿에서도 공유해서 사용할 수가 있다. 그러나 DispatcherServlet에서 만든 애플리케이션컨텍스트는 그 DispatcherServlet 안에서만 스코프가 한정되어있다. 즉, DispatcherServlet은 다른 DispatcherServlet을 모른다. 혹시라도 여러 DispatcherServlet만들어야하고 공유해야하는 빈들이 있어야하는 경우를 위해서 상속구조를 만들 수 있게 한 것.

그래서 Root WebApplicationContext는 Web과 관련된 빈들은 등록이 되지 않는다. 공용으로 써야하는 자원(빈)들이기 때문에 웹과 관련된 빈들이 굳이 들어갈 필요가 없기 때문이다. 

Servlet WebApplicationContext들은 Web과 관련된 빈들이다.



WebApplicationContext

- ContextLoaderListener -> Root WebApplicationContext 생성
- DispatcherServlet -> Servlet WebApplicationContext 생성					





----------------------------------------------------------------------------

# 스프링 MVC 연동

이제 스프링 MVC를 사용하고 싶다.

```java
//HelloController.java
package me.whiteship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, " + helloService.getName();
    }

}
```

이런한 핸들러 쪽으로 요청을 dispatch해줄 수 있는, 저런 에노테이션을 이해하는, 저런 응답을 HTTP 리스폰스로 만들어 줄 수 있는 DispatcherServlet을 사용해야한다.



지금까지 만든 @Service를 붙인 HelloService는 ContextLoaderListener가 만들어 주는 애플리케이션 컨텍스트에 등록이되야하고 HelloController는 DispatcherServlet이 만들어 주는 애플리케이션 컨텍스트에 등록이 되야한다.

<img src="../img/image-20211003113511892.png" alt="image-20211003113511892" style="zoom:50%;" />

그러기 위해서는 @ComponentScan에 필터를 적용해서 컨트롤러를 예외시켜준다.

똑같이 DispatcherServlet의 자바 설정 파일을 만들어 준다.

```java
package me.whiteship;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(useDefaultFilters = false, includeFilters 
               = @ComponentScan.Filter(Controller.class))
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/", ".jsp");
    }

}

```

이런 자바 설정 파일을 DispatcherServlet이 만들어 주는 애플리케이션 컨텍스트가 사용해야한다.

<img src="../img/image-20211003114620975.png" alt="image-20211003114620975" style="width:50%;" />

이렇게 web.xml을 설정한다. 참고로 name은 내가 정한다. 매핑을 /app/으로 들어오는 모든 요청이 다 DispatcherServlet으로 들어오고 뒤에 /hello가 더 붙어 있다면 아까 만들어 놓은 HelloController가 실행될것이다.



모든 상황을 이렇게 계층 구조를 만들 필요는 없다. 필요할 때만 사용하고 계층 구조가 필요 없을 때, 모든 빈을 DispatcherServlet에 등록하여 사용하면 된다.

위 프로젝트에서 계층 구조를 제거할려면 두 개의 자바 설정 파일에서 하나를 삭제하고 남아있는 자바 설정 파일에서 컴포넌트스캐너의 필터를 제거한 후, web.xml에서 리스너를 삭제한다. 그러면 자동으로 DispatcherServlet로 @Service, @Controller 빈들이 다 들어갈 것이다. 반대로 Root WebApplicationContext로도 가능하나 좋은 구조가 아니다.

**스프링과 스프링 부트와는 다르다. 지금까지는 서블릿 컨테이너가 먼저 뜨고 서블릿 컨테이너 안에 등록되는 서블릿 애플리케이션에다가 ContextLoaderListener등록한다던가 DispatcherServlet등록하는 식으로 스프링을 연동하는 방법이다. 대략적으로는 톰캣안에 스프링을 안에 넣은 것이라고 생각 스프링 부트는 스프링 부트안에 톰캣을 넣은 것이라고 생각하면 편하다고한다.**



# 즉, Applicaiton Context는 스프링이 관리하는 빈들이 담겨있는 컨테이너라고 생각. 스프링에는 여러 종류의 Application Context가 있다. 

# WebApplicationContext는 ApplicationContext를 확장한 인터페이스이고 Servlet, Root WebApplicationContext는 WebApplicationContext 인터페이스의 구현체이다.





## DispatcherServlet 동작 원리

- 스프링 MVC의 핵심.
- Front Controller 역할을 한다.

- 

#### DispatcherServlet 초기화

- 다음의 특별한 타입의 빈들을 찾거나, 기본 전략에 해당하는 빈들을 등록한다.
- **HandlerMapping: 핸들러를 찾아주는 인터페이스**
- **HandlerAdapter: 핸들러를 실행하는 인터페이스**
- HandlerExceptionResolver
- ViewResolver
- ...

#### DispatcherServlet 동작 순서

1. 요청을 분석한다. (로케일, 테마, 멀티파트 등)
2. (핸들러 맵핑에게 위임하여) 요청을 처리할 핸들러를 찾는다.
3. (등록되어 있는 핸들러 어댑터 중에) 해당 핸들러를 실행할 수 있는 “핸들러 어댑터”를 찾는다.
4. 찾아낸 “핸들러 어댑터”를 사용해서 핸들러의 응답을 처리한다.
   - 핸들러의 리턴값을 보고 어떻게 처리할지 판단한다.
     - 뷰 이름에 해당하는 뷰를 찾아서 모델 데이터를 랜더링한다.
     - @ResponseBody가 있다면 Converter를 사용해서 응답 본문을 만들고
5. (부가적으로) 예외가 발생했다면, 예외 처리 핸들러에 요청 처리를 위임한다.
6. 최종적으로 응답을 보낸다.

```java
package me.whiteship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    HelloService helloService;
//view가 아닌 문자열을 리턴
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, " + helloService.getName();
    }
//view의 이름을 리턴하여 view를 찾는다,
    @GetMapping("/sample")
    public void sample() {
      return "/WEB-INF/sample.jsp"
    }

}
```





#### HandlerMapping

- RequestMappingHandlerMapping
  - RequestMappingHandlerMapping 클래스는 Controller와 url을 매핑시켜주는 HandlerMapping의 구현클래스이다.

#### HandlerAdapter

 ● RequestMappingHandlerAdapter



#### 컨트롤러 작성 두가지

```java
package me.whiteship;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Controller("/simple")
public class SimpleController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("simple");
    }
}

```

```java
package me.whiteship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    HelloService helloService;
//view가 아닌 문자열을 리턴
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, " + helloService.getName();
    }
//view의 이름을 리턴하여 view를 찾는다,
    @GetMapping("/sample")
    public void sample() {
      return "/WEB-INF/sample.jsp"
    }

}
```

지금까지 본 것은 DispatcherServlet이 제공하는 핸들러, 어댑터, viewResolver를 사용하여 위와 같이 컨트롤러를 작성할 수 있었다. 





 ![image-20211004033432166](../img/image-20211004033432166.png)

위 사진은 DispatcherServlet이 제공하는 기능들의 초기화들이다. 우리가 지금까지 배웠던 핸들러맵핑, 어댑터, 뷰리졸버도 보인다. 위의 init()들은 서블릿이기 때문에 서블릿 생명주기를 따르며 최초에 한번만 이루어지고 프로젝트가 종료될 때까지 실행되지 않는다.

기능들은 loop문을 돌면서 해당하는 특정한 빈들을 찾는데 찾지 못한다면 기본전략을 사용하여 기능이 동작한다.

기능들은 DispatcherServlet.properties에 정의되어 있다.



### DispatcherServlet - ViewResolver 커스텀

#### ViewResolver

- InternalResourceViewResolver

#### InternalResourceViewResolver

- Prefix
- Suffix

ViewResolver는 기본전략이지만 우리가 따로 커스텀을 하여 사용할 수 있다.

여기서는 컨트롤러에서 view 위치까지 다 알려주었지만 뷰리졸버에 prefix, suffix를 추가하여 view에 이름만 간단하게 적어도 가능하다. 아래와 같다.

```java
package me.whiteship;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/", ".jsp");
    }

}
```



```java
package me.whiteship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, " + helloService.getName();
    }

    @GetMapping("/sample")
    public void sample() {}

}
```

```java
package me.whiteship;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Controller("/simple")
public class SimpleController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("simple");
    }
}
```

