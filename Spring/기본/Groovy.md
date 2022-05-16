# Groovy

- JVM 상에서 실행되는 스크립트 언어
- Java와 유사한 문법 구조
- Java와 호환성이 아주 좋다.
- gradle에서 사용되는 언어



- 세미 퀄론 생략 가능

- 변수 선언의 타입 생략

  - ```groovy
    String name = '김정우'
    def name = '김정우'
    ```

- 리턴 타입

  - ```groovy
    def getName(){
      '홍길동'
    }
    
    assert getName() == '홍길동'
    ```

- 함수 호출

  - ```groovy
    println ('Hello Wordl')
    println 'Hello Wordl'
    ```

    