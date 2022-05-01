# Dispatch



​	





1. Static Dispatch

```java
package com.example.dispatch;


public class Dispatch {

  static class Service1{
    void run() {
      System.out.println("run1()");
    }
  }

  static class Service2{
    void run() {
      System.out.println("run2()");
    }
  }


  public static void main(String[] args) {

    new Service1().run();
    new Service2().run();

  }


}
```





2. Dynamic Dispatch

```java
package com.example.dispatch;


public class Dispatch {

  static abstract class Service{
    abstract void run();
  }
  
  static class MyService1 extends Service{
    @Override
    void run() {
      System.out.println("run1");
    }
  }
  static class MyService2 extends Service{
    @Override
    void run() {
      System.out.println("run2");
    }
  }
  public static void main(String[] args) {
    
    Service svc = new MyService1(); //svc의 receiver parameter가 Myservice1 오브젝트로
    
    svc.run(); // receiver parameter
    
  }

}

```

위와 같은 상황은 변수 `svc`에 `Myservice1`이라는 구체적인 클래스를 정의했다.

그러나 `svc.run();`에 실행되는 것은 dynamic dispatch이 일어난다.

왜냐하면 static dispatch인지 dynamic dispatch인지 구별하는 것은 run타임 시점에 아는 것.

위 `svc.run()`은 **런타임시** svc 변수에 할당되어있는 오브젝트가 무엇인가 확인하고 그것에 의해서 결정되는 것.

더 자세하게 보면 `svc.run()`이라는 메소드 호출 과정에 첫번째로 들어가있는 것 중에 하나가 `receiver parameter`이다.

모든 클래스에 있는 `this`가 receiver parameter로 넘어간다.

즉, 위에서는 `svc.run()`의 `receiver parameter`가 `Myservice1`의 `tihs`를 받는 것.