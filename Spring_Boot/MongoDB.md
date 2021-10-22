---

---



# MongoDB

#### MongoDB는 JSON 기반의 도큐먼트 데이터베이스입니다.

#### 의존성 추가

- spring-boot-starter-data-mongodb

#### MongoDB 설치 및 실행 (도커)

- docker run -p 27017:27017 --name mongo_boot -d mongo
- docker exec -i -t mongo_boot bash
- mongo

#### 스프링 데이터 몽고DB

- MongoTemplate
- MongoRepository
- 내장형 MongoDB (테스트용)
  - de.flapdoodle.embed:de.flapdoodle.embed.mongo

● @DataMongoTest







```java
package com.example.demo7mongodb.account;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;

    private String username;

    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

```

Document 애노테이션을 사용하고 collection의 이름을 줄 수 있다. mongodb에서는 collection이 테이블이다.

```java
package com.example.demo7mongodb;

import com.example.demo7mongodb.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Demo7MongodbApplication {

    @Autowired
    MongoTemplate mongoTemplate;

    public static void main(String[] args) {

        SpringApplication.run(Demo7MongodbApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            Account account = new Account();
            account.setEmail("aaa@bbb");
            account.setUsername("jungwoo");

            mongoTemplate.insert(account);
        }
    }

}

```

`jdbcTemplate` 처럼 `MongoTemplate` 이 자동설정에 의해 이미 Bean으로 등록되어있다.

터미널에서  

 docker exec -i -t mongo_db bash 를 사용하여 bash모드로 접속하고 mongo 커맨드를 사용하여 mongo 쉘에 접속하여 확인한다.



확인 방법은

1. db
2. use test
3. db.collection이름.find({})



MongoRepository

AccountRepository를 만들어주고, MongoRepository를 extends한다. MongoRepository의 값은 이용할 클래스와, key타입으로 준다.

```java
package com.example.demo7mongodb.account;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

}

```

```java
package com.example.demo7mongodb;

import com.example.demo7mongodb.account.Account;
import com.example.demo7mongodb.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Demo7MongodbApplication {

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {

        SpringApplication.run(Demo7MongodbApplication.class, args);
    }


    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            Account account = new Account();
            account.setUsername("aa");
            account.setEmail("aaa@bbb");
            account.setUsername("jungwoo");

            accountRepository.insert(account);

            System.out.println("finished");
        };
    }

}

```

AccountRepository를 주입받아 insert한다.



내장형 MongoDB

테스트가 필요할 때 운영용 MongoDB에 데이터를 넣고 빼면 문제가 발생할 수 있다.

이것을 위해 내장형 MongoDB가 존재한다.

먼저 아래의 의존성을 추가해준다. 그러면 슬라이싱 테스트를 작성할 수 있다.

```xml
        <dependency>
            <groupId>de.flapdoodle.embed</groupId>
            <artifactId>de.flapdoodle.embed.mongo</artifactId>
            <scope>test</scope>
        </dependency>
```

그 후, 테스트를 만들어준다.



```java
package com.example.demo7mongodb.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByEmail(){
        Account account = new Account();
        account.setUsername("jungwoo");
        account.setEmail("am7227@ajou.ac.kr");

        accountRepository.save(account);

        Optional<Account> byId = accountRepository.findById(account.getId());
        assertThat(byId).isNotEmpty();

        Optional<Account> byEmail = accountRepository.findByEmail(account.getEmail());
        assertThat(byEmail).isNotEmpty();
        assertThat(byEmail.get().getUsername()).isEqualTo("jungwoo");


    }


}
```

강의에서는 @RunWith 애노테이션과 @DataMongoTest을 사용하여 테스트를 하였지만 Junit5로 넘어오면서 @DataMongoTest가 이미 가지고 있으므로 생략.