

# HTTP 헤더의 분류

[여기](#단순 전송)


## 표현

- Content-Type
  - 표현 데이터의 형식
  - 미디어 타입, 문자 인코딩
  - 예) text/html; charset=utf-8, application/json, image/png
  - 참고: application/json은 기본 문자 인코딩이 utf-8
- Content-Encoding
  - 표현 데이터의 압축 인코딩
  - **데이터를 전달하는 쪽에서 압축 후 인코딩 헤더를 추가**하고 **읽는 쪽에서 인코딩 헤더의 정보를 보고 적절히 압축을 해제**하여 사용
  - 예) gzip, deflate, identity(압축 x)

- Content-Language
  - 표현 데이터의 자연 언어
  - 예)ko, en, en-US
- Content-Length
  - 바이트 단위의 표현 데이터 길이
  - Transfer-Encoding을 사용하면 Content-Length 사용하면 안됨.



## 협상(Content Negotiation)

- 클라이언트가 선호나는 표현을 요청
- 협상 헤더는 요청(Request) 시에만 사용

### 협상 헤더의 종류

- Accept: 클라이언트가 선호하는 미디어 타입
- Accept-Charset: 클라이언트가 선호하는 문자 인코딩
- Accept-Encoding: 클라이언트가 선호나는 압축 인코딩
- Accept-Language: 클라이언트가 선호나는 자연 언어



### 협상 헤더의 우선순위

- q(Quality Values) 값을 사용하여 필드값의 우선 순위를 적용할 수 있음
  - 0~1 범위의 값을 사용하며 클수록 높은 우선순위를 가짐(생략시 1)
  - 예) Accept-Language : ko-KR, ko;q=0.9, en-US;q=0.8
- 구체적인 것이 높은 우선순위를 가짐
  - Accept: text/, text/plain, text/plain;format=flowed, */
  - 1. text/plain;format=flowed
    2. text
    3. text/*
    4. */*
- 구체적인 것을 기준으로 미디어 타입을 맞춤
  - Accept: text/*;q=0.3, text/html;q=0.7, text/html;level=1, text/html;level=2;q=0.4, \*/*;q=0.5
  - <img src="img/HTTP헤더/Screenshot of Typora (2022-11-19 6-19-22 PM).png" alt="Screenshot of Typora (2022-11-19 6-19-22 PM)" style="zoom:50%;" />

## 전송방식

- 서버에서 클라이언트로 전송하는 방식
- 주로 단순 전송, 압축 전송, 분할 전송, 범위 전송이 사용됨



### 단순 전송

- Content-Length

- 단순히 컨텐츠의 길이를 지정해주고 한번에 요청하고 한번에 받는 방법

- ```HTTP
  HTTP/1.1 200 OK
  Content-Type: text/html;charset=UTF-8
  **Content-Length: 3423**
  
  <html>
      ...
  </html>
  ```



### 압축 전송

- 메시지 본문을 압축하여 전송하는 방법

- 용량이 절반 이상 줄어드는 경우가 많기 때문에 사용을 권고

- Content-Encoding 헤더를 사용하여 압축 형식을 지정

- ```http
  HTTP/1.1 200 OK
  Content-Type: text/html;charset=UTF-8
  **Content-Encoding: gzip**
  Content-Length: 521
  
  lkj123kljoiasudlkjaweioluywlnfdo912u34ljko98udjkl
  ```



### 분할 전송

- 데이터를 분할하여 전송하는 방법
- Transfer-Encoding: chunked
- chunk : 덩어리
- 분할 전송 시에는 Contetnt-Length 헤더를 사용하면 안 됨
  - 처음에 길이를 예상할 수 없음
  - 분할해서 보낼 때 청크마다의 바이트 길이를 알려줌

- ```http
  HTTP/1.1 200 OK
  Content-Type: text/plain
  Transfer-Encoding: chunked
  
  5\r\n
  Hello\r\n
  5\r\n
  World\r\n
  0\r\n
  \r\n
  ```



