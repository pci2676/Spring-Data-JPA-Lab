# 단방향 @OneToMany 관계에서 발생 할 수 있는 문제점

김영한님의 JPA 책을 공부하면 단방향 `@OneToMany`를 사용하는 것을 지양하라고 한다.  
외래키 관리의 주체가 아닌 쪽에서 연관 관계를 관리 하게 됨으로써 발생하는 문제들이 있기 때문인데 이 부분에 대해 살펴보고자 한다.

## 준비

### 설정

실습에는 flyway로 DB 스키마를 정의하고 생성하였고 application.yml에 hibernate의 ddl-auto 설정을 validate로 설정해 두었다.

```groovy
implementation 'org.flywaydb:flyway-core'
```
```properties
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

### 모델

모델은 Menu - MenuProduct 관계로 One to Many로 이어져 있다.
```sql
CREATE TABLE menu
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE menu_product
(
    seq     BIGINT(20) NOT NULL AUTO_INCREMENT,
    menu_id BIGINT(20) NOT NULL,
    PRIMARY KEY (seq)
);

ALTER TABLE menu_product
    ADD CONSTRAINT fk_menu_product_menu
        FOREIGN KEY (menu_id) REFERENCES menu (id);
```
```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public void addMenuProduct(MenuProduct menuProduct) {
        this.menuProducts.add(menuProduct);
    }
}


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
}
```
## 문제점 살펴보기

### JoinTable 문제

`@OneToMany` 인 객체에 `@JoinColumn`을 명시하지 않으면 JPA가 자동으로 JoinTable을 생성하려고 한다.
이 때 Jpa 의 ddl 설정인 `validate`에 의해 아래와 같은 SchemaManagementException이 발생한다.

![SchemaManagementException](https://user-images.githubusercontent.com/13347548/100540149-cbb77c80-327e-11eb-85fc-7cf923017832.png)

물론 이 경우의 해결책은 아래와 같이 JoinColumn을 정의하면 된다. 이로써 자동으로 JoinTable이 생성되지 않게 됨으로써 더 이상 SchemaManagementException이 발생하지 않는다.
```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id") // 외래키의 column 이름을 적어준다.
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public void addMenuProduct(MenuProduct menuProduct) {
        this.menuProducts.add(menuProduct);
    }
}
```

### INSERT NULL 문제 

단방향으로 `@OneToMany`를 설정하고 `CascadeType`을 `ALL`로 하면 `Menu`를 save 할 때 같이 `MenuProduct`를 영속한다. 
그런데 `MenuProduct`를 영속할 때 외래키인 `menu_id`는 `NULL` 로 INSERT를 하고 영속된 `Menu`의 id 를 이용해서 UPDATE를 진행한다.

따라서 `MenuProduct`의 FK인 `menu_id` 컬럼에 NOT NULL 제약 조건이 붙어 있다면 다음과 같이 문제가 발생한다.

```java
@DisplayName("단방향일 때 MenuProduct에 null 값으로 insert 할 때 문제가 발생한다.")
@Test
void addMenuProduct() {
    //given
    Menu menu = new Menu();
    MenuProduct menuProduct = new MenuProduct();
    menu.addMenuProduct(menuProduct);

    //then
    assertThatThrownBy(() -> menuRepository.save(menu))
            .isInstanceOf(DataIntegrityViolationException.class)
            .hasMessageContaining("constraint [null]");
}
```

## 결론

따라서 이러한 문제를 해결하기 위해서라도 단방향 `@OneToMany`를 사용하기 보다 양방향으로 `@OneToMany`, `@ManyToOne`을 사용하는 것이 좋다.
 