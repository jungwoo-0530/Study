# Overriding

- 부모 클래스로부터 상속받은 메소드를 자식 클래스에서 재정의하는 것을 오버라이딩

- **슈퍼 클래스의 메소드 무시하기**로 변역되기도 함
- 오버라이딩된 메소드가 무조건 실행되도록 동적 바인딩됨.

## 사용 이유

- 자식 클래스에서 오버라이딩하는 메소드의 접근 제어자는 부모 클래스보다 더 좁게 설정 가능.
- 예외(Exception)는 부모 클래스의 메소드보다 많이 선언 가능.
- static 메소드를 인스턴스의 메소드로 또는 그 반대로 바꿀 수 없다.
  - 부모 클래스의 static 메소드를 자식에서 같은 이름으로 정의할 수 있지만 이것은 **다시 정의하는 것이 아니라 같은 이름의 static 메소드를 새로 정의하는 것**

## 조건

- 오버라이딩된 메소드의 접근 지정자는 **슈퍼 클래스의 메소드의 접근 지정자 보다 좁아 질 수 없다.**

  - 슈퍼클래스 메소드가 public인데 서브 클래스의 오버라이딩된 메소드가 protected라면 error

- 자식 클래스에서는 **오버라이딩하고자 하는 메소드의 이름, 매개변수, 리턴 값이 모두 같아야 한다.**

- ```java
  public class OverridingTest {
  
  	public static void main(String[] args) {
  		Person person = new Person();
  		Child child = new Child();
  		Senior senior = new Senior();
  		
  		person.cry();
  		child.cry();
  		senior.cry();
  	}
  }
  
  class Person {
  	void cry() {
  		System.out.println("흑흑");
  	}
  }
  
  class Child extends Person {
  	@Override
  	protected void cry() {
  		System.out.println("잉잉");
  	}
  }
  
  class Senior extends Person {
  	@Override
  	public void cry() {
  		System.out.println("훌쩍훌쩍");
  	}
  }
  ```





## @Override의 용도

- @Override 어노테이션 없어도 동적 바인딩으로 인해 오버라이드된 메소드를 찾는다.
- 어노테이션이라는 것은 주석이라는 뜻. 일반적인 주서과 다르게 **검증하는 기능**을 한다.
- @Override라는 어노테이션은 오버라이딩을 검증하는 기능을 하므로 코드상 에러가 발생시 컴파일시 오류를 출력.







# Overloading

- 자바의 **한 클래스 내**에 **이미 사용하려는 이름과 같은 이름을 가진 메소드**가 있더라도 **매개변수의 개수 또는 타입이 다르면**, **같은 이름을 사용해서 메소드를 정의**할 수 있다.

## 사용 이유

- 같은 기능을 하는 메소드를 하나의 이름으로 사용할 수 있다.
- 메소드의 이름을 절약할 수 있다.



## 조건

- 메소드 이름이 같고, **매개변수의 개수나 타입이 달라야** 한다.

- **리턴 값만 다른 것은 오버로딩을 할 수 없다.**

- ```java
  class OverloadingTest {
  
  	public static void main(String[] args) {
  		OverloadingMethods om = new OverloadingMethods();
  
  		om.print();
  		System.out.println(om.print(3));
  		om.print("Hello!");
  		System.out.println(om.print(4, 5));
  	}
  }
  
  class OverloadingMethods {
  	public void print() {
  		System.out.println("오버로딩1");
  	}
  
  	String print(Integer a) {
  		System.out.println("오버로딩2");
  		return a.toString();
  	}
  
  	void print(String a) {
  		System.out.println("오버로딩3");
  		System.out.println(a);
  	}
  
  	String print(Integer a, Integer b) {
  		System.out.println("오버로딩4");
  		return a.toString() + b.toString();
  	}
  
  }
  ```

  





# 차이점

**오버로딩 - 기존에 없는 새로운 메소드를 추가하는 것**

**오버라이딩 - 상속받은 메소드를 재정의 하는 것**