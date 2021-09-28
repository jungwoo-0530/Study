package spring.jungwoo.proxyaop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
//해야할 일(Advice)과 어디에 적용해야하는지(Point cut)을 정의해야한다.
public class PerfAspect {

    //@Around("execution(* spring.jungwoo..*.EventService.*(..))")//Point cut, 이 밑 부분이 Advice
    //위에는 delete까지 적용되므로 PerfLogging을 사용하여 delete는 적용안되게
    @Around("@annotation(PerfLogging)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }
}
