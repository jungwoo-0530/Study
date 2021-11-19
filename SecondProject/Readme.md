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
- User Dto

- User Repository

- User Service

- Join Form (회원 등록할 때 사용하는 Dto)

<img src="img/image-20211120062958394.png" alt="image-20211120062958394" style="width:80%;" />



- 쿠키, 세션
  - 

- 아이디, 이메일 중복 체크

```

```



## 1 - 2. 로그인

1. 구현 안한 것
   1. 시큐리티
   2. 에러 처리(validated)
   3. 세션, 쿠키