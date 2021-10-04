package mvc.jungwoo.demojsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoJspApplication //독립적인 War파일로 실행할때는 이 class를 사용
{

    public static void main(String[] args) {
        SpringApplication.run(DemoJspApplication.class, args);
    }

}