### 범위 전송

- 범위를 지정해서 일부분만 가져오는 방법







## 일반 정보

### From

- 유저 에이전트의 이메일 정보
- 일반적으로 잘 사용되지 않고, 주로 검색 엔진 같은 곳에서 사용
- 크롤러와 같은 로보틱 사용자 에이전트를 실행하고 있다면, 반드시 전송해야함.
- 예) 검색 엔진 사이트에서 내 웹사이트를 크롤링할 때, 검색 엔진 담당자에게 연락해야 할 때 사용
- 요청 헤더

### Referer

- 현재 요청된 페이지 이전 웹페이지 주소

- A->B사이트로 이동하는 경우, B사이트를 요청할 때 Referer: A를 포함해서 요청

- 예) google.com에서 검색을 통해 tistory.com에 접속할 때 Referer: google.com을 포함해서 요청

- 요청 헤더

  

### User-Agent

- User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36
- 클라이언트의 애플리케이션 정보(웹 브라우저 정보 등등)
- 활용
  - 통계 정보
  - 어떤 종류의 브라우저에서 장애가 발생하는지 파악 가능
- 요청 헤더

### Server

- 요청을 처리하는 ORIGIN 서버의 소프트웨어 정보
- 예)
  - Server: Apache/2.2.22(Debien)
  - Server: nginx
- 응답 헤더

### Date

- 메시지가 발생한 날짜와 시간
- Date: Tue, 15 Nov 1994 08:12:31 GMT
- 응답 헤더(과거 스펙에선 요청에서도 사용했지만 최신 스펙에서는 응답에서만 사용)

## 특별한 정보

### Host

- 요청한 호스트의 정보(도메인)
- 필수 헤더
- 요청 헤더
- 하나의 서버가 여러 도메인을 처리해야 할 때 구분에 사용
- 하나의 IP주소에 여러 도메인이 적용되어 있을 때 구분에 사용

### Location

- 페이지 리다이렉션
- 리다이렉션 : 웹 브라우저는 3xx 응답의 결과에 Location 헤더가 있으면, Location 위치로 자동으로 이동
- 201(Created)응답에서 Location 값은 요청에 의해 새로 생성된 리소스의 URI

### Allow

- 허용 가능한 HTTP 메서드
- 405(Method Not Allowed)에서 응답 헤더에 포함해야함.
- 예) Allow: GET, HEAD, PUT

### Retry-After

- 유저 에이전트가 다음 요청을 하기까지 기다려야 하는 시간
- 503(Service Unavailable)응답의 경우에 서비스가 언제까지 불능인지 알려줄 수 있음
- 예)
  - Retry-After: Fri, 31 Dec 1999 23:59:59 GMT(날짜표기)
  - Retry-After: 120 (초단위 표기)

## 인증

### Authorization

- 클라이언트 인증 정보를 서버에 전달
- 예) Authorization: Basic xxxxxxxxxxxxxxxx

### WWW-Authenticate

- 리소스 접근 시 필요한 인증 방법 정의
- 401 Unauthorized 응답과 함께 사용
- WWW-Authenticate: Newauth realm="apps", type=1, title="Login to "apps"", Basic realm="simple"

## 쿠키

### Set-Cookie

- 서버에서 클라이언트로 쿠키 전달
- 응답 헤더

### Cookie

- 클라이언트가 서버에서 받은 쿠키를 저장하고, HTTP 요청 시 서버로 전달
- 요청 헤더
- 사용처 : 사용자 로그인 세션 관리, 광고 정보 트래킹
- 쿠키 정보는 항상 서버에 전송되기 때문에 네트워크 트래픽을 추가 유발
- 보안에 민감한 데이터는 저장하면 안 됨(주민 번호, 신용카드 번호 등등)
- 최소한의 정보만 사용해야 됨(세션 id, 인증 토큰)
- 서버에 전송하지 않고, 웹 브라우저 내부에 데이터를 저장하고 싶으면 웹 스토리지(localStorage, sessionStorage)를 참고

