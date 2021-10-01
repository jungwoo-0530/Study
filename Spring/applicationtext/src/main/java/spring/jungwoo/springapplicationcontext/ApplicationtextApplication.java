package spring.jungwoo.springapplicationcontext;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;


@SpringBootApplication//스프링 부트에서는 이 에노테이션에 이미 @Componentscan이 들어 있으므로 ApplicaionConfig가 필요가 없다.
                      //그러나 spring은 들어있지 않으므로 필요하다.
public class ApplicationtextApplication {

    public static void main(String[] args)
    {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//
//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        System.out.println(Arrays.toString(beanDefinitionNames));
//        BookService bookService = (BookService) context.getBean("bookService");
//        System.out.println(bookService.bookRepository != null); // true가 나오면 의존성 주입이 되었다는 것.
    }



}
