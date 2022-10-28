package mvc.jungwoo.demojsp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer //tomcat이나 서블릿 엔진에 배포를 하는 형태를
                                                                        // 사용할때는 이 클래스를 사용한다
{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoJspApplication.class);
    }

}
