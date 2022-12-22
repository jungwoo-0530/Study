# JVM 메모리 구조



## JVM(Java Virtual Machine, 자바 가상 머신)이란?

- Java 프로그램의 범주에 들어가는 모든 것들을 실행시키는 기본 데몬을 JVM이라고 한다.
- **자바 바이트 코드를 실행시키기 위한 가상의 기계**

- Java에서 프로그램을 실행한다는 것은 **컴파일 과정을 통하여 생성된 Class 파일을 JVM으로 로딩하고 ByteCode를 해석하는 과정을 거쳐 메모리 등의 리소스를 할당하고 관리하며 정보를 처리하는 일련의 작업**들을 포괄합니다.

- <img src="img/JVM 메모리 구조/Screenshot of Safari (2022-12-07 10-52-23 AM).png" alt="Screenshot of Safari (2022-12-07 10-52-23 AM)" style="zoom:50%;" />

- 자바로 작성된 프로그램은 위와 같은 순서로 실행됩니다.

  - 자바 컴파일러(Java Compiler)
    - 자바 컴파일러는 자바를 가지고 작성한 **자바 소스 코드**를 **자바 가상 머신이 이해할 수 있는 자바 바이트 코드**로 변환
    - `.java -> .class`
  - 자바 바이트 코드(Java ByteCode)
    - **JVM이 이해할 수 있는 언어로 변환된 자바 소스 코드**
    - `.class`

- JVM은 다음과 같이 구성됨.

  - 자바 인터프리터(interpreter)
    - **자바 바이트 코드를 읽고 해석하는 역할**
  - 클래스 로더(class Loader)
    - **자바는 동적으로 클래스를 읽어오므로, 프로그램이 실행 중인 런타임에서야 모든 코드가 JVM과 연결.**
    - **이렇게 동적으로 클래스를 로딩해주는 역할을 해주는 것이 바로 클래스 로더**
  - JIT(Just In Time compiler) 컴파일러
    - 자바 바이트 코드를 런타임에 바로 기계어로 변환
      - 여기서 기계어란 0과 1로 이루어진 바이너리 코드
      - 즉, 컴퓨터(CPU)가 인식할 수 있는 0과 1로 구성된 이진코드.
      - 바이트코드는 JVM이 이해할 수 있는 언어.
  - 가비지 컬렉션

  

- <img src="img/JVM 메모리 구조/Screenshot of Safari (2022-12-07 10-35-49 AM).png" alt="Screenshot of Safari (2022-12-07 10-35-49 AM)" style="zoom:50%;" />

  - **Java Source:** 사용자가 작성한 Java 코드이다
  - **Java Compiler:** Java Source 파일을 JVM이 해석할 수 있는 Java Byte Code로 변경한다.
  - **Java Byte Code:** Java Compiler에 의해 수행될 결과물이다(확장자 .class 파일)
  - **Class Loader:** JVM 내로 .class 파일들을 Load하여 Loading된 클래스들을 Runtime Data Area에 배치된다.
  - **Execution Engine:** Loading된 클래스의 Bytecode를 해석(interpret)한다.
  - **Runtime Data Area:** JVM이라는 프로세스가 프로그램을 수행하기 위해 OS에서 할당받은 메모리 공간이다.

- JVM상에서 **Class Loader를 통해 Class파일들을 로딩시키고, 로딩된 Class파일들을 Execution(엑스큐션) Engine을 통해 해석됩니다.**
- 해석된 프로그램은 **Runtime Data Areas에 배치되어 실질적인 수행이 이루어지게 됩니다.**
- <img src="img/JVM 메모리 구조/Screenshot of Safari (2022-12-07 10-39-20 AM).png" alt="Screenshot of Safari (2022-12-07 10-39-20 AM)" style="zoom:50%;" />
  - Runtime data areas 내부
  - **Method Area:** 클래스, 변수, Method, static변수, 상수 정보 등이 저장되는 영역(모든 Thread가 공유)
  - **Heap Area:** new 명령어로 생성된 인스턴스와 객체가 저장되는 구역(Garbage Collection 이슈는 이 영역에서 일어나며, 모든 Thread가 공유)
  - **Stack Area:** Method 내에서 사용되는 값들(매개변수, 지역변수, 리턴값 등)이 저장되는 구역으로 메소드가 호출될때 LIFO로 하나씩 생성되고, 메소드 실행이 완료되면 LIFO로 하나씩 지워진다. (각 Thread별로 하나씩 생성)
  - **PC Register:** CPU의 Register와 역할이 비슷하다. 현재 수행중인 JVM 명령의 주소값이 저장된다. (각 Thread별로 하나씩 생성)
  - **Native Method Stack:** 다른 언어(C/C++ 등)의 메소드 호출을 위해 할당되는 구역으로 언어에 맞게 Stack이 형성되는 구역이다.



## Byte Code

- 자바 바이트코드는 **자바 가상 머신(JVM)이 이해할 수 있는 언어로 변환**된 자바 소스코드를 의미.
- 이러한 자바 바이트 코드의 확장자는 `.class`
- 자바 컴파일러에 의해 변환되는 코드의 명령어 크기가 1바이트라서 자바 바이트 코드라고 불리고 있다.
- 자바 바이트 코드는 JVM만 설치되어 있으면, **어떤 운영체제에서라도 실행될 수 있다.**



## Class Loader

- 자바는 런타임(바이트 코드를 실행할 때)에 클래스 로드하고 링크하는 특징이 있다.
- 이 동적 로드를 담당하는 부분이 JVM의 클래스 로더
- 정리하자면, **클래스 로더는 런타임 중에 JVM의 메소드 영역에 동적으로 Java 클래스를 로드하는 역할을 한다.**
- 로딩
  - 자바 바이트코드(.class)를 메소드 영역에 저장한다.
  - 



## Java Heap

- Heap은 Instance(Object)와 Array 객체 두 가지 종류만 저장되는 공간
- 모든 Thread들에 의해 공유되는 영역

- Garbage Collection의 대상이 되는 영역

## Method Area

- Class Loader가 적재한 클래스(또는 인터페이스)에 대한 메타데이터 정보가 저장된다.
- 이 영역에 등록된 Class만이 heap에 생성될 수 있다.
- <img src="img/JVM 메모리 구조/Screenshot of Typora (2022-12-07 10-45-35 AM).png" alt="Screenshot of Typora (2022-12-07 10-45-35 AM)" style="zoom:50%;" />
- 