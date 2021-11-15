# OSIV

트랜잭션이 시작할 때, JPA의 영속성 컨텍스트가 DB 커넥션을 가져온다.

DB 커넥션을 획득하고 다 사용 후, 언제 다시 DB에 반납하느냐?

-> OSIV가 켜져있으면(default가 켜진 상태) api면 유저에게 반환될 때까지, 화면이면 뷰 템블릿을 렌더링하고 리스폰을 할때 까지.

-> 그래서 지연 로딩이 가능 했던 것.

-> 지연 로딩은 영속성 컨텍스트가 살아 있어야 가능하고, 영속성 컨텍스트는 DB 커넥션이 유지되어야 한다.

```java
@Transactional//DB커넥션 획득.
public Long write(Board board) {
	boardRepository.save(board);
	return board.getId();
}
```



그러나 치명적인 단점은 너무 오랫동안 커넥션 리소스를 사용하기에 트래픽이 많은 환경에서는 커넥션이 부족할 수 있다.







그래서 ADMIN처럼 커넥션을 많이 사용하지 않는 곳에서는 OSIV를 키고 고객 서비스의 실시간 API는 OSIV를 끄는 것을 추천.

