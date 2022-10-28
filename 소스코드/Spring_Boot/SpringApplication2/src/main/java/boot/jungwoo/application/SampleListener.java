package boot.jungwoo.application;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class SampleListener implements ApplicationRunner
{
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("args로 들어온 값 확인.");
        System.out.println("jungwoo: " + args.containsOption("jungwoo"));
    }


}
