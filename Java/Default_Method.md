# Default Method



Default라는 키워드는 두가지 용도로 사용

1. 접근제어자 Default

   1. private -> default -> protected -> public

   2. ```java
      package house;
      
      public class Apartment{
        String name = "한솔"; // default 접근제어자. 생략가능.
      }
      ```

      

2. 인터페이스에서의 Defult

   1. 원래 인터페이스에서는 실제 로직을 구현할 수 없음

   2. 이러한 룰을 깬 것이 Default Method

   3. 접근제어자 default와는 다르게 생략할 수 없음

   4. ```java
      interface MyInterface{
        default void printHello(){
          System.out.println("Hello");
        }
      }
      ```

   5. Default Method 사용 이유

      1. 하위 호환성을 위해서
      2. 문제점 : 하나의 인터페이스에 구현체가 수십개 수백개인 상태에서 인터페이스에 새로운 Method를 구현한다면 모든 구현체 클래스에 새로 추가된 메소드를 구현해야한다. 
      3. 해결 : Default method를 사용함으로 기존의 구현체들을 건들지 않고 인터페이스 하나를 건들여서 모든 구현체에 영향을 끼칠 수 있다.