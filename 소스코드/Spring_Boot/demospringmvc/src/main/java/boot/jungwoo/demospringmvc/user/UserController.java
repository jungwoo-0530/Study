package boot.jungwoo.demospringmvc.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//핸들러
public class UserController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
