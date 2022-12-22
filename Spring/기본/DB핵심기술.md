## 서버가 데이터베이스를 사용하는 과정

**1. 커넥션 연결**: 주로 TCP/IP를 사용해서 커넥션을 연결한다.

**2. SQL 전달**: 애플리케이션 서버는 DB가 이해할 수 있는 SQL을 연결된 커넥션을 통해 DB에 전달한다.

**3. 결과 응답**: DB는 전달된 SQL을 수행하고 그 결과를 응답한다. 애플리케이션 서버는 응답 결과를 활용한다.







<img src="img/DB연결/image-20221026042026838.png" alt="image-20221026042026838" style="zoom:50%;" />





### DataSource

- DriverManager의 대안
- DataSource를 통해 Connection Pool을 활용할 수 있다.
  - 즉, Connection Pool을 관리
  - <img src="img/DB핵심기술/Screenshot of Safari (2022-12-15 6-10-57 PM).png" alt="Screenshot of Safari (2022-12-15 6-10-57 PM)" style="zoom:50%;" />
- **DataSource의 구현체로  HikariCP가 있다.**

- DB와 관계된 커넥션 정보, 커넥션을 위한 standard method를 담고있으며 빈으로 등록하여 인자로 넘겨준다.
  - 이 과정을 통해 Spring은 DataSource로 DB와 Connection객체를 획득한다.
    - DB 서버와의 연결을 해준다.
    - DB Connection pooling 기능
- spring boot에서는 `spring-boot-starter-jdbc` 또는 `spring-boot-starter-data-jpa`를 추가하면 Spring Boot에서는 DataSource를 관리를 위한 구현체로써 tomcat-jdbc를 default를 제공한다.

- dataSource 빈 생성 방법

  - 스프링 부트에서 프로퍼티 설정으로 dataSource 빈 자동 생성 가능.(`@EnableAutoConfiguration`이 해줌.)

  - `spring.datasource.*`

    - 

      ```properties
      #몽고 DB 기본 설정.
      spring.data.mongodb.host=127.0.0.1
      spring.data.mongodb.port=27017
      spring.data.mongodb.authentication-database=admin
      spring.data.mongodb.username=<username specified on MONGO_INITDB_ROOT_USERNAME>
      spring.data.mongodb.password=<password specified on MONGO_INITDB_ROOT_PASSWORD>
      spring.data.mongodb.database=<the db you want to use>
      ```

    - 

      ```Yaml
      spring:
      
        datasource:
          driver-class-name: org.mariadb.jdbc.Driver
          url: jdbc:mysql://127.0.0.1:3306/apiserver
          username: root
          password: root
      ```

    - 위와 같이 예약된 키에 프로퍼티 설정을 하면 자동으로  dataSource 빈을 생성해 줌.

  - JavaConfig에 직접 `application.yml`설정값 주입하여 dataSource 빈 직접 생성

    - 프로퍼티의 예약된 key에 datasource 설정을 하고 웹 애플리케이션 실행시 dataSource 빈이 자동으로 생성
    - database 정보가 하나라면 프로퍼티 자동 설정을 이용하는 것이 편리
    - 여러개의 database를 연결해야 하는 경우에는 JavaConfig 또는 xml을 통한 datasource설정
    - `@ConfigurationProperties`로 dataSource 정보를 읽어옴
    - https://blog.jiniworld.me/69





Database Connection을 위한 DataSource 설정이 완료되었다면, JDBC Template클래스를 통해 Query 질의를 할 수 있다.



### Plain JDBC(JAVA Database Connectivity)

- DB에 접근할 수 있도록 java에서 제공하는 API

- 쿼리를 실행하기 전과 후에 많은 코드를 작성해야한다. (연결생성, 명령문, 등등)

- 예외처리코드와 트랜잭션 처리등에 시간과 자원이 소모

  - jdbc에서 발생하는 에러는 Runtime Exception이다. 따라서 모두 예외처리를 해줘야함

- 이러한 문제점을 보완하여 생겨난것이 Spring JDBC

- JDBC 드라이버 : 각 DBMS 회사에서 제공하는 드라이버

  - 드라이버를 제공하기에 mysql 쓰다가 postgre로 얼마든지 옮길 수 있다.

- JDBC 프로그래밍을 위해서 JDBC 드라이버가 필요.

- ```java
  public class JdbcMemberRepository implements MemberRepository {
      private final DataSource dataSource;
      public JdbcMemberRepository(DataSource dataSource) {
          this.dataSource = dataSource;
      }
      @Override
      public Member save(Member member) {
          String sql = "insert into member(name) values(?)";
          Connection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          try {
              conn = getConnection();
              pstmt = conn.prepareStatement(sql,
                  Statement.RETURN_GENERATED_KEYS);
              pstmt.setString(1, member.getName());
              pstmt.executeUpdate();
              rs = pstmt.getGeneratedKeys();
              if (rs.next()) {
                  member.setId(rs.getLong(1));
              } else {
                  throw new SQLException("id 조회 실패");
              }
              return member;
          } catch (Exception e) {
              throw new IllegalStateException(e);
          } finally {
              close(conn, pstmt, rs);
          }
      }
      private Connection getConnection() {
          return DataSourceUtils.getConnection(dataSource);
      }
      private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
          try {
              if (rs != null) {
                  rs.close();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
          try {
              if (pstmt != null) {
                  pstmt.close();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
          try {
              if (conn != null) {
                  close(conn);
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
      private void close(Connection conn) throws SQLException {
          DataSourceUtils.releaseConnection(conn, dataSource);
      }
  }
  ```

  



### Spring JDBC(JAVA Database Connectivity)

- **등장 이유**
  - 데이터 베이스를 다른 종류의 DB로 변경하면 애플리케이션 서버에 개발된 DB 사용 코드도 함께 변경해야함.
  - 개발자가 각각의 DB마다 커넥션 연결,  SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로 학슴.
  - 이러한 문제 해결을 위해  JDBC라는 자바 표준 등장.
  - <img src="img/DB핵심기술/image-20221026203532188.png" alt="image-20221026203532188" style="zoom:50%;" />
  - 이  JDBC 인터페이스를 각각의 DB 벤더(회사)에서 자신의 DB에 맞도록 구현해서 라이브러리를 제공하는데, 이것을 **JDBC 드라이버**라 한다.

