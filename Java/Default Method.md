# Interface의 Default Methods

- Java8에서 인터페이스에 디폴트 메소드가 추가.
- 원래 인터페이스는 메소드 정의만 할 수 있었다. 구현은 못했다.
- 디폴트 메소드가 추가되면서 구현도 인터페이스에 추가할 수 있다.
- 접근제어자 Default랑 다른 것.





## Default Methods 정의 방법

```java
public interface Vehicle {
    public default void doSomething(int n) {
        System.out.println("doSomething(Vehicle)");
    }
}
```







## 클래스가 인터페이스를 implements할 때

- 인터페이스의 디폴트 메소드가 클래스에 자동으로 구현.

- ```java
  public interface Vehicle {
      public default void doSomething(int n) {
          System.out.println("doSomething(Vehicle)");
      }
  }
  
  public static class Car implements Vehicle {
  }
  
  public static void main(String args[]) {
      Car car = new Car();
      car.doSomething(10);
  }
  // 실행 결과
  // doSomething(Vehicle)
  ```

  

## (다중상속개념) 클래스가 두개의 인터페이스를 implements했을 때

- 인터페이스도 아니고 동일한 메소드 이름(Signature)으로 디폴트 메소드를 구현한 인터페이스입니다.

- 개념적으로 다중상속이기 때문에 컴파일러는 **어떤 인터페이스의 메소드를 상속받아야 할 지 헷갈릴 수 있습니다.** 결과를 먼저 말씀드리면 컴파일 에러가 발생합니다.

- ```java
  public interface Vehicle {
      public default void doSomething(int n) {
          System.out.println("doSomething(Vehicle)");
      }
  }
  
  public interface Movable {
      public default void doSomething(int n) {
          System.out.println("doSomething(Movable)");
      }
  }
  
  public class Car implements Vehicle, Movable {
  }
  
  //////////error 발생
  ```

- 해결하려면 디폴트 메소드를 오버라이드해야함

- ```java
  public interface Vehicle {
      public default void doSomething(int n) {
          System.out.println("doSomething(Vehicle)");
      }
  }
  
  public interface Movable {
      public default void doSomething(int n) {
          System.out.println("doSomething(Movable)");
      }
  }
  
  public static class Car implements Vehicle, Movable {
      @Override
      public void doSomething(int n) {
          Vehicle.super.doSomething(n);
          Movable.super.doSomething(n);
          System.out.println("doSomething(Car)");
      }
  }
  
  public static void main(String args[]) {
      Car car = new Car();
      car.doSomething(10);
  }
  
  // 실행 결과
  // doSomething(Vehicle)
  // doSomething(Movable)
  // doSomething(Car)
  ```





## (다중상속개념) 클래스가 extends와 implements 했을 때

- 컴파일러가 충돌이 발생할 때, extends한 클래스의 우선순위가 더 높아, 이 클래스의 디폴트 메소드를 상속

- ```java
  public interface Vehicle {
      public default void doSomething(int n) {
          System.out.println("doSomething(Vehicle)");
      }
  }
  
  public interface Movable {
      public default void doSomething(int n) {
          System.out.println("doSomething(Movable)");
      }
  }
  
  public static class Car implements Vehicle {
      @Override
      public void doSomething(int n) {
          System.out.println("doSomething(Car)");
      }
  }
  
  public static class MovableCar extends Car implements Movable {
  }
  
  public static void main(String args[]) {
      MovableCar car = new MovableCar();
      car.doSomething(10);
  }
  
  // 실행 결과
  // doSomething(Car)
  ```

  