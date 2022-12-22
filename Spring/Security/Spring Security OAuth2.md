# Spring Security OAuth2





## 카카오 Oauth(오오쓰)2





<img src="img/Spring Security OAuth2/Screenshot of Typora (2022-12-08 8-13-01 PM).png" alt="Screenshot of Typora (2022-12-08 8-13-01 PM)" style="zoom:50%;" />

<img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-08 8-12-53 PM).png" alt="Screenshot of Safari (2022-12-08 8-12-53 PM)" style="zoom:50%;" />

<img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-08 8-26-12 PM).png" alt="Screenshot of Safari (2022-12-08 8-26-12 PM)" style="zoom:50%;" />

- 로그인 시 Redirect URI를 통해 우리가 얻으려는 Authorization code(인가 코드)를 반환받는다.







```yaml
spring:
	security:
    oauth2.client:
      registration:
        google:
          clientId: '{구글 client-id}'
          clientSecret: '{구글 client-secret}'
          scope:
            - email
            - profile
        facebook:
          clientId: '{페이스북 client-id}'
          clientSecret: '{페이스북 client-secret}'
          scope:
            - email
            - public_profile
        naver:
          clientId: '{네이버 client-id}'
          clientSecret: '{네이버 client-secret}'
          clientAuthenticationMethod: post
          authorizationGrantType: authorization_code
          redirectUri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          scope:
            - nickname
            - email
            - profile_image
          clientName: Naver
         kakao:
          clientId: '{카카오 client-id}' #Rest API 키
          clientSecret: '{카카오 client-secret}'
          clientAuthenticationMethod: post
          authorizationGrantType: authorization_code
          redirectUri: http://localhost:8080/login/oauth2/code/kakao #(1)
          scope:
            - profile_nickname
            - profile_image
            - account_email
          clientName: Kakao
      # Provider 설정
      provider:
        naver:
          authorizationUri: https://nid.naver.com/oauth2.0/authorize
          tokenUri: https://nid.naver.com/oauth2.0/token
          userInfoUri: https://openapi.naver.com/v1/nid/me
          userNameAttribute: response
        kakao:
          authorizationUri: https://kauth.kakao.com/oauth/authorize #(2)Authorization Code 요청 보낼 때 사용하는 Uri
          tokenUri: https://kauth.kakao.com/oauth/token #(3)access token 요청 보낼 때 사용하는 Uri
          userInfoUri: https://kapi.kakao.com/v2/user/me
          userNameAttribute: id
```

- 여기서는 기본 폼을 보여주므로 clientId, clientSecret, redirectUri는 기본 폼을 보여준다.
- 카카오만 정상적으로 넣은 것을 보여줌
- 
- `clientId` : Resource Server에서 발급해주는 ID, 웹사이트에 구글, 네이버가 할당해주는 ID
- `clientSecret` : Resource Server에서 발급해주는 PW, 웹사이트 구글, 네이버에서 할당해주는 PW
- `redirectUri` : Client 측에서 등록하는 Url, **로그인을 실행할 Url**, 만약 이 Uri로부터 인증을 요구하는 것이 아니라면, Resource Server에서 무시한다.
  - 카카오의 경우 : <img src="img/Spring Security OAuth2/Screenshot of Typora (2022-12-08 8-18-37 PM).png" alt="Screenshot of Typora (2022-12-08 8-18-37 PM)" style="zoom:50%;" />
- 구글 혹은 페이스북은 `CommonOauth2Provider`에서 기본적인 정보를 제공하므로 `Provider, clientAuthenticationMethod, authorizationGrantType, redirectUri`를 작성하지 않아도 된다. 





1. 우리가 개발한 서버를 Resource Server(구글, 네이버, ...)에 등록
   - `clientId, clientSecret`을 얻고 `redirectUrl` 설정
2. 



