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
