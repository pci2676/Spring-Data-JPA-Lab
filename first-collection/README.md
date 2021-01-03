# 일급컬렉션 embedding 하기
`@OneToMany`와 같은 일대다 관계를 일급컬렉션으로 표현하고 싶어서 확인해보았다.

## final 이 아니 방법
```java
@Getter
@Embeddable
public class Teams {

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Team> teams;

    protected Teams() {
        this.teams = new ArrayList<>();
    }

    public Teams(List<Team> teams) {
        this.teams = teams;
    }
}

```

## final 인 방법
```java
@Getter
@NoArgsConstructor
@Embeddable
public class Teams2 {

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team2> teams;

    public Teams2(List<Team2> teams) {
        this.teams = teams;
    }
}
```

위와 같이 두 가지 방법으로 일급컬렉션을 표현할수 있었다.