- AuthenticationEntryPoint vs AccessDeniedHandler
  - AuthenticationEntryPoint
    - 인증이 되지않은 유저가 요청을 했을 때 동작
  - AccessDeniedHandler
    - 서버에 요청을 할 때, 액세스가 가능한지 권한을 체크 후 액세스 할 수 없는 요청을 했을 시 동작된다.



필요한것

- Member Entity

- Security Config 설정

- 사용하는 인터페이스, 클래스

  - OAuth2User

  - OAuth2UserService, DefaultOAuth2UserService

  - OncePerRequestFilter

  - AccessDeniedHandler

  - SavedRequestAwareAuthenticationSuccessHandler

  - Jwts, Claims

  - OAuth2User, UserDetails, User, UserPrincipal, 

  - OAuth2UserInfo

    



```java

```

---------------------------------

- https://lemontia.tistory.com/1020

- redirectUrl : `http://localhost:3000/login/oauth2/code/kakao`

- 프론트에서 authorizationUri에 response_type, client_id, redirect_url을 붙여서 인가코드를 프론트에서 얻는다.

  - ```js
    const callKakaoLoginHandler = () =>{
      router.push({
        pathname: "https://kauth.kakao.com/oauth/authorize",
        query: {
          "response_type": "code",
          "client_id": "a",
          "redirect_uri": "http://localhost:3000/login/oauth2/code/kakao"
        }
      })
    }
    ```

    

- 인가코드를 받은 프론트는 redirect url callback하여 백엔드로 인가코드와 함께 백엔드로 전송한다.

  - ```js
    const ...{
      const url = "/oauth2/authorization/kakao"
      const code = "인가코드"
      axios.get(url, {params: code})...
    }
    ```

    

- 인가 코드를 받은 백엔드는 RestTemplate을 이용하여 `https://kauth.kakao.com/oauth/token`에 params들 grant_type, client_id, redirect_uri, code(인가코드)를 붙여서 보내고 토큰을 받는다.

- 



1. 사용자가 http://localhost:3000/login 페이지에서 원하는 로그인 서비스(구글, 카카오 ..)등을 선택한다

   - 선택하면 이 링크로 가게 만든다.
   - <img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-08 10-46-30 PM).png" alt="Screenshot of Safari (2022-12-08 10-46-30 PM)" style="zoom:50%;" />
   - 위의 링크를 클릭시 로그인 창이 보여진다.

2. 리액트에서 다음과 같은 url로 요청하게된다.

   - ```http
     http://localhost:8080/oauth2/authorization/kakao?redirect_uri=@@@
     ```

3. 스프링부트에서 











-------------------------------------------------------------------

- `http://localhost:8080/oauth2/authorization/google` : 구글 로그인 페이지





---------------

프론트에서 Authorization code와 Access Token을 모두 받는다

- Access Token을 받은 후 백엔드로 보낼 때 헤더에 붙여서 보낸다.
- 백엔드는 검증을 위해서 Resource server로 검증을 받는다.





-----------------------





<img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 10-15-27 AM).png" alt="Screenshot of Safari (2022-12-09 10-15-27 AM)" style="zoom:50%;" />



<img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 10-15-49 AM).png" alt="Screenshot of Safari (2022-12-09 10-15-49 AM)" style="zoom:50%;" />



프론트 로그인 버튼 누르고 구글 로그인을 누르면

- http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhsot:3000/oauth2/redirect
  - redirect_uri는 프론트를 사용하여 나중에 인증이 완료되면 저 url에 access token query를 붙여서 보낸다, 여기서의 redirect_uri는 OAuth2의 redirectUri와는 다르다.
  - http://localhsot:3000/oauth2/redirect?access_token=sdfdskjf323232
- <img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 10-25-49 AM).png" alt="Screenshot of Safari (2022-12-09 10-25-49 AM)" style="zoom:50%;" />\
- <img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 10-18-56 AM).png" alt="Screenshot of Safari (2022-12-09 10-18-56 AM)" style="zoom:50%;" />
- <img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 10-19-40 AM).png" alt="Screenshot of Safari (2022-12-09 10-19-40 AM)" style="zoom:50%;" />
- <img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 10-19-45 AM).png" alt="Screenshot of Safari (2022-12-09 10-19-45 AM)" style="zoom:50%;" />