- DB에 접근할 수 있도록 java에서 제공하는 API

- Spring JDBC가 하는 일(개발자가 하지 않는 일)

  - Connection 열기와 닫기
  - Statement 준비와 닫기
  - Statement 실행
  - ResultSet Loop처리
  - Exception 처리와 반환
  - Transaction 처리

- Spring JDBC에서 개발자가 할 일

  - datasource 설정
  - sql문 작성
  - 결과 처리

- JDBC 드라이버 : 각 DBMS 회사에서 제공하는 드라이버

  - 드라이버를 제공하기에 mysql 쓰다가 postgre로 얼마든지 옮길 수 있다.

- JDBC 프로그래밍을 위해서 JDBC 드라이버가 필요.

- 자바 프로그래밍 -> JDBC API -> JDBC Driver Manager -> DB

- JDBC를 이용한 데이터 베이스 연동과정

  - <img src="img/DB연결/image-20221026025739333.png" alt="image-20221026025739333" style="zoom:50%;" />

- Spring Boot 이전의 JDBC는 xml로 dataSource생성 후 jdbcTemplate의 property에 dataSource를 넣어서 사용하였다.

  - ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    		xmlns:util="http://www.springframework.org/schema/util"
    		xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
    			http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-3.2.xsd">
    
        <!-- Database properties -->
    	<util:properties id="dataSourceConfig" location="classpath:/properties/datasource.properties" />
    	
    	<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
    		<property name="driverClassName" value="#{dataSourceConfig['datasource.driverClassName']}" />
    		<property name="url" value="#{dataSourceConfig['datasource.url']}" />
    		<property name="username" value="#{dataSourceConfig['datasource.username']}" />
    		<property name="password" value="#{dataSourceConfig['datasource.password']}" />
    		<property name="initialSize" value="10" />
    		<property name="maxTotal" value="10" />
    		<property name="maxIdle" value="10" />
    		<property name="minIdle" value="10" />
    		<property name="maxWaitMillis" value="3000" />
    	</bean>
    	
    	<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
     		<property name="dataSource" ref="dataSource" />
     		<property name="queryTimeout" value="5" />
     	</bean>
    </beans>
    
    ```

  - ```java
    @Repository
    public class MonitorRepository {
    	
    	@Autowired
    	private JdbcTemplate jdbcTemplate;
    	
    	public String selectStatus() {
    		return jdbcTemplate.queryForObject("SELECT 'OK'", new Object[] {}, String.class);
    	}
    }
    ```

- Spring Boot에서는 spring JDBC 제공을 위한 starter를 제공

  - ```groovy
    compile "org.springframework.boot:spring-boot-starter-jdbc"
    ```

  - Spring boot에서는 spring JDBC를 사용하기 위해서 DataSource가 필요하다.

  - ```java
    @Configuration
    public JdbcTemplateConfig {
    
    	@Bean
    	public DataSource dataSource() {
    		DriverManagerDataSource dataSource = new DriverManagerDataSource();
    		dataSource.setUsername("testsuer");
    		dataSource.setPassword("testuserpwd");
    		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
    		dataSource.setUrl("jdbc:mariadb://localhost:3306/test_db");
    		return dataSource;
            }
        
    	@Bean
    	public JdbcTemplate jdbcTemplate(){
    		return new JdbcTemplate(dataSource());
    	}
    }
    
    //위와 같이 datasource를 빈으로 등록 안해도 됨.
    //프로퍼티 설정으로 datasource 자동 생성 후, datasource를 주입 받고 객체를 주입하면 된다.
    
    // 빈 설정이 다 끝나면 주입해서 jdbc Template 사용
    ```

- Spring Boot는 `@EnableAutoConfiguration`을 통해 DataSourece와 JdbcTemplate에 대한 Bean을 별도로 등록하지 않아도 된다.(DataSource Properties의 정보로 생성)

  - autoConfigure이 가능한 속성으로 별도의  DataSource를 구현하여 Bean으로 등록했다면, application.properties 내의 spring.datasource.* 속성은 적용되지 않는다.



### JDBC Template

- **Spring JDBC 접근 방법 중 하나**로, **내부적**으로 Plain JDBC API를 사용하지만 **Plain JDBC의 문제점을 해결**한 **Spring에서 제공하는 클래스**
- Plain JDBC와 다르게 DataSource를 직접 사용하는 것이 아닌, DataSource를 감싼 JdbcTemplate 인스턴스를 사용해서  DB에 접근한다.
- JdbcTemplate이 제공하는 기능
  - 실행 : Insert나 Update같이 DB의 데이터에 변경이 일어나는 쿼리를 수행하는 작업
  - 조회 : Select를 이용해 데이터를 조회하는 작업
  - 배치 : 여러 개의 쿼리를 한 번에 수행해야 하는 작업
  - jdbc template을 사용하면 커넥션 연결/종료와같은 세부적인 작업을 개발자가 직접 처리하지 않아도 되게된다.

- JDBC 사용법

  - `implementation 'org.springframework.boot:spring-boot-starter-jdbc'`

  - 

  - ```java
    @RequiredArgsConstructor
    @Repository
    public class JdbcTemplateMemberRepository implements MemberRepository {
        
      private final JdbcTemplate jdbcTemplate;
        
        @Override
        public Member save(Member member) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);        
            jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");        
            Map<String,Object> parameters = new HashMap<>();
            parameters.put("name", member.getName());        
            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            member.setId(key.longValue());
            return member;
        }
        @Override
        public Optional<Member> findById(Long id) {
        	List < Member > result = jdbcTemplate.query("select * from member where id = ? ", memberRowMapper(), id);
            return result.stream().findAny();
        }
        @Override
        public List < Member > findAll() {
            return jdbcTemplate.query("select * from member", memberRowMapper());
        }
        @Override
        public Optional < Member > findByName(String name) {
            List < Member > result = jdbcTemplate.query("select * from member where name = ? ", memberRowMapper(), name);
                return result.stream().findAny();
            }
            private RowMapper < Member > memberRowMapper() {
                return (rs, rowNum) -> {
                    Member member = new Member();
                    member.setId(rs.getLong("id"));
                    member.setName(rs.getString("name"));
                    return member;
                };
    }
    ```

    



1. 커넥션풀에서 커넥션을 미리 만들어 놓음(DBCP)
2. 







### Spring Data JPA vs Spring Data JDBC

- | Spring Data JPA                                              | Spring Data JDBC                              |
  | ------------------------------------------------------------ | --------------------------------------------- |
  | Lazy, Eager Loding 설정 가능                                 | X                                             |
  | Dirty Check 있음                                             | 없음                                          |
  | 많은 Annotation                                              | JPA보다 적은 Annotation                       |
  | OneToOne, ManyToOne, ManyToMany, OneToMany 단반향, 양방향 제공 | OneToOne, OneToMany, ManyToMany 단방향만 제공 |
  | 외래키 맵핑 있음                                             | 외래키 맵핑 없음                              |

- Spring Data JDBC 와 Spring JDBC는 다른 것.







### 커넥션 풀

- 자바는 기본적으로 DataSource 인터페이스를 사용하여 커넥션 풀을 관리함

- Spring Boot 2.0 이전은 `tomcat-jdbc`를 제공

- Spring Boot 2.0 이후부터는 `HikariCP`를 기본 옵션으로 채택

- 히카리는 **Connection 객체**를 한번 **Wrappring**한 **PoolEntry**로 Connection을 관리하며,
  이를 관리하는 **ConcurrentBag이라는 구조체**를 사용하고 있다.

  - `ConcurrentBag은 HikariPool.getConnection() -> ConcurrentBag.borrow()`로 Connection 리턴 가능.

- Hikari CP 사용법

  - ```yaml
    spring:
     datasource:
       url: jdbc:mysql://localhost:3306/world?serverTimeZone=UTC&CharacterEncoding=UTF-8
       username: root
       password: your_password
       hikari:
         maximum-pool-size: 10
         connection-timeout: 5000
         connection-init-sql: SELECT 1
         validation-timeout: 2000
         minimum-idle: 10
         idle-timeout: 600000
         max-lifetime: 1800000
    
    server:
     port: 8000
    ```

  

  - 데드락 피하기.

  이론적으로 필요한 최소한의 커넥션 풀 사이즈를 알아보면 다음과 같다.

  > PoolSize = Tn × ( Cm -1 ) + 1
  >
  > - Tn : 전체 Thread 갯수
  > - Cm : 하나의 Task에서 동시에 필요한 Connection 수

  위와 같은 식으로 설정을 한다면 데드락을 피할 수는 있겠지만 여유 커넥션풀이 하나 뿐이라 성능상 좋지 못하다.
  따라서 커넥션풀의 여유를 주기위해 아래와 같은 식을 사용하는것을 권장한다.

  > PoolSize = Tn × ( Cm - 1 ) + ( Tn / 2 )
  >
  > - thread count : 16
  > - simultaneous connection count : 2
  > - pool size : 16 * ( 2 – 1 ) + (16 / 2) = 24

  더 자세히 알아보고 싶으면 다음 블로그에서 확인하면 좋을듯 하다.







### JPA(JAVA Persistence API)

- 자바 어플리케이션에서 **관계형 데이터 베이스를 사용하는 방식을 정의하는 인터페이스**
- JAVA의 **ORM**기술에 대한 표준 명세
- 관계형 데이터를 사용하는 기술
  - 객체간 관계를 바탕으로 SQL을 자동으로 생성
  - SQL 쿼리가 아닌 메서드로 데이터를 조작
    - jdbcTemplate는 ORM에 반대되는 SQL Mapper의 하나.
  - 도메인에 `@Entity`를 붙이면 JPA가 Entity를 관리한다.
- JPA의 핵심은 `EntityManger`, `EntityManagerFactory` 라이브러리
  - `Entity CRUD`를 처리
  - 
- JPA(자바 표준 인터페이스), Hibernate(구현체)

- `implementation 'org.springframework.boot:spring-boot-starter-data-jpa'`

- ```groovy
  spring:
    jpa:
    	show-sql = true //jpa가 날리는 sql을 볼 수 있음
  		hibernate:
        ddl-auto: none //jpa를 사용하면 객체를 보고 자동으로 테이블을 만들어 주는데 그 기능을 끌려면 none
  ```





- ```java
  @Configuration
  public class SpringConfig {
  	
  	private EntityManager em;
  	public SpringConfig(EntityManager em) { this.em= em;}
  	/*
  	// JdbcTemplate 파라미터를 위함
      private final DataSource dataSource;
       public SpringConfig(DataSource dataSource) {
          this.dataSource = dataSource;
      }*/
      
      @Bean
      public MemberService memberService() {
          return new MemberService(memberRepository());
      }
      @Bean
      public MemberRepository memberRepository() {
          //return new MemoryMemberRepository();
      	//return new JdbcMemberRepository(dataSource);
      	//return new JdbcTemplateMemberRepository(dataSource);
      	return new JpaMemberRepository(em);
      }
  ```





- JPA를 정의한 **javax.persistence** 패키지 대부분이 **Interface** 와 enum으로 구성되어있고 JPA의 핵심인 **EntityManager**도 interface로 정의되어 있다. 우리가 주로 사용하는 **Hibernate가 JPA를 구현한 구현체**이며 Hibernate 외에 여러 ORM Framework가 존재한다.

- ```java
  package javax.persistence;
  import ...
  
  public interface EntityManager {
  
    public void persist(Object entity);
    public <T> T merge(T entity);
    public void remove(Object entity);
    public void flush();
    // more...
  }
  ```

- Spring data jpa를 사용하지 않는다면 EntityManager에서 제공하는 API를 사용하면 된다.

  - ```java
    @Repository
    @RequiredArgsConstructor
    public class OrderRepository {
    
        private final EntityManager em;
    
        public void save(Order order){
            em.persist(order);
        }
    
        public Order findOne(Long id){
            return em.find(Order.class, id);
        }
    
        public List<Order> findAll() {
            return em.createQuery("select o from Order o", Order.class)
                    .getResultList();
        }
        
        public List<Order> findAllWithItem() {
            return em.createQuery(
                    "select distinct o from Order o" +
                            " join fetch o.member m" +
                            " join fetch o.delivery d" +
                            " join fetch o.orderItems oi" +
                            " join fetch oi.item i", Order.class)
                    .getResultList();
        }
    }
    ```

  - 위 OrderRepository는 Spring Data JPA가 제공하는 JpaRepository를 상속하지 않고 **@Repository만 추가**했다. EntityManager가 제공하는 API를 활용해 적절한 API를 만들면 된다.

  - 구현체가 Hibernate이므로 위를 실행한다면 주로 jpa를 구현한 Hiberante 객체가 일할 것이다. 

- **다양한 쿼리 방법을 지원**
  - **JPQL**
  - JPA Criteria
  - **QueryDSL**
  - 네이티브 SQL
  - JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용



### Hibernate

- JPA 인터페이스의 구현체
- Dialect
- ``



### Spring Data Jpa

- Spring에서 제공하는 모듈 중 하나로, **JPA**를 더 쉽고 편하게 사용할 수 있도록 도와준다.

  - Spring Data JPA의 가장 핵심은 `JpaRepsoitory`
    - JPA를 한 단계 더 추상화 시킨 인터페이스
    - 구현에 있어서 JPA를 사용
    - JpaRepository가 JPA가 아닌 사용하기 쉽게 해주는 Spring Data JPA의 모듈 중 하나
    - 기능 : 사용자가 `JpaRepository` 인터페이스에 정해진 **규칙대로 메소드를 작성**하면, Spring이 알아서 해당 **메소드 이름에 적합한 쿼리를 날리는 구현체**를 만들어서 **Bean으로 등록**해준다.

- `implementation 'org.springframework.boot:spring-boot-starter-data-jpa'`

- Spring Data JPA의 **Repository Interface (SimpleJpaRepository)**에는 **EntityManager가 포함**되어 있기 때문에 직접 작성하지 않아도 내부에서 자동으로 호출된다. 또한 @Repository를 작성하지 않아도 **Bean으로 등록**된다. 자세한 내용은 SimpleJpaRepository 코드를 보며 알아가자.

  - ```java
    package org.springframework.data.jpa.repository.support;
    import ...
    
    @Repository
    @Transactional(readOnly = true)
    public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {
    
    	private final JpaEntityInformation<T, ?> entityInformation;
    	private final EntityManager em;//여기에 포함되어 있다.
    	private final PersistenceProvider provider;
    
    	public SimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {...}
        
        @Override
    	public Optional<T> findById(ID id) {...}
        
        @Override
    	public List<T> findAllById(Iterable<ID> ids) {...}
        
        @Override
    	public long count() {...}
        
        @Transactional
    	@Override
    	public <S extends T> S save(S entity) {...}
    
        @Override
    	@Transactional
    	@SuppressWarnings("unchecked")
    	public void delete(T entity) {...}
    ```

    - 

- ```java
  public interface AccountRepository extends JpaRepository<Account, Long> {
  
      boolean existsByUsername(String username);
  
      Optional<Account> findById(Long id);
  
      Optional<Account> findByUsername(String username);
  
      Optional<Account> findByUsernameAndPassword(String username, String password);
  }
  ```

  - Repository Interface 에 정해진 규칙대로 method 입력 시 **method 에 적합한 JPQL을 생성해 처리**한다.
    - findBy`entity 속성이름` : entity 속성이름으로 검색
    - findByEmail**Or**UserId (String email, String userId) : 여러 필드로 검색
    - countBy`entity 속성이름` : entity 속성이름에 해당하는 결과 개수를 반환

- 유용한 어노테이션

  - `@EntityGraph`, `@Lock`, `@Modifying`, `@Query`, `@QueryHints`, and `@Procedure`.

- Raw JPA 사용과 Spring Data JPA 사용

  - <img src="img/DB연결/image-20221026045459488.png" alt="image-20221026045459488" style="zoom:50%;" />

  - DB에 접근하는 순서도
    - <img src="img/DB연결/image-20221026045612988.png" alt="image-20221026045612988" style="zoom:50%;" />



- 참고로 spring-boot-starter-data-jpa 의존성을 추가하면 jdbc도 사용 가능하다.
  - <img src="img/DB연결/image-20221026055253034.png" alt="image-20221026055253034" style="zoom:50%;" />
  - hibernate, jakarta.persistence .... 존재.



---------------------

entityManagerFactory(엔티티매니저 생성 기능)= dataSource + jpaProperites

transactionManager 빈(트랜잭션 처리 기능) = entityManagerFactory



### EntityManagerFactory

- Persistence 객체로 생성된다.

- EntityManagerFactory는 DB당 하나

- 동시성(Concurrency)

  - 동시 수행처럼 보이지만 사용자가 체감할 수 없을 정도로 짧은 시간단위로 작업들을 번갈아가면서 수행

- 병렬

  - 실제로 동시에 여러 작업이 수행되는 개념

- EntityManger는 여러 스레드가 동시에 접근하면 안된다(동시성문제).

- 동시성 문제로인해 EntityManager는 상황에 따라 계속 만들어줘야함

- 이러한 만들어주는 역할을 하는 것이 EntitymangerFactory

- ```java
  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");//db당 하나의 엔티티매니저팩토리 생성
  
  //엔티티매니저 팩토리에서 엔티티 매니저 생성.
  EntityManager entityManager = entityManagerFactory.createEntityManager();
  
  //위와 같은 행위는 우리가 설정만 해주면 자동으로 스프링 부트에서 해준다.
  ```











### EntityManager

- ORM을 위해서 나온 것. -> JPA를 위해서 나온 것.

- 테이버베이스의 테이블에 매핑되는 객체가 Entity

  - 이러한 Entity를 관리하는 기능을 **수행하는 객체가  EntityManager**이고, EntityManager는 **요청 쓰레드 1개**에만 제공될 수 있다.
  - EntityManager는 DB와의 **실제 connection을 가지고**  Transaction을 수행하기 때문에 여러 쓰레드가 공유하게되면 동시성 문제가 발생할 수 있다.

- 실제  connection을 가지고 있기 때문에 `em.close()`를 통해서 DB Connection을 반환해야한다.

- 실제로 쿼리가 나가는 시점은 `em.flush()`

  - Transaction이 커밋되는 시점에 `em.flush()`가 자동으로 호출.

  



- ```java
  @PersistenceContext
  private EntityManager entityManager;
  
  //스프링 에서는 위와같은 코드로 스프링 컨테이너에 등록된 빈을 찾아 주입해줌.
  //@PersistenceContext 대신 @Autowired와 동일한 기능이나 스프링 부트 낮은 버전에서는 @Autowired가 동작하지 않을 수 있음.
  ```





### EntityTransaction

- 기본적으로 데이터베이스에 대한 접근은 Transaction 단위로 명령이 처리된다.

- JPA는 이러한 Transcation 단위로 처리되지 않는 명령에 대해서는 `TransactionRequiredException `을 발생

  





### QueryDsl

- Spring Data Jpa는 CRUD 메서드, 쿼리 메서드 기능을 사용하더라도 원하는 조건의 데이터를 수집하기 위해서 JPQL을 사용할 때가 온다.

- 복잡한 로직은 그 만큼 JPQL이 길어지고 문법적인 오류가 발생할 확률이 높다.

- 이러한 것들을 해소하기 위해서 **컴파일 시점**에서 알 수 있도록 하는 **프레임 워크**.

- 기본적으로 QueryDSL은 프로젝트 내의 @Entity 어노테이션을 선언한 클래스를 탐색하고, `JPAAnnotationProcessor`를 사용해 Q 클래스를 생성합니다.

- ```groovy
  implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
      annotationProcessor(
              "javax.persistence:javax.persistence-api",
              "javax.annotation:javax.annotation-api",
              "com.querydsl:querydsl-apt:${queryDslVersion}:jpa")
  }
  ```

  - `querydsl-jpa` : QueryDsl JPA 라이브러리
  - `query-apt` : 쿼리 타입(Q)을 생성할 때 필요한 라이브러리
  - query-apt가 `@Entity, @Id`등의 어노테이션을 알 수 있도록 `javax.persistence`과 `javax.annotataion`을 `annotationProcessor`에 함께 추가.
    - `annotationProcessor`는 **java 컴파일러 플러그인**으로서 **컴파일 단계**에서 어노테이션을 분석 및 처리함으로써 추가적인 파일을 생성할 수 있습니다.



### Query Class

- `JPQLQuery Interface`는 JPQL 쿼리를 위한 `Query Interface`이며
- `JPAQuery Calss`는 `JPQLQuery Interface`를 구현한 클래스이며, 쿼리를 작성하고 실행하는 역할
- `JPQQueryFactory`도 JPAQuery를 생성해주는 factory 클래스입니다.
- 결국 JPAQuery를 사용하든 JPaQueryFactory를 사용하든 JPAQuery를 사용한다. 어떤 것을 사용하냐는 개인 취향.
- JPAQuery와 JPAQueryFactory의 차이점
  - JPAQueryFactory는 select절부터 적을 수 있게 도와줌.
  - JPAQueryFactory는 객체를  new해서 생성하지 않고 팩토리를 통해서 생성하여 향후에 구현 클래스가 변경되어도 해당 코드를 사용하는 클라이언트 코드는 손대지 않아도 되는 장점.
  - 성능 차이는 없음.

### JPAQueryFactory

- 



### 어노테이션

- @Qualfier

  - 동일한 타입을 가진 Bean 객체가 두개를 해결하기 위한 어노테이션

  - ```java
      @Bean
      public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource master,
                                          @Qualifier("slaveDataSource") DataSource slave) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
      
        HashMap<Object, Object> sources = new HashMap<>();
        sources.put(DATASOURCE_KEY_MASTER, master);
        sources.put(DATASOURCE_KEY_SLAVE, slave);
      
        routingDataSource.setTargetDataSources(sources);
        routingDataSource.setDefaultTargetDataSource(master);
      
        return routingDataSource;
      }
      
    @Primary
      @Bean
      public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
      }
    }
    ```

  - 위와 같이 빈을 설정할 때, 이름을 정의한다.

  - 빈을 주입시 `@Qualifier(명칭)`을 붙여서 사용한다.



- @EnableAutoConfiguration
  - @SpringBootApplicaion에 존재하는 어노테이션
  - https://donghyeon.dev/spring/2020/08/01/스프링부트의-AutoConfiguration의-원리-및-만들어-보기/



- @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})

  - DataSource를 커스텀마이징한다고 하면, 위와 같이 exclude하여 한다고 한다.

  - 그러나 `@Conditional()`을 사용해서 충분히 AutoConfiguration을 exclude하지 않아도 된다.

    - ```java
      // DataSourceAutoConfiguration 클래스
      @Configuration(proxyBeanMethods = false)
      @Conditional(EmbeddedDatabaseCondition.class)
      @ConditionalOnMissingBean({DataSource.class, XADataSource.class})
      @Import(EmbeddedDataSourceConfiguration.class)
      protected static class EmbeddedDatabaseConfiguration {
      }
      ```

      - 위 코드는 DataSourceAutoConfiguration 에서는 @ConditionalOnMissingBean({ DataSource.class, XADataSource.class }) 설정을 통해, DataSource클래스의 Bean이 이미 존재한다면, 새로운 DataSource Bean을 생성하지 않는다.



- @EnableTransactionalManager, @EnableJpaRepositories
  - 해당 설정은 `TransactionAutoConfiguration`에서 자동으로 설정이 된다. 그렇기에 불필요한 설정이다. `JpaRepositoriesAutoConfiguration` 와 `@EnableJpaRepositories`는 효과가 동일하기 때문에 삭제해도 된다.



### Native Sql 

- JPQL은 표준 SQL이 지원하는 대부분의 문법과 SQL 함수들을 지원하지만 특정 데이터베이스에 종속적인 기능은 지원하지 않음

- 이러한 이유로 **JPQL을 사용할 수 없을 때**, **JPA**는 **Native SQL**을 통해 SQL을 직접 사용할 수 있는 기능을 제공

- 네이티브  SQL 사용 시 엔티티를 조회하고, JPA가 지원하는 영속성 컨텍스트의 기능을 그대로 사용 가능

- 반면, JDBC API 사용 시 단순히 데이터의 나열을 조회

- 

  ```java
  //결과 타입 정의
  public Query createNativeQuery(String sqlString, class resultClass);
  
  //결과 타입을 정의할 수 없을 경우
  public Query createNativeQuery(String sqlString);
  
  //결과 매핑 사용
  public Query createNativeQuery(String sqlString,	String resultSetMapping);
  
  
  ////////////////////////////////
  //예시
  //엔티티 조회
  String sql = "SELECT ID, AGE, NAME, TEAM_ID" +
    " FROM MEMBER WHERE AGE > ?";
  Query nativeQuery = em.createNativeQuery(sql, Member.class).setParameter(1, 20);
  List<Member> resultList = nativeQuery.getResultList();
  
  
  //값 조회
  
  String sql = "SELECT ID, AGE, NAME, TEAM_ID " +
    "FROM MEMBER WHERE AGE > ?";
  Query nativeQuery = em.createNativeQuery(sql).setParameter(1, 10); 
  List<Object[]> resultList = nativeQuery.getResultList();
  for(Object[] row : resultList) {
    System.out.println("id = " + now[0]);
    System.out.println("age = "now[1]);
    System.out.println("name = "now[2]);
    System.out.println("team_id = "now[3]);}
  
  //결과 매핑 사용
  
  
  
  ```



https://data-make.tistory.com/616

- 결과 매핑 어노테이션





- Named Native SQL













### JPQL(Java Persistence Query Language)

- JPA를 사용하면 엔티티 객체를 중심으로 개발

- 그러므로 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색

- 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된  SQL이 필요

  - 그래서  JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공

- ```java
  //검색
  String jpql= "select m From Member m where m.name like '%hello%'";
  
  List<Member> result = em.createQuery(jpql, Member.class).getResultList();
  
  ```

  - 위의 JPQL에 의해 변환되어(데이터 베이스 방언을 참조하여 DB에 맞는 쿼리로) 실행된 SQL

  - ```sql
    select
        m.id as id,
        m.age as age,
        m.USERNAME as USERNAME,
        m.TEAM_ID as TEAM_ID
    from
        Member m
    where
        m.age>18
    ```

- 결과 조회 API

  - `query.getResultList()`
    - 결과가 하나 이상인 경우, 리스트 반환
  - `query.getSingleResult()`
    - 결과가 정확히 하나, 단일 객체를 반환한다.(하나가 아닐시 예외)

- 파라미터 바인딩

  - 이름 기준

    - ```java
      String jpql= "select m From Member m where m.name = :username";
          Query query = em.createQuery(jpql, Member.class);
          query.setParameter("username", username);
      
          List resultList = query.getResultList();
      ```

  - 위치 기준

    - ```java
      String jpql= "select m From Member m where m.name = ?1";
          Query query = em.createQuery(jpql, Member.class);
          query.setParameter(1, username);
      
          List resultList = query.getResultList();
      ```

- 프로젝션(Select 절에 조회할 대상을 지정하는 것)

  - 엔티티 프로젝션(멤버 조회)
    - `SELECT m FROM MEMBER m ...`
  - 엔티티 프로젝션(멤버 안에 있는 팀 조회)
    - `SELECT m.team FROM Member m ...`
  - 단순 값 프로젝션
    - hibernate에서 지원을 해서  username, age로 쓸 수 있지만
    - 공식적으로  m.username, m.age로 접근
    - `SELECT m.username, m.age FROM Member m ...`
  - new 명령어
    - **단순 값을  DTO로 바로 조회한다.**
    - new 패키지명,  DTO를 넣고 생성자처럼 사용해서  DTO로 바로 반환 가능
    - `SELECT new jpabook.jpal.UserDto(m.usernmae, m.age) FROM Member m ...`

- 페이징 API

  - 



- 집합과 정렬

  - 기본적인 집합 명령어 다 동작

    - ```java
      String jpql = "select 
          COUNT(m),   //회원수
          SUM(m.age), //나이 합
          AVG(m.age), //평균 나이
          MAX(m.age), //최대 나이
          MIN(m.age)  //회소 나이
      from Member m";
      ```

  - GROUP BY, HAVING

  - ORDER BY

- 조인

  - 내부 조인

    - 멤버 내부의 팀에 m.team으로 접근

    - ```java
      String jpql = "select m FROM Member m [INNER] JOIN m.team t";
      ```

  - 외부 조인

    - ```java
      String jpql = "select m FROM Member m LEFT [OUTER] JOIN m.team t";
      ```

  - 세타 조인

    - 막? 조인이라고도 한다. 연관관계 상관 없이 유저명과 팀의 이름이 같은 경우 찾아라 라는 쿼리 날릴 수 있다. 이런 조인을 세타 조인

      - ```java
        String jpql = "select COUNT(m) FROM Member m, Team t WHERE m.username = t.name;
        ```

- 페치 조인

  - 현업에서 굉장히 많이 쓰인다. FetchType을 LAZY로 다 세팅 하고 쿼리 튜닝할 때 한꺼 번에 조회가 필요한 경우 페치조인을 사용
  - 엔티티 객체 그래프를 한번에 조회하는 방법
  - 별칭을 사용할 수 없다
  - JPQL
    - 멤버를 조회할 때, 팀까지 같이 조회한다.
    - `select m from Member m join fetch m.team`
  - SQL
    - `SELECT M.*, T.* FROM MEMBER T INNER JOIN TEAM T ON M.TEAM_ID = T.ID`



- 기타
  - 서브 쿼리 지원
  - EXISTS, IN
  - BETWEEN, LIKE, IS NULL

- JPQL 기본 함수

  - CONCAT
  - SUBSTRING
  - TRIM
  - LOWER, UPPER
  - LENGTH
  - LOCATE
  - ABS, SQRT, MOD
  - SIZE, INDEX(JPA 용도)

- Named 쿼리 - 정적 쿼리

  - **미리 정의**해서 **이름을 부여**해두고 사용하는 JPQL

  - 어노테이션,  XML에 정의

  - 애플리케이션 로딩 시점에 초기화 후 재사용

  - 애플리케이션 로딩 시점에 쿼리를 검증

  - ```java
    //어노테이션
    @Entity
    @NamedQuery(
      name = "Member.findByUsername",
      query = "select m from Member m where m.username = :username"
    )
    public class Member{
      //...
    }
    
    
    //////////////////////////////////
    List<Member> resultList = 
      em.createNamedQuery("Member.findByUsername", Member.class)
      .setParameter("username", "회원1")
      .getResultList();
    ```

  - 쓰는 이유

    - em.craeteQuery()안에 넣어서 그냥 사용할 수 있다. 그러나
    - 문자열 자체이기 때문에 에러 포인트가 많다. 사용자 요청이 오기 전까지 쿼리가 실행되지 않으니 모른다.
    - 배포하기 전에 문제를 잡을 수 있다
    - Spring Data JPA를 사용할 때, @Query 어노테이션이 이 Named쿼리로 동작하게 된다.
    - 런타임 에러는 정말 위험하다

  - Named 쿼리 환경에 따른 설정

    - XML이 항상 우선권을 가짐
    - 애플리케이션 운영 환경에 따라 다른  XML을 배포할 수 있다.

  - Named 쿼리 vs @Query

    - Named 쿼리는 Entity가 쿼리까지도 담당하게 되어서 단일 책임 원칙도 벗어남
    - Named 쿼리는 엔티티에 쿼리가 많이 쌓인다는 단점 존재
    - Spring data JPA는 명명규칙을 사용해서  NamedQuery를 사용할 수 있다. 그러나 파라미터가 3개 이상이 되고 order by 등 쿼리가 복잡해지면 메서드명이 길어져 복잡해 진다.
    - 이럴 때 @Query를 사용하자









--------------------------------

# 성능 개선



## Querydsl

### Select 성능 개선

- `exist`메소드 대체하기

  - Querydsl의  exist는 SQL의 exist를 사용하지 않고 성능상 문제가 발생할 수 있는 count를 사용하여 수행

  - 그러기에 limit 1로 조회를 제한한다.

  - ```java
     @Transactional(readOnly = true)
        public Boolean exist(Long idx) {
            Integer fetchOne = queryFactory
                    .selectFrom(book)
                    .where(
                            book.idx.eq(idx)
                    )
                    .fetchFirst();
    ```

- Cross Join 회피하기

  - 일반적으로 Cross Join은 두 테이블의 나올 수 있는 모든 조합을 조회하기에 성능에 좋지 않다.

  - **JPA와 QueryDsl**을 사용하면서, 명시적으로  Join하지 않고 **Where절의 동치로 묵시적 Join**하는 경우 **Cross join** 발생(Hiberante의 이슈로 인해  Spring Data JPA를 사용하는 이상 어쩔 수 없음)

  - **명시적 Join으로  Cross Join을 방지할 수 있다.**

  - ```java
    @Transactional(readOnly = true)
        public List<Book> crossJoin() {
            return queryFactory
                    .selectFrom(book)
                    .where(
                            book.idx.gt(book.lender.idx)//묵시적 Join, Cross Join
                    )
                    .fetch();
        }
        
        @Transactional(readOnly = true)
        public List<Book> innerJoin() {
            return queryFactory
                    .selectFrom(book)
                    .innerJoin(book.lender, lender)//명시적 Join
                    .where(
                            book.idx.gt(book.lender.idx)
                    )
                    .fetch();
        }
    ```

    

- Entity가 아닌  Dto를 사용하기
  - 엔티티를 사용할 경우 단점
    - 모든 컬럼을 가져오기에 로직에서 불필요한 컬럼도 가져온다.
    - 영속성 컨텍스트의 캐시 기능을 반드시 사용하게 된다.
    - **@OneToOne 관계에 있는 엔티티 정보도 울며 겨자먹기로 가져와야함**
      - **참고로 @OneToOne 양방향 Lazy Loading로 설정하여도  Eager Loading로 실행된다.**
        - 이유는 양방향이므로  연관관계 주인(mappedBy가 없는 곳(외래키가 있는 곳))이 있다.
        - 
      - 단방향은 Lazy Loading이 잘 실행됨.
  - 엔티티 변경이 필요한 경우 Entity 조회.
  - 대량의 데이터 조회가 필요하거나 성능 최적화를 고강도 하고 싶은 경우  Dto 조회
  - 



- Group By 최적화

  - Mysql에서 Group By를 실행하면 FileSort가 반드시 발생한다.

  - 정렬이 필요없는 경우에도 대량의 정렬한다면 성능 손실이 생긴다.

  - 이를 해결하기 위해 Mysql에서 **order by null을 사용하면  FileSort가 제거되는 기능을 지원**

  - 하지만 **QueryDsl**에선 **order by null을 지원하지 않기 때문에** 직접 구현해야함

  - ```java
    public class OrderByNull extends OrderSpecifier {
    
        public static final OrderByNull DEFAULT = new OrderByNull();
        
        private OrderByNull() {
        	super(Order.ASC, NullExpression.DEFAULT, default);
        }
    }
    
    // 사용
    
    ...
    .orderBy(OrderByNull.DEFAULT)
    .fetch();
    ```

  - 조회 결과가 100건 이하의 소량의 경우 정렬하는 것을 추천.
  - 페이징일 경우엔 Order By null을 사용할 수 없다.



- 커버링 인덱스
  - 커버링 인덱스란?
    - 쿼리를 충족시키는데 필요한 모든 컬럼을 포함하고 있는 인덱스
    - select / where / order by / group by 등에서 사용되는 모든 컬럼이 인덱스 안에 있다.
  - 문제는 JPQL은 from 절의 서브쿼리를 지원하지 않기에 from 서브 쿼리를 사용하는 경우 커버링 인덱스를 적용할 수 없다.
  - 우회하는 방법
    - **조회 쿼리 하나를 2개로 쪼갠다.**
  - 참고로 SubQuery는 쿼리에서의 **안티패턴**이라고 생각
  - 개인적으로 SubQuery가 필요하다면
    - Join으로 해결할 순 없는지
    - 어플리케이션에서 처리할 수 없는지
    - 쿼리를 나눠서 실행할 수 없는지 등을 고려





### Update 성능 개선



- Batch Update 최적화(더티체킹 하지 않기)
  - JPA는 영속성 컨텍스트의 1차 캐시에 저장된 엔티티 정보를 트랜잭션 커밋 직전에 비교(더티체킹)하여, 변경된 점이 존재하다면 Update쿼리를 날린다.
  - 이러한 이유로 대량의 데이터를 수정할 때 영속성 컨텍스트에 존재하는 모든 엔티티의 변경사항을 체크하는 문제가 발생
  - 단점은 더티체킹을 하지 않기에 JPA에서 사용하는 캐시도 갱신되지 않습니다. 캐시를 사용하는 로직이 있으면 캐시를 직접 갱신할 필요가 있다.
  - Querydsl update는 대량의 데이터를 일괄로 업데이트 처리할 때 사용하면 좋습니다. 특히 JPA 캐시 갱신이 필요 없는 경우 말이죠.





### Insert 성능 개선



- JdbcTemplate으로 Batch Insert 사용하기

  - JDBC는 `rewriteBatchedStatements`옵션으로  Insert 합치기 기능을 제공
  - 그러나 JPA에서  ID 생성 전략을  IDENTITY(AUTO_INCREMENT) 방식을 사용하면  Batch Insert가 비활성화됨.
  - 새로 할당할 PK 값을 미리 알 수 없기 떄문입니다.(DB에 INSERT되고 나서야  PK가 할당됨)
  - 이는  Hibernate가 채택한 flush 방식인 'Transactional Write behind'와 충돌
  - 즉, 엔티티를 생성할 경우  Insert 쿼리를 쓰기 지연 SQL 저장소에 모아놓았다가, 트랜잭션 커밋 직전에 한꺼번에 전송하는 데 이 방식과 Batch Insert가 충돌한다는 것.

  - 따라서 **IDENTITY가 아닌  SEQUENCE나 TABLE 방식**을 사용하여 Batch Insert를 날리거나, **JPA를 벗어나 JdbcTemplate의 batchUpdate**를 사용하여 Batch Insert를 날리는 방법.



- Type safe하게 Batch Insert 사용

  































------------------

DataSource

- 인터페이스

- Spring은 DataSource 구현체로 DB와의 연결을 획득한다.

- 즉, 커넥션 풀에서 커넥션을 얻어오고 반납한다.

- ```java
  public Connection getConnection(final long hardTimeout) throws SQLException
  {
    suspendResumeLock.acquire();
    final long startTime = currentTime();
  
    try {
      long timeout = hardTimeout;
      do {
        PoolEntry poolEntry = connectionBag.borrow(timeout, MILLISECONDS);
        if (poolEntry == null) {
          break; // We timed out... break and throw exception
        }
  
        final long now = currentTime();
        if (poolEntry.isMarkedEvicted() || (elapsedMillis(poolEntry.lastAccessed, now) > aliveBypassWindowMs && !isConnectionAlive(poolEntry.connection))) {
          closeConnection(poolEntry, poolEntry.isMarkedEvicted() ? EVICTED_CONNECTION_MESSAGE : DEAD_CONNECTION_MESSAGE);
          timeout = hardTimeout - elapsedMillis(startTime);
        }
        else {
          metricsTracker.recordBorrowStats(poolEntry, startTime);
          return poolEntry.createProxyConnection(leakTaskFactory.schedule(poolEntry), now);
        }
      } while (timeout > 0L);
  
      metricsTracker.recordBorrowTimeoutStats(startTime);
      throw createTimeoutException(startTime);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new SQLException(poolName + " - Interrupted during connection acquisition", e);
    }
    finally {
      suspendResumeLock.release();
    }
  }
  ```





JDBC(Java Database Connectivity)

- 데이터베이스에 접근할 수 있게 해주는 자바에서 제공하는 API(Application Programming Interface)
- 불편함이 존재
  - JDBC Driver 로드
  - 유 dusruf



JdbcTemplate

- Spring에서 제공하는 클래스로 JDBC API의 불편함을 해소시켜주는 역할
  - JDBC API의 불편함.
    - DB연결, 트랜잭션, 
    - 이러한 반복적인 루틴을 알아서 해준다.
- 내부적으로 JDBC API의 루틴이 정의되어 있다. 
  - 즉, 우리는 쿼리만 작성하면 JDBC API의 불편함은 알아서 해준다.
- 

- Spring 





JPA(Java Persistence API)

- 자바에서 만듬.
- 이것도 JdbcTemplate과 마찬가지로 내부적으로 JDBC API의 루틴이 정의되어 있다.



Spring Data Jpa

- Spring Data JPA가 JPA를 추상화 시킨 Repository 인터페이스 제공.
  - 추상화 시킨 Repository 제공 -> Repositroy의 내부적으로 JPA를 사용함
    - 사용자가 Repository 인터페이스에 정해진 규칙대로 메소드를 입력시
    - Spring이 알아서 JPA를 사용해 메소드 이름에 적합한 쿼리를 날려준다.
- JPA + JDBC API



DAO -> JDBC API 



Java

- JDBC API
- JPA

Spring

- JdbcTemplate
- Spring Data JPA









































































