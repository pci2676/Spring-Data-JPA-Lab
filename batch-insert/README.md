## Spring Data Jpa 에서 bulk insert 하기

### 환경
Java 1.8

Spring DATA JPA  

Maria DB 10.4 (Using Docker)

### 필요 설정

- application.yml
```yml
spring:
  jpa:
    properties:
      hibernate.jdbc.batch_size: 1000
      hibernate.order_inserts: true
      hibernate.order_updates: true
  datasource:
    hikari:
      data-source-properties:
        rewriteBatchedStatements: true

```

- 자식 엔티티는 Sequence table 을 이용한 ID의 TABLE 전략을 이용한다.
  - ex) StatDetail
```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@TableGenerator(
        name = "GENERATOR_STAT_DETAIL",
        table = "stat_sequence",
        pkColumnName = "sequence_name",
        valueColumnName = "next_val",
        allocationSize = 1000
)
public class StatDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.TABLE
            , generator = "GENERATOR_STAT_DETAIL"
    )
    private Long seq;

    private Long score;

    @ManyToOne
    @JoinColumn(name = "stat_id")
    private Stat stat;

    public StatDetail(Long score) {
        this.score = score;
    }

    public void placeStat(Stat stat) {
        this.stat = stat;
    }
}
```

## 주의사항 
**application.yml 의 batch_size 와 Sequence Table 의 allocationSize 가 같은 값을 가지고 있는것이 좋다.**