- <img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 11-17-16 AM).png" alt="Screenshot of Safari (2022-12-09 11-17-16 AM)" style="zoom:50%;" />

- ```java
  .authorizationEndpoint().baseUri("/oauth2/authorize")
  ```

  - 위 베이스 유알야이 접근 시 oauth 로그인 요청한다고 생각

- ```java
  .redirectionEndpoint()
                  .baseUri("/oauth2/callback/**")
  ```

  - 리다이렉트 uri를 설정.

- 

  ```java
   .userService(customOAuth2UserService)
   .and()
   .successHandler(oAuth2SuccessHandler)
  ```

  - userService는 oauth2로 유저정보를 받아오게되면 그 유저 정보를 oauth2 인증 유저 객체로 등록하게끔 구현된 커스텀 클래스
  - successHandler는 정상적으로 유저가 잘 인증되어 등록되면 실행되는 클래스. 여기서 프론트로 access token을 전달하면된다.



--------------------------

https://jyami.tistory.com/121

<img src="img/Spring Security OAuth2/Screenshot of Safari (2022-12-09 11-20-39 AM).png" alt="Screenshot of Safari (2022-12-09 11-20-39 AM)" style="zoom:50%;" />

\1. OAuth2 login 플로우는 맨처음 frontend client 에서 엔드포인트에 서 요청을 보내면서 시작된다.

```
http://localhost:8080/oauth2/authorize/{provider}?redirect_uri=<redirect_uri_after_login>
```

- provider : google, facebook, github
- redirect_uri : OAuth2 provider가 성공적으로 인증을 완료했을 때 redirect 할 URI를 지정한다. (OAuth2의 redirectUri 와는 다르다)

\2. endpoint(Spring Boot)로 인증 요청을 받으면, Spring Security의 OAuth2 클라이언트는 user를 provider가 제공하는 AuthorizationUrl(yml에서 설정한)로 redirect 한다.

- ```
  https://kauth.kakao.com/oauth/authorize
  ?client_id=  
      ${kakao.clientID}    //1
  	  &redirect_uri=${kakao에 등록한 redirectUri} // 2
  	  &response_type=code //3
  ```

- Authorization request와 관련된 state는 authorizationRequestRepository 에 저장된다 (Security Config에 정의함)
  provider에서 제공한 AutorizationUrl에서 허용/거부가 정해진다.

- 이때 만약 유저가 앱에 대한 권한을 모두 허용하면 provider는 사용자를 callback url로 redirect한다. (`http://localhost:8080/oauth2/callback/{provider}`) 그리고 이때 사용자 인증코드 (authroization code) 도 함께 갖고있다.

  - ```
    GET
    /oauth2/callback/{provider-id}?code=MLiFGqZjfdubcXUqOexxdN4TKXCICuP1Kp9D5vKQcCwx5AmQPZewX5rDQAXE80ucXsSZZwo9c00AAAF68RgEXQ&state=Vi2q6hfu7YqKwcVxrIaVt1FFzy-qkOMhVzbwf7CaEZU%3D
    ```

- 만약 거부하면 callbackUrl로 똑같이 redirect 하지만 error가 발생한다.

\3. Oauth2 에서의 콜백 결과가 에러이면 Spring Security는 oAuth2AuthenticationFailureHanlder 를 호출한다. (Security Config에 정의함)

\4. Oauth2 에서의 콜백 결과가 성공이고 사용자 인증코드 (authorization code)도 포함하고 있다면 Spring Security는 `access_token`에 대한 authroization code를 교환하고, `customOAuth2UserService` 를 호출한다 (Security Config에 정의함)

