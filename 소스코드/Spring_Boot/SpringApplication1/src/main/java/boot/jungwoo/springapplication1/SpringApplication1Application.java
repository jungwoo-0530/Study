package boot.jungwoo.springapplication1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApplication1Application {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SpringApplication1Application.class);
        app.run(args);

    }

}
