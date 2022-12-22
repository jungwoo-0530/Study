# MongoDB 집계(Aggregation) 프레임워크

https://spidyweb.tistory.com/190









# Spring MongoDB Aggregation

## Aggregation

<img src="img/MongoDB 집계 프레임워크/Screenshot of MongoDB Compass (2022-11-26 6-33-11 PM).png" alt="Screenshot of MongoDB Compass (2022-11-26 6-33-11 PM)" style="zoom:50%;" />



### GroupOperation

- ```java
  GroupOperation groupOperation = Aggregation.group("keyword");
  ```

  <img src="img/MongoDB 집계 프레임워크/Screenshot of Typora (2022-11-26 6-33-30 PM).png" alt="Screenshot of Typora (2022-11-26 6-33-30 PM)" style="zoom:50%;" />

  - 위와 같이 keyword를 기준으로 그룹화를 해서 보여준다. 

- ```java
  GroupOperation groupOperation = Aggregation.group("keyword").count().as("cnt");
  ```

<img src="img/MongoDB 집계 프레임워크/Screenshot of Typora (2022-11-26 6-36-03 PM).png" alt="Screenshot of Typora (2022-11-26 6-36-03 PM)" style="zoom:50%;" />

위와 같이 keyword로 그룹핑하고 그 그룹핑안에 있는 개수를 세서 cnt로 반환. 

위 상태에서 _id를 바꾸고싶다면? ProjectionOpreation을 사용해서 바꾼다.

```java
GroupOperation groupOperation = Aggregation.group("keyword").count().as("cnt");

ProjectionOperation projectionOperation = Aggregation.project().andExclude("_id").and("_id").as("keyword");

Aggregation aggregation = Aggregation.newAggregation(groupOperation, projectionOperation);//오퍼레이션 순서 중요
```

<img src="img/MongoDB 집계 프레임워크/Screenshot of Typora (2022-11-26 6-40-41 PM).png" alt="Screenshot of Typora (2022-11-26 6-40-41 PM)" style="zoom:50%;" />

- 아까 그룹핑할때 생긴 값(cnt)를 추가.

```java
GroupOperation groupOperation = Aggregation.group("keyword").count().as("cnt");
    ProjectionOperation projectionOperation = Aggregation.project().andExclude("_id").and("_id").as("keyword").and("cnt").as("cnt");
```

<img src="img/MongoDB 집계 프레임워크/Screenshot of IntelliJ IDEA (2022-11-26 6-41-17 PM).png" alt="Screenshot of IntelliJ IDEA (2022-11-26 6-41-17 PM)" style="zoom:50%;" />



### ProjectionOperation



<img src="img/MongoDB 집계 프레임워크/Screenshot of MongoDB Compass (2022-11-26 6-47-42 PM).png" alt="Screenshot of MongoDB Compass (2022-11-26 6-47-42 PM)" style="zoom:50%;" />

- 기본적인 프로젝션

  - ```java
    Aggregation.project().and("domain").as("domain")
            .and("hit").as("hit");
    ```

  - <img src="img/MongoDB 집계 프레임워크/Screenshot of Typora (2022-11-26 6-48-57 PM).png" alt="Screenshot of Typora (2022-11-26 6-48-57 PM)" style="zoom:50%;" />

- _id 필드 제거.

  - ```java
    Aggregation.project().and("domain").as("domain")
            .and("hit").as("hit").andExclude("_id");
    ```

    <img src="img/MongoDB 집계 프레임워크/Screenshot of Typora (2022-11-26 6-49-38 PM).png" alt="Screenshot of Typora (2022-11-26 6-49-38 PM)" style="zoom:50%;" />

### SortOperation

- 아까 GroupOperation에서 나온 결과를 cnt 순서대로 정렬 내림차순

- ```java
  GroupOperation groupOperation = Aggregation.group("keyword").count().as("cnt");
  
  ProjectionOperation projectionOperation = Aggregation.project().andExclude("_id").and("_id").as("keyword").and("cnt").as("cnt");
  
  SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "cnt");
  
  Aggregation aggregation = Aggregation.newAggregation(groupOperation, projectionOperation, sortOperation);
  ```

  <img src="img/MongoDB 집계 프레임워크/Screenshot of IntelliJ IDEA (2022-11-26 6-43-37 PM).png" alt="Screenshot of IntelliJ IDEA (2022-11-26 6-43-37 PM)" style="zoom:50%;" />



### LimitOperation

- SortOperation에서 하던 것을 이어서 5개로 짜르기

- ```java
  GroupOperation groupOperation = Aggregation.group("keyword").count().as("cnt");
  
  ProjectionOperation projectionOperation = Aggregation.project().andExclude("_id").and("_id").as("keyword").and("cnt").as("cnt");
  
  SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "cnt");
  
  LimitOperation limitOperation = Aggregation.limit(5);
  
  Aggregation aggregation = Aggregation.newAggregation(groupOperation, projectionOperation, sortOperation, limitOperation);
  ```

  <img src="img/MongoDB 집계 프레임워크/Screenshot of IntelliJ IDEA (2022-11-26 6-44-53 PM).png" alt="Screenshot of IntelliJ IDEA (2022-11-26 6-44-53 PM)" style="zoom:50%;" />



### MatchOperation

- Criteria를 섞어서 사용 가능하다.

- ```java
  MatchOperation matchOperation = Aggregation.match(Criteria.where("userId").is("admin"));
  ```

  

### CountOperation



### Aggregation.newAggregation()의 순서

- `Aggregation.newAggregation()`의 메소드 인자로 들어가는 오퍼레이션들은 순서가 굉장히 중요하다.
- 프로젝션을 먼저하고 그룹핑을 한다면 프로젝션한 결과에서 그룹핑을 실시한다.
- 그러므로 프로젝션을 한 필드가 그룹핑 키값에 포함이 안된다면 에러가 발생한다.





### AddFieldOperation (필드에 없는 값 Projection하기)

- 결과에 원하는 필드를 추가해준다. DB에는 영향이 없다.

- ```java
  rojectionOperation projectionOperation1 = Aggregation.project().and("domain").as("domain");
      AddFieldsOperation addFieldsOperation = Aggregation.addFields().addField("test").withValueOf(1).build();
  
  Aggregation aggregation1 = Aggregation.newAggregation(projectionOperation1, addFieldsOperation);
  ```

  <img src="img/MongoDB 집계 프레임워크/Screenshot of Typora (2022-11-26 7-05-24 PM).png" alt="Screenshot of Typora (2022-11-26 7-05-24 PM)" style="zoom:50%;" />