- ```
  POST
  https://auth.provider.com/oauth/token
  {
    "grant_type": "authorization_code",
    "code": "MLiFGqZjfdubcXUqOexxdN4TKXCICuP1Kp9D5vKQcCwx5AmQPZewX5rDQAXE80ucXsSZZwo9c00AAAF68RgEXQ",
    "redirect_uri": "http://localhost:8080/oauth2/callback/{provider-id}",
    "client_id": "{client-id}",
    "client_secret": "{client-secret}"
  }
  ```

\5. `customOAuth2UserService` 는 인증된 사용자의 세부사항을 검색한 후에 데이터베이스에 Create를 하거나 동일 Email로 Update 하는 로직을 작성한다.

\6. 마지막으로 `oAuth2AuthenticationSuccessHandler` 이 불리고 그것이 JWT authentication token을 만들고 queryString에서의 redirect_uri로 간다 (1번에서 client가 정의한 ) 이때 JWT token과 함께!





```
.authorizationRequestRepository(cookieAuthorizationRequestRepository())
```

- 여기서 uri로 넘어온 redirect_uri





1. 사용자가 로그인화면에서 소셜 로그인 클릭
2. Authorization Code 획득
3. A





------------------------------------------

주소창에 http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhost:3000/oauth2/redirect를 입력하면

https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?response_type=code&client_id=1057978715351-mfj7j1v8kr6971vvtaf1leoeasherfg4.apps.googleusercontent.com&scope=email%20profile&state=OU14lc4yTZs20bC7IDS0xIYFkZDqOBPjv7MnOajlxeA%3D&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Foauth2%2Fcallback%2Fgoogle&service=lso&o2v=2&flowName=GeneralOAuthFlow로 이동된다.

또한 redirect_uri의 값으로 스프링 부트는 쿠키를 생성한다. 

그 화면은 아래와 같다.

<img src="img/Spring Security OAuth2/Screenshot of Typora (2022-12-09 9-06-29 PM).png" alt="Screenshot of Typora (2022-12-09 9-06-29 PM)" style="zoom:50%;" />







- CommonOauth2Provider enum에 기본적으로 google, github, facebook이 저장되어있다.

```java
public enum CommonOAuth2Provider {
  GOOGLE {
    public ClientRegistration.Builder getBuilder(String registrationId) {
      ClientRegistration.Builder builder = this.getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, "{baseUrl}/{action}/oauth2/code/{registrationId}");
      builder.scope(new String[]{"openid", "profile", "email"});
      builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
      builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
      builder.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs");
      builder.issuerUri("https://accounts.google.com");
      builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
      builder.userNameAttributeName("sub");
      builder.clientName("Google");
      return builder;
    }
  },
  GITHUB {
    public ClientRegistration.Builder getBuilder(String registrationId) {
      ClientRegistration.Builder builder = this.getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, "{baseUrl}/{action}/oauth2/code/{registrationId}");
      builder.scope(new String[]{"read:user"});
      builder.authorizationUri("https://github.com/login/oauth/authorize");
      builder.tokenUri("https://github.com/login/oauth/access_token");
      builder.userInfoUri("https://api.github.com/user");
      builder.userNameAttributeName("id");
      builder.clientName("GitHub");
      return builder;
    }
  },
  FACEBOOK {
    public ClientRegistration.Builder getBuilder(String registrationId) {
      ClientRegistration.Builder builder = this.getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_POST, "{baseUrl}/{action}/oauth2/code/{registrationId}");
      builder.scope(new String[]{"public_profile", "email"});
      builder.authorizationUri("https://www.facebook.com/v2.8/dialog/oauth");
      builder.tokenUri("https://graph.facebook.com/v2.8/oauth/access_token");
      builder.userInfoUri("https://graph.facebook.com/me?fields=id,name,email");
      builder.userNameAttributeName("id");
      builder.clientName("Facebook");
      return builder;
    }
  },
  OKTA {
    public ClientRegistration.Builder getBuilder(String registrationId) {
      ClientRegistration.Builder builder = this.getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, "{baseUrl}/{action}/oauth2/code/{registrationId}");
      builder.scope(new String[]{"openid", "profile", "email"});
      builder.userNameAttributeName("sub");
      builder.clientName("Okta");
      return builder;
    }
  };

  private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}";

  private CommonOAuth2Provider() {
  }

  protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUri) {
    ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
    builder.clientAuthenticationMethod(method);
    builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
    builder.redirectUri(redirectUri);
    return builder;
  }

  public abstract ClientRegistration.Builder getBuilder(String var1);
}
```

