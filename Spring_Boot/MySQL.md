---
title:  "MySQL 설정"
excerpt: ""

categories:
  - Spring-Boot
tags:
  - [Spring-Boot, Web, Mvc]
 
date: 2021-10-18
last_modified_at: 2021-10-18
---



# MySQL 설정

#### 지원하는 DBCP

1. **HikariCP** **(**기본**)**
   - https://github.com/brettwooldridge/HikariCP#frequently-used
2. Tomcat CP
3. Commons DBCP2

#### DBCP 설정

- **spring.datasource.hikari.\***
- spring.datasource.tomcat.
- *spring.datasource.dbcp2.*

#### MySQL 커넥터 의존성 추가



#### MySQL 추가 (도커 사용)

- docker run -p 3306:3306 --name **mysql_boot** -e MYSQL_ROOT_PASSWORD=**1** -e

  MYSQL_DATABASE=**springboot** -e MYSQL_USER=**keesun** -e

  MYSQL_PASSWORD=**pass** -d mysql

- docker exec -i -t mysql_boot bash

- mysql -u root -p



#### MySQL용 Datasource 설정

- spring.datasource.url=jdbc:mysql://localhost:3306/springboot?useSSL=false
- spring.datasource.username=keesun
- spring.datasource.password=pass



#### MySQL 접속시 에러

 MySQL 5.* 최신 버전 사용할 때

| 문제 | Sat Jul 21 11:17:59 PDT 2018 WARN: Establishing SSL connection without server's identity verification is not recommended. **According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set.** For compliance with existing applications not using SSL the **verifyServerCertificate property is set to 'false'**. You need either to explicitly disable SSL by setting **useSSL=false**, or set **useSSL=true and provide truststore** for server certificate verification. |
| ---- | ------------------------------------------------------------ |
| 해결 | jdbc:mysql:/localhost:3306/springboot?**useSSL=false**       |

  MySQL 8.* 최신 버전 사용할 때

| 문제 | com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException : Public Key Retrieval is not allowed |
| ---- | ------------------------------------------------------------ |
| 해결 | jdbc:mysql:/localhost:3306/springboot?useSSL=false&**allowPublicKeyRetr ieval=true** |

