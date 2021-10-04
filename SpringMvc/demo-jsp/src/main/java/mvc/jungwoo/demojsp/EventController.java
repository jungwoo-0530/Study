package mvc.jungwoo.demojsp;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

    @GetMapping
    public String getEvents(Model model) {

        Event event1 = new Event();
        event1.setName("스프링 웹 스터디 1차");
        event1.setStarts(LocalDateTime.of(2021, 1, 15, 10, 0 ));

        Event event2 = new Event();
        event1.setName("스프링 웹 스터디 2차");
        event1.setStarts(LocalDateTime.of(2022, 1, 15, 10, 0 ));

        List<Event> events = List.of(event1, event2);

        model.addAttribute("events", events);
        model.addAttribute("message", "hello");
        return "events/list";

    }

}
