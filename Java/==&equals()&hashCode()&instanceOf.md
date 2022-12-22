

# ==(동일성 비교)

- ==도 주소값으로 비교한다.
- 원시타입은 ==비교를 통해 값 비교가 가능하다. 그 이유는
  - 원시타입은 같은 값을 가진다면 같은 주소값을 가진다.
    - 원시 타입의 변수는 Stack영역에 저장이 되고 그 값(상수)는 **Runtime Constant Pool**에 저장된다.
    - Stack 변수 선언부는 해당 **Runtime Contant Pool의 주소값을 가지게 되고** 만약 다른 변수도 같은 상수를 저장하고 있다면, 이 다른 변수도 같은 **Runtime Constant Pool의 주소값을 가지게 된다.**
  - 추가적으로 String("" 리터럴, new 는 안됨.)은 원시타입은 아니지만 String Constant Pool에서 값이 같다면 같은 값을 참조하기 때문에 ==비교해도 된다.
- 객체타입은 참조변수의 값을 비교하기 때문에 주소값을 비교하게 된다. 즉, 내가 원하는 결과가 안나온다.

# equals() (동등성 비교)

- equals()는 동등성 비교에 사용하므로 객체 내부의 값을 비교합니다.

- 재정의를 하지 않는 equals()는 ==와 마찬가지로 주소값으로 비교를 한다.

- ```java
  public boolean equals(Object obj) {
          return (this == obj);
  }
  ```

  - Object 클래스에 정의된 것.
  - 즉, 내부에서 == 비교를 한다. 즉, 주소값으로 비교한다.

- String의 equals()

  - ```java
    public boolean equals(Object anObject) {
      if (this == anObject) {
        return true;
      }
      if (anObject instanceof String) {
        String anotherString = (String)anObject;
        int n = value.length;
        if (n == anotherString.value.length) {
          char v1[] = value;
          char v2[] = anotherString.value;
          int i = 0;
          while (n-- != 0) {
            if (v1[i] != v2[i])
              return false;
            i++;
          }
          return true;
        }
      }
      return false;
    }
    ```

  - String의 equals()를 보면 equals를 재정의하였기에 두 String의 길이가 같다면 하나씩 ==을 사용하여 같은 값을 가지는 지 확인한다.

  - 즉, equals는 내부적으로 원시타입 ==를 사용한다.

  - 그러므로 내가 원하는 결과인 참조변수가 가르키는 참조객체의 실제 값을 비교할 수 있다.



- 내가 정의한 Object의 equals()
  - 내가 정의한 Object의 equals는 내가 새로 재정의 해줘야 한다. 왜냐하면 내부적으로 ==을 사용하기 때문에

# hashCode()

- 기본적으로 hashCode()는 **객체의 메모리 번지를 이용해 시코드를 만들어 리턴**
- 그러기에 객체마다 다른 값을 가지고 있다.



- String의 hashCode()

  - ```java
    public int hashCode() {
      int h = hash;
      if (h == 0 && value.length > 0) { //(1)
        char val[] = value;
    
        for (int i = 0; i < value.length; i++) {
          h = 31 * h + val[i];
        }
        hash = h;
      }
      return h;
    }
    ```

  - (1) 멤버 변수 hash가 0, 즉, hash code를 계산한 적이 없으면 문자열 앞에서부터 한자씩 읽으면서 ascii code로 변환해서 처리.

  - 기존까지 계산한 값은 31을 곱하고 새로운 문자는 ascii code의 숫자로 변환해서 숫자로 더합니다.

  - 그러기에 String의 값이 같다면 hashCode()가 같은 값이 나온다.

  - 즉, 중복값이 나올 수 있다.



- 내가 새로 정의한 Object의 hashCode()
  - String의 hashCode()와는 다르게 내가 정의한 Object에 대해서는 논리적(필드값들)이 같아서 같은 객체로 찾기 위해서는 재정의를 해줘야한다.
  - hashCode()와 equals()는 세트로 재정의를 해줘야한다.



# instanceOf()

- 참조변수가 참조하고 있는 **인스턴스의 실제 타입**을 알아보기 위해 t사용한다.









# equals()와 hashcode()를 같이 정의해야하는 이유

- 일반 객체들끼리 비교는 equals()만 재정의해도 내가 원하는 결과값이 나온다.
- 그러나 HashSet, HashMap, HashTable을 사용할 경우에는 내가 원하는 결과가 안나온다.
  - <img src="img/==&equals()&hashCode()&instanceOf/Screenshot of Typora (2022-11-28 7-10-08 PM).png" alt="Screenshot of Typora (2022-11-28 7-10-08 PM)" style="zoom:50%;" />
  - 위와 같이 hashCode()를 거친다.
  - 그러기에 hashCode()를 재정의해야한다.



