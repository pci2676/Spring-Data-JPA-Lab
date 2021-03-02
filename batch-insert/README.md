# Spring Data Jpa Bulk Insert 하기

나는 내가 모르는 기술을 만나면 반갑기도 하지만 엄청 겁을 먹어버리는데 처음 맡은 업무에서 파트장님이 '이 부분을 JPA 를 이용해서 bulk insert 하도록 변경해주시면 됩니다!' 하셨을 때 살짝 식은땀이
났다.

~~'헉 나 할줄 모르는데..'~~

다행히 업무를 주시기 전에 어떻게 풀어나가야 하는지 팀 기술 세미나를 통해서 가이드를 해주셨고 무사히 마칠 수 있었다!

## application.yml 설정

application.yml 에 필요한 설정은 다음과 같다.

```yml
spring:
  jpa:
    properties:
      hibernate:
        jdbc.batch_size: 100
        order_inserts: true
        order_updates: true
  datasource:
    hikari:
      data-source-properties:
        rewriteBatchedStatements: true
```

### JDBC batching

[공식 문서](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#batch-jdbcbatch)

`hibernate.jdbc.batch_size` 를 설정해줘야한다.  
JDBC batching 에 대해 먼저 알아보면 여러개의 SQL statement 을 하나의 구문(Single PreparedStatement)으로 처리한다는 것을 의미한다. 즉, 같은 일을 하는 1000개의 쿼리가
발생할 것을 1개의 쿼리로 처리해 버릴 수 있는 것을 의미하는데, 1000번 DB 와 통신하는 부분을 1번만 통신하도록 개선하게 되니까 네트워크 자원의 낭비가 덜하게 되고 성능이 개선된다.  
여기서 `batch_size` 는 Hibernate 가 최대 몇 개의 statement 를 묶어서 처리할지 지정한다. 만약 값을 100이라고 지정한 뒤 1000개의 insert statement 를 호출한다면,
100개씩 10번의 insert 가 발생하게 된다.

이렇게만 하면 batch 설정이 끝나는줄 알았지만 그렇지 않다.

### rewriteBatchedStatements

[공식 문서](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-connp-props-performance-extensions.html)

`rewriteBatchedStatements: true` 로 설정해줘야 한다.  
MySQL 의 경우 위와 같은 설정이 있어야지 실제로 여러개의 insert 쿼리를 하나의 쿼리로 합쳐준다.

### order inserts, updates

insert 하는 것과 update 하는 것의 순서를 말하는데

트랜잭션에서 서비스 코드가 부모 엔티티 save 후 자식 엔티티를 save 하는 순서가 있다고 가정하자.

부모 엔티티인 a1, a2 가 있고 자식 엔티티가 b1, b2 가 있을 때 위 설정을 하지 않으면 트랜잭션이 끝날때 다음과 같은 순서로 영속화 한다.

```
insert into A values a1
insert into B values b1
insert into A values a2
insert into B values b2
```

A1, A2 는 같은 insert 쿼리를 사용하고 b1, b2 는 같은 insert 쿼리를 사용한다. 즉, 묶어서 처리할 수 있다는 말인데 옵션이 되어있지 않으면 일괄 처리를 하지 못한다.  
옵션을 설정할 경우에는 다음과 같이 일괄 처리가 된다.

```
insert into A values (a1, a2)
insert into B values (b1, b2)
```

## ID 생성전략 설정

JPA 의 ID 생성 전략(GeneratedValue)중 IDENTITY 는 save 함과 동시에 flush 를 해서 바로 1차캐시에 save 된 엔티티를 저장하게 된다.

따라서 SEQUENCE 전략이나 TABLE 전략을 사용해야 하는데 Mysql 은 SEQUENCE 전략을 지원하지 않으므로 TABLE 전략을 사용해야 한다.

이때 TABLE 전략을 사용하면서 Sequence Table 을 사용하게 되는데 옵션인 generator 에 해당 sequence 값의 키(generator name) 역할을 하는 값을 적어주면 된다.

(example)

```java
@Id
@GeneratedValue(
        strategy = GenerationType.TABLE
        , generator = "GENERATOR_NAME"
)
```

### TABLE GENERATOR

위에서 Sequence Table 에서 키에 해당하는 sequence generator 가 필요하다고 했는데 이에 table generator 에 대한 정의도 Entity 에 해주면된다.

(example)

```java
@TableGenerator(
        name = "GENERATOR_NAME",
        table = "sequence_table",
        pkColumnName = "sequence_name",
        valueColumnName = "next_val",
        allocationSize = 1000
)
```

name 은 똑같이 sequence generator 이름을 적어주면 되고, table 은 시퀀스 테이블로 사용할 테이블의 이름을 적어주면된다.  
pkColumnName 은 generator 이름을 저장할 컬럼의 이름이고 valueColumnName 은 sequence 값을 저장할 컬럼의 이름이다.  
allocationSize 가 중요한데 위와 같이 1000으로 지정하면 1건을 저장하든 1000건을 지정하든 무조건 1000개의 sequence(Entity 의 아이디로 쓰일 값)을 미리 할당 받는다. 따라서
1000처럼 너무 큰 값을 잡지 말고 해당 엔티티가 평균적으로 몇건씩 insert 되는지 파악하고 적절한 값을 적어주는 것이 좋다.

### hibernate.id.new_generator_mappings

`hibernate.id.new_generator_mappings: false` 설정이 있는데 스프링 부트 1.5 에서 2로 올라올 때 hibernate 와의 호환성을 위해 있는 옵션이다. 근데 해당 옵션이
false 가 되어있으면 Sequence Table 의 sequence 생성 전략이 조금 바뀐다. allocation size 가 10 이고 20건을 insert 하면 sequence table 에는 2가
기록되어있는데, 1은 1 ~ 10, 2는 11 ~ 20 이렇게 `(value * (allocation size -1 )) ~ (value * allocation size)` 과 같이 계산이 되서 실제 Entity 가 가지고
있는 키값과 다르기 때문에 계산을 해야한다. 대부분 스프링 2.x 를 쓸테니 해당 옵션은 기본이 true 라 신경 쓸 필요 없겠지만 알아두면 좋을 것 같다.

## 맺으며

예전에 JPA 책 읽으면서 나는 TABLE 전략 죽어도 안쓸줄 알았는데 세상에 절대라는 건 없었다.. 그리고 정리하기 무색하게 이미 많은 선배 개발자 분들이 무지 많이 정리해두셨었다 끆끄.. 그래도 내가 써봤던
기술을 까먹지 않고 내 것으로 만들기 위해서 정리해 둔다!
