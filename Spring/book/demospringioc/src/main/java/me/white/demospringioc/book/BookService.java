package me.white.demospringioc.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;


@Service//bean
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Book save(Book book){
        book.setCreated(new Date());
        book.setBookStatus(BookStatus.DRAFT);
        return bookRepository.save(book);
    }

    @PostConstruct//라이프사이클 인터페이스 : 빈을 등록할때 나는 추가적인 작업을 하고싶을때
    public void postConstruct(){
        System.out.println("=================");
        System.out.println("Hello");
    }

}

