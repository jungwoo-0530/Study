# Method Signature, Method Type



## Method Signature(메소드 시그니쳐)

- name, parameter types가 메소드 시그니쳐
- 메소드 시그니쳐가 같으면 오버라이딩을 할 수 있고 같은 메소드를 정의할 수 없다.
- return type은 시그니쳐에 포함 x

```java
String hello(int id) {
    return "";
  }
```

`hello(int id)`가 Method Signature





## Method Type

- return type, method parameger type , method argument types,  exception이 Method Type
- Method Type이 일치해야 Method Reference를 사용할 수 있다.