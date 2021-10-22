---

---



# Spring-Data-JPA 연동



#### 스프링 데이터 JPA 의존성 추가

```xml
 <dependency>
<groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
```



#### 스프링 데이터 JPA 사용하기

-  @Entity 클래스 만들기

-  Repository 만들기

  

#### 스프링 데이터 리파지토리 테스트 만들기

- H2 DB를 테스트 의존성에 추가하기
- @DataJpaTest (슬라이스 테스트) 작성



```
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot
spring.datasource.username=jungwoo
spring.datasource.password=pass
```

스프링에게 내가 만든 Database의 정보를 알려준다.

```java
package com.example.jpalink.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Account {
    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(username, account.username) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
```

@Entity는 객체와 테이블 맵핑하는 애노테이션이다. @Entity는 JPA가 관리하고 

@GeneratedValue를 사용하면 Repository를 생성할 때, 자동으로 값을 생성해준다. 여기서는 id를 자동으로 생성.

@Id는 기본키를 설정.





```java
package com.example.jpalink.Account;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}

```

 Repository도 생성.

```java
package com.example.jpalink.Account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {
        Account account = new Account();
        account.setUsername("jungwoo");
        account.setPassword("pass");

        Account newAccount = accountRepository.save(account);

        assertThat(newAccount).isNotNull();

        Account existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        assertThat(existingAccount).isNotNull();

        Account nonExistingAccount = accountRepository.findByUsername("kim");
        assertThat(nonExistingAccount).isNull();

    }

}
```

@DataJpaTest는 슬라이싱 테스트이다.  슬라이싱 테스트는 지금 현재 Repository에 대한 테스트니깐 Repository와 관련된 빈만 등록해서 테스트 하는 것이 슬라이싱 테스트.

위와 같이 비어있는 테스트를 돌릴 시, 위에 있는 빈들이 잘 등록이 되는지 테스트 애플리케이션이 잘 실행되는지 확인할 수 있으므로 확인하는 것이 좋다.

슬라이싱 테스트를 할 경우에는 인베디드 데이터베이스(인메모리)가 반드시 필요하다. 그러므로 아래와 같이 h2 의존성을 추가한다.

```xml
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>
```

테스트가 인메모리 데이터베이스로 돌아간다.

Database를 postgres를 사용하였다. 테스트가 아닌 곳에서 돌리면 properties에서 설정한 곳에서 postgres정보를 가져와서 

