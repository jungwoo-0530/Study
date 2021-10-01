package spring.jungwoo.springapplicationcontext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ApplicationtextApplication.class)//자동으로 빈 스캔
public class ApplicationConfig {
//직접 빈 등록
//    @Bean
//    public BookRepository bookRepository() {
//        return new BookRepository();
//    }
//
//    @Bean
//    public BookService bookService() {
//        BookService bookService = new BookService();//의존성 주입
//        bookService.setBookRepository(bookRepository());
//        return bookService;
//    }


}
