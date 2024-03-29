#### JPA

- **자바 ORM 기술 표준**, 인터페이스의 모음
- 3가지 구현체 : **하이버네이트**, EclipseLink, DataNucleus

- SQL 중심적인 개발에서 **객체** 중심으로 개발
- 생상선, 유지 보수, 패러다임의 불일치 해결, 성능, 표준

#### ORM

- **Object-relational mapping(객체 관계 맵핑)**
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- **ORM 프레임워크가 중간에서 매핑**
- 대중적인 언어에는 대부분 ORM 기술이 존재

#### JPA의 장점

1. 생산성 - jpa와 CRUD

- 저장 : **jpa.persist**(member)
  - .persist()는 리턴 형태 void, .save()는 리턴 형태가 저장된 객체(Serializable)
- 조회 : Member member = **jpa.find**(memberid)
- 수정 : **member.setName**("변경할 이름") 
- 삭제 : **jpa.remove**(member)



2. 유지보수 

- 기존: 필드 변경시 모든 SQL 수정
- JPA: 필드만 추가하면 됨, SQL은 JPA가 처리



3. **관계형 데이터베이스와 객체의 패러다임의 불일치 해결**

- 상속 : 객체 상속 관계에서 Table 슈퍼타입 서브타입 관계에서 우리는 하나의 코드로 JPA가 알아서 SQL을 짜줌.
- 



4. JPA의 성능 최적화 기능

- 1차 캐시와 동일성(identity) 보장
  - 같은 트랜잭션 안에서는 같은 엔티티를 반환(약간의 조회 성능 향상)
  - DB Isolation Level이 Read commit이어도 애플리케이션에서 Repeatable Read 보장
    - 같은 멤버를 연속으로 두번 find하면 처음 한번은 SQL, 두 번째은 캐시로 가져옴.
    - 우리가 아는 캐시보다는 하나의 트랜잭션에서의 캐시라서 큰 성능 향상은 어려움.
- 트랜잭션을 지원하는 쓰기 지연(transactioanl write-behind) - 버퍼
  - 트랜잭션을 커밋할 때까지 INSERT SQL을 모음.
- 지연 로딩(Lazy Loading)과 즉시 로딩
  - 지연 로딩 : 객체가 실제 실행될 때 로딩
  - 즉시 로딩 : JOIN SQL로 한번에 연관된 객체까지 미리 조회

