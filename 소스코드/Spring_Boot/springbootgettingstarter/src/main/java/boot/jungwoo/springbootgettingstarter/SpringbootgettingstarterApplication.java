package boot.jungwoo.springbootgettingstarter;

import boot.jungwoo.Holoman;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootgettingstarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootgettingstarterApplication.class, args);
    }

//    @Bean
//    public Holoman holoman(){
//        Holoman holoman = new Holoman();
//        holoman.setName("kim");
//        holoman.setHowLong(10);
//        return holoman;
//    }
// 버전업 되면서 빈 오버라이딩이 허용되지 않는다. 그러므로 오버라이딩(재정의)을 하고 싶다면
// setting spring.main.allow-bean-definition-overriding=true
// starter에서 @ConditionalOnMissingBean을 설정했기에 이 빈이 등록된다.

    // 굳이 빈을 등록해서 사용해야하나? Application.properties에 값들을 등록해서 사용.


}
