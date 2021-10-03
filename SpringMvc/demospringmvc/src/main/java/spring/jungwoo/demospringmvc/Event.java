package spring.jungwoo.demospringmvc;

import lombok.*;

import java.time.LocalDateTime;

//POJO 객체
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Event {

    private String name;

    private int limitOfEnrollment;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

}