### 쿠키 - 생명 주기

- expires
  - 예) Set-Cookie: **expires**=Sat, 26-Dec-2020 04:39:21 GMT
  - 만료일이 되면 쿠키 삭제
  - 옵션 값이며, 지정하지 않으면 세션 쿠키로 취급되어 클라이언트 종료 시 쿠키 파기됨
    - 세션 쿠키 : 만료 날짜를 생략하면 브라우저 종료시 까지만 유지
    - 영속 쿠키 : 만료 날짜를 입력하면 해당 날짜까지 유지
- max-age
  - 예) Set-Cookie: max-age=3600 (3600초)
  - 쿠키가 만료될 때까지의 시간(초 단위)
  - 0이나 음수를 지정하면 쿠키 삭제
  - Exprires와 함께 지정 시 max-age의 우선순위가 더 높음

### 쿠키 - 도메인

- 예) domain=example.org
- 명시 - domain=example.org 지정해서 쿠키 생성
  - 명시한 문서 기준 도메인 + 서브 도메인 포함
  - exmaple.org에 더해 dev.example.org도 쿠키 접근 가능
- 생략 - exmple.org에서 쿠키를 생성하고 domain 지정 생략
  - exmaple.org에서만 쿠키 접근 가능
  - dev.exmaple.org에서는 쿠키 미접근 → 서브 도메인에서 접근 불가

### 쿠키 - 경로

- 예) path=/home
- 지정한 경로를 포함한 하위 경로 페이지만 쿠키 접근 가능
- 일반적으로 루트를 지정(path=/)

### 쿠키 - 보안

- Secure : 쿠키는 http, https를 구분하지 않고 전송하는데, Secure를 적용하면 https인 경우에만 쿠키를 전송
- HttpOnly : XSS 공격 방지, 자바스크립트에서 접근 불가(document.cookie), HTTP 전송에만 사용
- SameSite : XSRF 공격 방지, 요청 도메인과 쿠키에 설정된 도메인이 같은 경우만 쿠키 전송

## 캐시

- 캐시를 사용하면 서버에 최초 요청 시에만 데이터를 다운로드하여 **브라우저 캐시에 저장하고**, 해당 데이터의 변경이 일어날 때까지 로컬에 있는 데이터를 사용하여 네트워크 사용량, 브라우저 로딩 속도를 줄일 수 있다.
- <img src="img/HTTP헤더/Screenshot of Safari (2022-11-19 6-39-01 PM).png" alt="Screenshot of Safari (2022-11-19 6-39-01 PM)" style="zoom:50%;" />

### 캐시 제어 헤더

####  Cache-Control

- 캐시 지시어
- **Cache-Control: max-age** : 캐시 유효 시간 설정, 초단위
- **Cache-Control: no-cache** : 데이터는 캐시 해도 되지만, 항상 원(Origin) 서버에 검증하고 사용
- **Cache-Control: no-store** : 데이터에 민감한 정보가 있으므로 저장하면 안 됨, 메모리에서 사용하고 최대한 빨리 삭제
- Cache-Control: must-revalidate
  - 캐시 만료 후 최초 조회 시 원 서버에 검증해야 함
  - 원 서버 접근 실패 시 반드시 오류가 발생해야 함 - 504(Gateway Timeout)
  - no-cacahe의 경우 원서버에 접근할 수 없어 검증할 수 없는 경우에도 서버 설정에 따라서 캐시 데이터를 반환할 수도 있음
  - 캐시 유효시간이라면 캐시를 사용함

#### Pragma

- 캐시 제어(하위 호환)
- **Pragma: no-cache :** no-cache처럼 동작
- HTTP 1.0의 하위 호환으로, 지금은 거의 사용하지 않음

#### Expires

