# SOLID

- SOLID원칙은 소프트웨어를 설계함에 있어 이해하기 쉽고, 유연하며, 유지보수 및 확장이 편하다는 장점

1. SRP(Single responsibility principle) : 단일 책임 원칙
2. OCP(Open-cloesd principle) : 개방-폐쇄 원칙
3. LSP(Liskov substitution principle) : 리스코프 치환 원칙
4. ISP(Interface segregation principle) : 인터페이스 분리 원칙
5. DIP(Dependency inversion principle) : 의존관계 역전 원칙







## SRP - 단일 책임 원칙

- **한 클래스는 하나의 책임만 가져야 한다.**
- 모든 클래스는 각각 하나의 책임만 가져야 하며, 수정할 이유는 단 한 가지여야 한다.
- 즉, 클래스는 **그 책임을 완전히 캡슐화**해야 함을 말한다.
- 예를 들어, 결제 클래스가 있다치면 이 클래스는 오직 결제 기능만을 책임지고 이 클래스를 수정해야 한다면 결제에 관련된 문제일 뿐일 것이다.



### SRP 예

```java
class 강아지 {
    final static Boolean 수컷 = true;
    final static Boolean 암컷 = false;
    Boolean 성별;

    void 소변보다() {
        if (this.성별 == 수컷) {
            // 한쪽 다리를 들고 소변을 보다.
        } else {
            // 뒷다리 두 개를 굽혀 앉은 자세로 소변을 본다.
        }
    }   
}
```

- 위의 코드는 SRP를 지키지 못한 경우

```java
abstract class 강아지 {
    abstract void 소변보다();
}

class 수컷강아지 extends 강아지 {
    void 소변보다() {
        // 한쪽 다리를 들고 소변을 본다.
    }
}

class 암컷강아지 extends 강아지 {
    void 소변보다() {
        // 뒷다리 두 개로 앉은 자세로 소변을 본다.
    }
}
```

- 위의 코드는 SRP를 지킨 것.

## OCP - 개방-폐쇄 원칙

- **확장에는 열려있고 변경에는 닫혀 있어야 한다.**
- 소프트웨어의 구성요소(컴포넌트, 클래스, 모듈, 함수)는 확장에는 열려 있어야 하지만 변경에는 폐쇄적이어야 함을 의미한다.
- 즉, **기존 코드를 변경하지 않으면서, 기능을 추가할 수 있도록 설계가 되는 원칙**
- 예를 들어, 캐릭터 하나를 생성한다고 할 때, 각 캐릭터마다 움직임이 다를 경우,
- 움직임 패턴 구현을 하위 클래스에 맡긴다면 캐릭터 클래스의 수정은 필요없고(closed)
- 움직임 패턴만 재정의 하면 된다.(Open)
- **개방 폐쇄 원칙을 적용하기 위한 중요 메커니즘은 추상화와 다형성이다.**



### 개방 폐쇄 원칙의 아주 좋은 예

- JDBC가 개방 폐쇄 원칙의 가장 좋은 예.
- 데이터 베이스가 MySQL에서 오라클로 바뀌더라도 Connection을 설정한 부분만 변경해주면 된다.
- 즉, JAVA 어플리케이션은 데이터베이스라고 하는 주변의 변화에 닫혀 있는 것. 데이터베이스를 교체한다는 것은 데이터베이스가 자신의 확장에는 열려 있다는 것.



## LSP - 리스코프 치환 원칙

- **서브 타입은 언제나 자신의 기반 타입으로 변경할 수 있어야 한다.**
- 상위 타입은 항상 하위 타입으로 대체할 수 있어야 함을 의미한다.
- 즉, **부모 클래스가 들어갈 자리에 자식 클래스를 넣어도 역할을 하는데 문제가 없어야 한다는 의미**
- <img src="img/객체지향의 원리SOLID/image-20221115103104705.png" alt="image-20221115103104705" style="zoom:50%;" />
- **리스코프 치환 원칙은 다형성과 확장성을 극대화하며, 개방-폐쇄 원칙을 구성한다.**





## ISP - 인터페이스 분리 원칙

- **하나의 일반적인 인터페이스보다 여러 개의 구체적인 인터페이스가 낫다.**
- 각 열할에 맞게 인터페이스를 분리하는 것.
- 인터페이스 내에 메소드는 최소한 일수록 좋다.
- 즉, **최소한의 기능만 제공하면서 하나의 역할에 집중하라는 뜻**
- 단일 책임 원칙과 인터페이스 분리 원칙은 같은 문제에 대한 두가지 다른 해결책이라고 볼 수 있다.
- 일반적으로 ISP보다 SRP할 것을 권장한다.





## DIP - 의존관계 역전 원칙

- **구체적인 것이 추상화된 것에 의존해야 한다. 자주 변경되는 구체 클래스에 의존하지마라**
- 변화가 거의 없는 것에 의존하라는 것.
- 즉, **구체적인 클래스보다 상위 클래스, 인터페이스, 추상 클래스와 같이 변하지 않을 가능성이 높은 클래스와 관계를 맺으라는 것**
- DIP 원칙을 따르는 가장 인기있는 방법은 의존성 주입(DI)을 활용한 것이다.