- 카카오나 네이버는 아래와 같이 설정을 일일히 해줘야한다.

```yaml
spring:
	security:
    oauth2.client:
      registration:
        naver:
          clientId: '{네이버 client-id}'
          clientSecret: '{네이버 client-secret}'
          clientAuthenticationMethod: post
          authorizationGrantType: authorization_code
          redirectUri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          scope:
            - nickname
            - email
            - profile_image
          clientName: Naver
         kakao:
          clientId: '{카카오 client-id}' #Rest API 키
          clientSecret: '{카카오 client-secret}'
          clientAuthenticationMethod: post
          authorizationGrantType: authorization_code
          redirectUri: http://localhost:8080/login/oauth2/code/kakao #(1)
          scope:
            - profile_nickname
            - profile_image
            - account_email
          clientName: Kakao
      # Provider 설정
      provider:
        naver:
          authorizationUri: https://nid.naver.com/oauth2.0/authorize #(2)
          tokenUri: https://nid.naver.com/oauth2.0/token
          userInfoUri: https://openapi.naver.com/v1/nid/me
          userNameAttribute: response
        kakao:
          authorizationUri: https://kauth.kakao.com/oauth/authorize #(2)Authorization Code 요청 보낼 때 사용하는 Uri
          tokenUri: https://kauth.kakao.com/oauth/token #(3)access token 요청 보낼 때 사용하는 Uri
          userInfoUri: https://kapi.kakao.com/v2/user/me
          userNameAttribute: id
```

1. (1) redirectUri는 OAuth2에서 설정한 redirect Uri다

   

2. ```
   .authorizationEndpoint()
       .baseUri("/oauth2/authorize")
       .authorizationRequestRepository(cookieAuthorizationRequestRepository())
   ```

   - 위와 같이 설정했으면 프론트에서 http://localhost:8080/oauth2/authorize/google...으로 요청을보내면
   - 위에서 캐치해서 리다이렉션시킨다. 리다이렉션은 (2)인 authorizationUri로 리다이렉션.
   - https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?response_type=code&client_id=1057978715351-mfj7j1v8kr6971vvtaf1leoeasherfg4.apps.googleusercontent.com&scope=email%20profile&state=MtdAIPgMrt1YuO3tMLyYjcTu50FfIrOAJPDx_k1F-KI%3D&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Foauth2%2Fcallback%2Fgoogle&service=lso&o2v=2&flowName=GeneralOAuthFlow

3.  <img src="img/Spring Security OAuth2/Screenshot of Typora (2022-12-09 9-59-27 PM).png" alt="Screenshot of Typora (2022-12-09 9-59-27 PM)" style="zoom:50%;" />

   - 2에서 리다이렉션된 화면이다. 여기서 로그인을 하면 우리가 1번에서 설정한 redirect Uri로 인가코드가 온다.

   - 난 redirect Uri를 http://localhost:8080/oauth2/callback/google로 설정하였으니

   - ```java
     GET
     /oauth2/callback/google?code=MLiFGqZjfdubcXUqOexxdN4TKXCICuP1Kp9D5vKQcCwx5AmQPZewX5rDQAXE80ucXsSZZwo9c00AAAF68RgEXQ&state=Vi2q6hfu7YqKwcVxrIaVt1FFzy-qkOMhVzbwf7CaEZU%3D
     ```

   - 이렇게 온다.