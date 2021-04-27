# 프록시와 AOP

## 1. 프록시

- 핵심 기능의 실행은 다른 객체에 위임하고 부가적인 기능을 제공하는 객체를 프록시라고 한다.

- 실제 핵심 기능을 실행하는 객체는 대상 객체라고 한다.

- 핵심 기능을 구현하지 않는 대신 여러 객체의 공통으로 적용할 수 있는 기능을 구현한다.

  

## 2. AOP

#### 스프링 AOP : 런타임시 동적으로 프록시 객체를 생성해주는 것.

### 1)pom.xml

```null
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.0.9.RELEASE</version>
        </dependency>
        
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.8.6</version>
        </dependency>
        
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.6</version>
        </dependency>
```





### 1) AOP의 주요 용어

- Aspect : 여러 객체에 공통으로 적용되는 기능
- Advice : 언제 공통 관심 기능을 핵심 로직에 적용할 지를 정의
  - Before Advice : 대상 객체의 메서드 호출 전에 공통 기능을 수행
  - After Returning Advice : 대상 객체의 메서드가 익셉션 없이 실행된 이후에 공통 기능 실행
  - After Throwing Advice : 대상 객체의 메서그를 실행하는 도중 익셉션이 발생한 경우에 공통 기능 실행
  - After Advice : 익셉션 발생 여부 상관없이 대상 객체의 메서드 실행 후 공통 기능 실행
  - Around Advice : 대상 객체의 메서드 실행 전, 후 또는 익셉션 발생 시점에 공통 기능 실행
- Joinpoint : Advice를 적용 가능한 지점을 의미
- Pointcut : Joinpoint의 부분 집합으로 실제 Advice가 적용되는 Joinpoint를 나타냄
- weaving : Advice를 핵심 로직 코드에 적용하는 것을 의미





### 2) AOP 구현

- Aspect로 사용할 클래스에 @Aspect 애노테이션을 붙인다.
- @Pointcut 애노테이션으로 공통 기능을 적용할 Pointcut을 정의한다.
- 공통 기능을 구현한 메서드에 @Around 애노테이션을 적용한다.



- 다음은 예제를 보여준다.

```java
package aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
@Aspect
// @Aspect 
public class ExeTimeAspect {
//publicTarget()메서드를 chap07패키지와 그 하위패키지에 위치한 public 메서드를 Pointcut으로 설정.
@Pointcut("execution(public * chap07..*(..))")
private void publicTarget() {
}
  
@Around("publicTarget()")
//Around Advice이며 publicTarget() 메서드에 정의한 Pointcut에 공통 기능을 적용한다는 것.
//ProceedingJoinPoint 타입의 파라미터는 프록시 대상 객체의 메서드를 호출할 때 사용.
public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
	long start = System.nanoTime();
	try {
		Object result = joinPoint.proceed();//proceed()메서드를 사용해서 실제 대상 객체의 메서드를 호출
		return result;
	} finally {
		long finish = System.nanoTime();
		Signature sig = joinPoint.getSignature();
		System.out.printf("%s.%s(%s) 실행 시간 : %d ns\n",
				joinPoint.getTarget().getClass().getSimpleName(),
				sig.getName(), Arrays.toString(joinPoint.getArgs()),
				(finish - start));
	}
}
```

- 이 ExeTimeAspect를 @EnableAspectAutoProxy 애노테이션을 사용하여 빈으로 등록한다.
- 그러면 

