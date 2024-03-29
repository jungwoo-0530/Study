# MVC

### 1. MVC란?

M: 모델
 V: 뷰
 C: 컨트롤러

- 모델: 평범한 자바 객체 POJO 

- 뷰: HTML. JSP, 타임리프, ... 

- 컨트롤러: 스프링 @MVC

  -------------------------------------------------------------------

- 모델: 도메인 객체 또는 DTO로 화면에 전달할 또는 화면에서 전달 받은 데이터를 담고 있는 객체. 

- 뷰: 데이터를 보여주는 역할. 다양한 형태로 보여줄 수 있다. HTML, JSON, XML, ... 

- 컨트롤러: 사용자 입력을 받아 모델 객체의 데이터를 변경하거나, 모델 객체를 뷰에 전달하는 역할. 
  - 입력값 검증
  - 입력 받은 데이터로 모델 객체 변경 
  - 변경된 모델 객체를 뷰에 전달

### 2. MVC 패턴의 장점

 ● 동시 다발적(Simultaneous) 개발 - 백엔드 개발자와 프론트엔드 개발자가 독립적으로

개발을 진행할 수 있다.
 ● 높은 결합도 - 논리적으로 관련있는 기능을 하나의 컨트롤러로 묶거나, 특정 모델과

관련있는 뷰를 그룹화 할 수 있다.
 ● 낮은 의존도 - 뷰, 모델, 컨트롤러는 각각 독립적이다.
 ● 개발 용이성 - 책임이 구분되어 있어 코드 수정하는 것이 편하다. ● 한 모델에 대한 여러 형태의 뷰를 가질 수 있다.

### 3. MVC 패턴의 단점

 ● 코드 네비게이션 복잡함
 ● 코드 일관성 유지에 노력이 필요함 ● 높은 학습 곡선





### 4. 간략한 MVC

주 설명은 주석처리에서 설명한다.

의존성에 Web, Lombok, Thymeleaf를 추가하여 프로젝트 생성.

- #### 객체

```java
//POJO 객체, 모델에 해당.
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Event {

    private String name;

    private int limitOfEnrollment;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

}
```



- #### 컨트롤러

```java
package spring.jungwoo.demospringmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//컨트롤러 역할을 하는 클래스.
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/events")//웹브라우저에서 요청이 들어오면 맵핑
    public String events(Model model)//Model을 맵으로 생각, 우리가 보여줄 것을 담으면 된다.
    {

        model.addAttribute("events", eventService.getEvents());//model에 event들 담기.
         return "events";//리턴해주는 것은 view의 이름. 기본적으로 리소스의 템플릿에서 찾게된다.
    }
}

```



- ##### 모델에 넣을 event를 만든 것.

```java
package spring.jungwoo.demospringmvc;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    public List<Event> getEvents(){
        Event event1 = Event.builder()
                .name("스프링 웹 MVC 스터디 1차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2021, 8,10, 10, 0 ))
                .endDateTime(LocalDateTime.of(2050, 1, 1, 10, 0))
                .build();

        Event event2 = Event.builder()
                .name("스프링 웹 MVC 스터디 2차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2050, 8,10, 10, 0 ))
                .endDateTime(LocalDateTime.of(2090, 1, 1, 10, 0))
                .build();

        return List.of(event1, event2);
    }


}
```

- #### 뷰

  - 뷰는 기본적으로 ./resource/templates에 만든다.
  - HTML, XML ...등등으로 만들 수 있다.

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"> <!-- 네임스페이스(xmlns)를 추가하겠다. 어디에 정의가 되어있느냐 http~ -->
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>이벤트 목록</h1>
    <table>
        <tr>
            <th>이름</th>
            <th>참가 인원</th>
            <th>시작</th>
            <th>종료</th>
        </tr>
        <tr th:each="event: ${events}">
            <td th:text="${event.name}">이벤트 이름</td>
            <td th:text="${event.limitOfEnrollment}">100</td>
            <td th:text="${event.startDateTime}">2021년 8월 10일 오전 10시</td>
            <td th:text="${event.endDateTime}">2050년 1월 1일 오전 10시</td>
        </tr>
    </table>
</body>
</html>	
```



<img src="../img/image-20211003035659845.png" alt="image-20211003035659845" style="width:45%;" />

그 결과 잘 나오는 것을 알 수 있다.