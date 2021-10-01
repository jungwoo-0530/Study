package spring.jungwoo.proxyaop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD )
@Retention(RetentionPolicy.CLASS)//이 에노테이션은 이 에노테이션 정보를 얼마나 유지할것인가. 기본값이 클래스이므로 안해도된다.
public @interface PerfLogging {
}
