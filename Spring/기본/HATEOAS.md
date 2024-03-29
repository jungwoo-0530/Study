# Spring HATEOAS

- hateoas를 구현하기에 편리한 제공을 해주는 라이브러리

**H**ypermedia **A**s **T**he **E**ngine **O**f **A**pplication **S**tate

- RestApi를 만들 때, 리소스에 대한 정보를 제공할 때 리소스와 연관된 링크정보들까지 제공을하고 연관된 링크 정보 바탕으로 접근.
  - 서버: 현재 리소스와 연관된 링크 정보를 클라이언트에게 제공한다.
  - 클라이언트: 연관된 링크 정보를 바탕으로 리소스에 접근한다.
- 연관된 링크 정보
  - **Rel**ation
  - **H**ypertext **Ref**erence

- spring-boot-starter-hateoas 의존성 추가

  - ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-hateoas</artifactId>
    </dependency>
    ```

  - 이 의존성을 추가하면 많은 것 중에 두가지로 ObjectMapper, LinkDiscovers를 제공한다.

- https://spring.io/understanding/HATEOAS

- https://spring.io/guides/gs/rest-hateoas/

- https://docs.spring.io/spring-hateoas/docs/current/reference/html/

  

#### ObjectMapper 제공

- 우리가 제공하는 리소스를 json으로 변환할 때 쓰는 인터페이스
- Starter Web만 의존성을 넣더라도 자동으로 사용가능

- spring.jackson.*
  - 커스텀하고 싶을때는 프로퍼티에서 커스텀.
- Jackson2ObjectMapperBuilder

#### LinkDiscovers 제공

- 클라이언트 쪽에서 링크 정보를 Rel 이름으로 찾을때 사용할 수 있는 XPath 확장 클래스

-----------------



```java
@RestController
public class SampleController {

    @GetMapping("/hello")
    public EntityModel<Hello> hello(){
        Hello hello = new Hello();
        hello.setPrefix("Hey,");
        hello.setName("jungwoo");

        EntityModel<Hello> helloResource = new EntityModel<>(hello);
        helloResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());


        return helloResource;
    }
}

```

```java
 EntityModel<Hello> helloResource = new EntityModel<>(hello);
```

강의해서는 Resource를 hateoas에 있는 resource를 import하지만 변경돼서 지금은 EntityModel을 사용해야한다.

- `ResourceSupport` is now `RepresentationModel`
- `Resource` is now `EntityModel`
- `Resources` is now `CollectionModel`
- `PagedResources` is now `PagedModel`

여기서 hateoas에서 제공하는 리소스는 우리가 제공하는 리소스 + 링크 정보이다.

```java
helloResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());

```

SampleController에서 제공하는 hello라는 메소드의 대한 링크를 따서 self라는 릴레이션으로 만들어서 추가를 한것.



```java

@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists());

    }

}
```

테스트를 진행하면

```
MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = [Content-Type:"application/hal+json"]
     Content type = application/hal+json
             Body = {"prefix":"Hey,","name":"jungwoo","_links":{"self":{"href":"http://localhost/hello"}}}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```

위와 같이 우리가 리턴하려던 리스소에 덤으로 링크 정보가 추가된 것을 알 수 있다.

