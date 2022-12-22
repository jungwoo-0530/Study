# Nested Class(중첩 클래스)

- 기본적으로 작성하는 Class(Outer Class)는 Static Class이다.
  - 그렇기 때문에, 바로 Class 생성이 가능하다(Class 참조 값을 알 수 있다.)
- Static Class에만 바로 접근이 가능하다는 의미이지, 해당 Class의 멤버변수, 멤버함수에 접근이 가능하다는 의미가 이니다.

## 중첩클래스란?

- 중첩 클래스란 흔히 멤버 클래스로도 알려져있다.

- 클라스 안에 멤버로 정의된 다른 클래스

- ```java
  class OuterClass {
      ...
      class NestedClass {
          ...
      }
  }
  ```

  

- 중첩클래스는 자신을 감싸고 있는 클래스에서만 쓰여야한다.

### 중첩 클래스의 종류

- Static
  - Static Nested Class(정적 중첩 클래스)
- Non-Static : Inner Class(내부클래스)
  - Non-static Nested Class(비정적 중첩 클래스)
  - Anonymous Class(익명 클래스)
  - Local Class(지역 클래스)





#### Inner Class(내부 클래스, Instance Inner Class)

```java
public class OuterClass {
	...
    class InnerClass {
        ...
    }
}
```

- InnerClass는 OuterClass에 선언된 모든 멤버에 접근할 수 있다. private로 선언된 멤버에도 접근할 수 있음.

- 내부 클래스의 객체 생성 방법

  - ```java
    OuterClass outerClass = new OuterClass();
    OuterClass.InnerClass innerClass = OuterClass.new InnerClass();
    ```

    

- 

#### Local Class(지역 클래스, Local Inner Class)

- Block에 정의된 클래스

- ```java
  public class LocalClassExample {
      ...
      public void methodExample() {
          ...
          class LocalClass {
              ...
          }
      }
  ```

- Local Class는 자신을 감싸고 있는 block의 모든 멤버에 접근할 수 있다.





#### Anonymous Class(익명 클래스)

- 이름이 없는 지역 클래스와 같다.
- 선언과 동시에 초기화가 이루어진다.









#### Static Nested Class(정적 중첩 클래스, Static Inner Class)

- static 키워드와 함께 선언된 정적 중첩 클래스

- ```java
  class OuterClass {
      ...
      static class StaticNestedClass {
          ...
      }
  }
  ```

- 자신을 감싸고 있는 클래스의 멤버에 접근할 수 없습니다.

- 자신을 감싸고 있는 클래스의 static 멤버에만 접근할 수 있다.

- static이기 때문에, JVM 클래스 로딩 매커니즘에 의해 클래스 로딩 시점에 한 번만 호출됩니다.







------------------

보통 Inner Class는 생성자에서 생성함

```java
Class OutClass{
  
  private InClass inClass;
	
  public OutClass(){
    this.inclass = new InClass();
  }
  
  class InClass{
    
    
  }
}
```

