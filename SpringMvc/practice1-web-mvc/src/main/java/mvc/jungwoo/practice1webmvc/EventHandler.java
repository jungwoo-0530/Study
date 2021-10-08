package mvc.jungwoo.practice1webmvc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventHandler {

    @GetMapping("/events")
    @ResponseBody
    public String eventGet(){
        return "event";
    }

    @GetMapping("/events/{id}")
    @ResponseBody
    public String eventGet(@PathVariable String id){
        return "event";
    }

    @PostMapping(value = "/events",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    
    @ResponseBody
    public String eventCreate(){
        return "event";
    }

    @DeleteMapping("/events/{id}")
    @ResponseBody
    public String eventDelete(@PathVariable String id){
        return "event";
    }

    @PutMapping (value = "/events/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String eventUpdate(@PathVariable String id){
        return "event";
    }



}
