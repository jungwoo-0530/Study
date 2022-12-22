# Authentication(인증) vs Authorization(인가)

- Authentication(인증)
  - 인증이 안된 유저는 서버에 요청을 보내도 서버에서 요청에 대한 응답을 하지 않는다.
  - 예) 비 로그인 사용자
- Authorization(인가)
  - 인증은 된 유저지만 해당 리소스에 대한 권한이 없어서 응답을 하지 않는다.
  - 예)로그인 사용자지만 관리자 리소스에 접근할 경우









https://kim-jong-hyun.tistory.com/36

# AccessDeniedHandler 와 AuthenticationEntryPoint

- Spring Security에는 AccessDeniedHandler 인터페이스 와 AuthenticationEntryPoint인터페이스가 존재한다.
- AccessDeniedHandler는 **서버에 요청을 할 때, 액세스가 가능한지 권한을 체크 후 액세스 할 수 없는 요청을 했을시 동작된다.**
- AuthenticationEntryPoint는 인증이 되지 않은 유저가 요청을 했을 때 동작된다.