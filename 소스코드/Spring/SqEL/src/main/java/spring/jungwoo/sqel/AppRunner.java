package spring.jungwoo.sqel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {
    //#은 표현식 $은 프로퍼티
    @Value("#{1 + 1}")
    int value;

    @Value("#{'hello '+'world'}")
    String greeting;

    @Value("#{1 eq q}")
    boolean trueOrFalse;

    @Value("${my.value}")
    int myValue;

    @Value("#{${my.value} eq 100}")//이렇게도 가능 반대는 불가능.
    int myValue1;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("--------------------------");
        System.out.println(value);
        System.out.println(greeting);
        System.out.println(trueOrFalse);
        System.out.println(myValue);
        System.out.println(myValue1);
    }
}
