# Gradle





`query-apt` : 쿼리타입(Q)를 생성할 떄 필요한 라이브러리

`io.spring.dependency-management` : Gradle 플러그인으로 해당 플러그인을 사용하면 버전을 명시하지 않아도 Management에서 관리되고 있는 버전으로 종속성관리를 해준다.



```

//gradle 플러그인 설정은 아래와 같이.
plugins {
    id 'org.springframework.boot' version '2.6.3'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

group = 'com.jungwoo'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
// mavenCentral 레퍼지토리 사용 https://mvnrepository.com/repos/central
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'io.jsonwebtoken:jjwt:0.9.1'
    implementation 'com.querydsl:querydsl-jpa'

    implementation 'io.springfox:springfox-boot-starter:3.0.0'


    compileOnly 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation('com.h2database:h2')
    //email
    implementation 'org.springframework.boot:spring-boot-starter-mail'

		//image
    implementation 'com.github.downgoon:marvin:1.5.5'
    implementation 'com.github.downgoon:MarvinPlugins:1.5.5'

    compileOnly 'org.projectlombok:lombok'

    developmentOnly 'org.springframework.boot:spring-boot-devtools'

    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'

    annotationProcessor 'org.projectlombok:lombok'



    // Querydsl
    implementation 'com.querydsl:querydsl-jpa'
    // Querydsl JPAAnnotationProcessor 사용 지정
    annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa"
    // java.lang.NoClassDefFoundError(javax.annotation.Entity) 발생 대응
    annotationProcessor "jakarta.persistence:jakarta.persistence-api"
    // java.lang.NoClassDefFoundError(javax.annotation.Generated) 발생 대응
    annotationProcessor "jakarta.annotation:jakarta.annotation-api"


    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}

```





- Dependency-management 사용하기

```

plugins {
    id 'org.springframework.boot' version '2.6.3'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa"
```

`${dependencyManagement.importedProperties['querydsl.version']}` 이렇게 사용.







## Project Object



- configuration
  - build.gradle 내부에서 사용되는 설정
  - 목록
    - compileOnly
    - runtimeOnly
    - implementation
    - annotationProcessor : 어노테이션 프로세서로써 컴파일 시 사용되는 의존성을 주입한다.
    - testImplementation





## Java plugin

```groovy
//두가지 방법.
apply plugin: 'java'

plugin{
  id 'java'
}
```



- SourceSets

  - 함께 컴파일과 실행되는 소스파일들의 그룹

  - 컴파일 클래스 패스와 런타임 클래스 패스와 관련

  - build target으로 잡히게 한다.

  - 기본 Java Source Set

    - main : 실제 작동 소스코드, 컴파일해서 JAR파일로 들어간다
    - test : 단위 테스트 소스코드, 컴파일해서 JUnit으로 들어간다

  - ```groovy
    // sourceSets 기본형태
    sourceSets {
      main {
        java {
          srcDir 'src/main/java'
        }
      }
    }
    // sourceSets 추가
    sourceSets {
      main {
        java {
          srcDir 'src/main/java'
          srcDir 'src/my'
        }
      }
    }
    // 특정 디렉토리를 build의 target에서 뺀다.
    sourceSets {
      main {
        java {
          exclude 'src/main/java/exet/**'
        }
      }
    }
    ```



