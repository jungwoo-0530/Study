# Servlet



### 1. Servlet이란 ?

-> 자바를 사용하여 웹의 요청을 **동적**으로 처리가 가능한 클래스.

1. 사용자가 웹 페이지 form(HTML Form)을 통해 자신의 정보를 입력 (Input)

2. Servlet의 doGet() 또는 doPost() 매서드는 입력한 from data에 맞게 DB또는 다른 소스에서 관련된 정보를 검색

3. 이 정보를 이용하여 사용자의 요청에 맞는 적절한 동적 컨텐츠(HTML Page)를 만들어서 제공 (Output)







### 2. Servlet property

- 클라이언트의 요청에 대해 **동적으로 작동**하는 웹 어플리케이션 컴포넌트
- html을 사용하여 요청에 응답한다.
- Java Thread를 이용하여 동작한다.
- MVC 패턴에서 Controller로 이용된다.
- HTTP 프로토콜 서비스를 지원하는 javax.servlet.http.HttpServlet 클래스를 상속받는다.
- UDP보다 처리 속도가 느리다.
- HTML 변경 시 Servlet을 재컴파일해야 하는 단점이 있다.

**#동적으로 작동하는 것이란 사용자가 요청한 시점에 페이지를 생성해서 전달해주는 것.**



### 3.  Servlet Structure

![img](https://blog.kakaocdn.net/dn/bhFxuh/btqw1vaiXQw/NjpVtrub8x1eFBznGWYfTK/img.png)





1. Web Server는 HTTP request를 Web Container(Servlet Container)에게 위임한다.

​    1) web.xml 설정에서 어떤 URL과 매핑되어 있는지 확인

​    2) 클라이언트(browser)의 요청 URL을 보고 적절한 Servlet을 실행

2. Web Container는 service() 메서드를 호출하기 전에 Servlet 객체를 메모리에 올린다.

   1) Web Container는 적절한 Servlet 파일을 컴파일(.class 파일 생성)한다.

   2) .class 파일을 메모리에 올려 Servlet 객체를 만든다.

   3) 메모리에 로드될 때 Servlet 객체를 초기화하는 init() 메서드가 실행된다.

3. Web Container는 Request가 올 때 마다 thread를 생성하여 처리한다.

   1) 각 thread는 Servlet의 단일 객체에 대한 service() 메서드를 실행한다.