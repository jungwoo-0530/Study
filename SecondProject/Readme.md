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
   2. 회원 목록 - Admin 전용
   3. 회원 추방 -  Admin 전용
   4. 회원 탈퇴
   5. 로그인
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

우선 의존성 추가를 한 내용은 순서대로 spring data jpa, thymeleaf, web, devtools, lombok, h2이다.




## 메인화면 만들기
1. 공통으로 들어가는 view

우선 어느 화면으로 이동하던 대쉬보드는 항상 있어야하므로 common.html을 _resource_templates/fragments에 생성한다. 즉, 여기에는 매 화면에 공통으로 들어가는 view를 작성한다.
``` html
<!DOCTYPE html>

<html xmlns:th="http://www.thymeleaf.org">

<head th:fragment="head(title)">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="starter-template.css" th:href="@{/starter-template.css}" rel="stylesheet">

    <title th:text="${title}">Second</title>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top" th:fragment="menu(menu)">
    <a class="navbar-brand" href="/">JPASHOP</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active" th:classappend="${menu} == 'home'? 'active'">
                <a class="nav-link" href="#" th:href="@{/}">Home <span class="sr-only" th:if="${menu} == 'home'">(current)</span></a>
            </li>
            <li class="nav-item" th:classappend="${menu} == 'join'? 'active'">
                <a class="nav-link" href="#" th:href="@{/members/join}">회원가입 <span class="sr-only" th:if="${menu} == 'join'">(current)</span></a>
            </li>
            <li class="nav-item" th:classappend="${menu} == 'board'? 'active'">
                <a class="nav-link" href="#" th:href="@{/boards}">게시판 <span class="sr-only" th:if="${menu} == 'board'">(current)</span></a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
            <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
</body>

</html>

```
네비바에서 


이제 home.html을 작성하여 메인화면을 생성한다. 
```html
<!doctype html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="fragments/common ::head('second')">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top" th:replace="fragments/common :: menu('home')">

</nav>

<main role="main" class="container">

    <div class="starter-template">
        <h1>Bootstrap starter template</h1>
        <p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a mostly barebones HTML document.</p>
    </div>

</main>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>


</body>
</html>

```

