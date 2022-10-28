package boot.jungwoo.springbootgettingstarter;

import boot.jungwoo.Holoman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class HolomanRunner implements ApplicationRunner
{
    @Autowired
    Holoman holoman;//이 프로젝트에서는 어디에도 홀로맨을 정의하지 않지만 내가 만든 boot.jungwoo.Holoman starter에 있기 떄문에 사용가능

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(holoman);
    }
}
