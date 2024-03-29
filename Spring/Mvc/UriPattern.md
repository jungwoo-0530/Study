# URI 패턴

#### @PathVariable

- 요청 URI 패턴의 일부를 핸들러 메소드 아규먼트로 받는 방법. 
- 타입 변환 지원.
- (기본)값이 반드시 있어야 한다.
- Optional 지원.

#### @MatrixVariable

- 요청 URI 패턴에서 키/값 쌍의 데이터를 메소드 아규먼트로 받는 방법
- 타입 변환 지원.
- (기본)값이 반드시 있어야 한다.
- Optional 지원.
- 이 기능은 기본적으로 비활성화 되어 있음. 활성화 하려면 다음과 같이 설정해야 함.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
  
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) { 
    UrlPathHelper urlPathHelper = new UrlPathHelper();
    urlPathHelper.setRemoveSemicolonContent(false);
    configurer.setUrlPathHelper(urlPathHelper);
} }
```



#### 참고: 

- https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-typeconversion 
- https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-matrix-variables