- 캐시 만료일을 날짜로 지정(하위 호환)
- 예) expires: Mon, 01 Jan 1990 00:00:00 GMT
- HTTP 1.0부터 사용
- 지금은 더 유연한 Cache-Control: max-age 사용을 권장
- Cache-Control: max-age와 함께 사용하면 expires는 무시됨



### 검증 헤더와 조건부 요청

- 캐시 데이터와 서버 데이터가 같은지 검증하는 데이터

#### Last-Modified

- 서버에 있는 데이터의 마지막 수정 날짜를 담고 있다(GMT 시간 사용)
- 예) Last-Modified: Wed, 21 Oct 2015 07:28:00 GMT

#### ETag

- 캐시 데이터에 임의의 고유한 버전 이름을 달아두고, 데이터가 변경되면 이 이름을 바꾸어서 변경(hash를 다시 생성)
- 예) ETag: "v1.0", ETag: "a2jiodwjekjl3"



### 조건부 요청 헤더

- 검증 헤더로 조건에 따른 분기
- 조건이 만족하면 200 OK 응답과 함께 BODY를 포함한 모든 데이터 전송
- 조건이 만족하지 않으면 304 Not Modified 응답, 헤더 데이터만 전송



#### If-Modified-Since

- 주어진 시간이 클라이언트의 Last-Modified의 시간보다 최근인 경우 성공
- 예) If-Modified-Since: Wed, 21 Oct 2015 07:28:00 GMT
- If-Modified-Since ↔ If-Unmodified-Since



#### If-None-Match

- 클라이언트의 ETag 값과 일치하지 않으면 성공
- 예) If-None-Match: "v1.0", If-None-Match: "a2jiodwjekjl3"
- If-None-Match ↔ If-Match



#### Last-Modified와 If-Modified-Since의 단점

- 1초 미만의 단위로 캐시 시간 조정 불가능
- 날짜 기반의 로직을 사용하기 때문에
- 데이터를 수정해서 날짜가 다르지만, 데이터를 수정한 결과가 전과 같은 경우
- 서버에서 별도의 캐리 로직을 관리하고 싶은 경우
  - 예) 스페이스나 주석처럼 크게 영향이 없는 변경에서 캐시를 유지하고 싶은 경우



### 프록시 캐시

- 프록시 서버는 클라이언트가 자신을 통해서 원서버에 간접적으로 접속할 수 있게 해주는 것을 가리킨다.
- 서버와 클라이언트 사이에서 중계기로써 대리로 통신을 수행하는 것을 프록시라고하고, 그 기능을 하는 것을 프록시 서버라고한다.
- 프록시 서버에 요청된 내용들을 캐시를 이용하여 저장해두면, 캐시안에 있는 데이터들을 요구하는 요청에 대해서는 원서버에 접속하여 데이터를 가져올 필요가 없게 됨으로써, 전송 시간을 절약함과 동시에 불필요한 네트워크 접속을 하지않아도 되는 장점을 가진다.
- private 캐시 : 웹 브라우저 같이 로컬에 저장되는 캐시
- public 캐시 : 중간에서 공용으로 사용하는 캐시 서버, 프록시 캐시 서버



#### Cache-Control 기타

- **Cache-Control: public** : 응답이 public 캐시에 저장되어도 됨
- **Cache-Control: private** : 응답이 해당 사용자만을 위한 것임, private 캐시에 저장해야 함(기본 값)
- **Cache-Control: s-maxage** : 프록시 캐시에만 적용되는 max-age
- **Age: 60(HTTP 헤더)** : 오리진 서버에서 응답 후 프록시 캐시 내에 머문 시간(초 단위)
- Cache-Control: s-maxage, Age 이 두 가지는 이런 게 있다는 것 정도만 알아두면 됨.



### 캐시 무효화

- 확실하게 캐시 무효화를 하는 방법
- **Cache-Control: no-cache, no-store, must-revalidate**
- **Pragma: no-cache** → HTTP 1.0 하위 호환을 위해