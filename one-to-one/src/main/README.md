# OneToOne 연관관계 문제점 알고 쓰기

Product(부모) <-> Price(자식)
위와 같은 경우

FK를 가지고 있는 One 에서 FK를 가지고 있지 않은 One에대한 조회를 하는경우엔 문제가 없다.
```java
    @DisplayName("Price 를 조회하면 Product 는 정상적으로 레이지 로딩이 된다.")
    @Test
    void findAllPriceTest() {
        List<Price> allPrice = productPriceService.findAllPrice();
        Product product = allPrice.get(0).getProduct();

        assertThat(product).isInstanceOf(HibernateProxy.class);
    }
```

다음은 주인이 아닌 객체에서 참조객체를 조회할 때의 상황이다.

1. nullable 하지 못한 1:1 관계에서 참조객체가 optional = false 가 가능하지 않으면 안된다.
2. 단방향이어야 한다. 자식이 FK를 가지고 있으면 Lazy 로딩이 안된다.
3. @PrimaryKeyJoin 은 허용되지 않는다. 즉, 부모-자식간의 JoinColumn 이 PK 인 경우이다.
4. 조회를 해야할때 2번의 문제를 해결하기 위해서는 데이터베이스에서 외래키의 위치를 부모테이블로 옮겨주어야 한다.

```java
    @DisplayName("Product 는 조회하면 Price 는 레이지 로딩이 되지 않는다 !!!")
    @Test
    void findAllProductTest() {
        List<Product> allProduct = productPriceService.findAllProduct();
        Price price = allProduct.get(0).getPrice();

        assertAll(
            () -> assertThat(price).isNotInstanceOf(HibernateProxy.class),
            () -> assertThat(price).isInstanceOf(Price.class)
        );
    }
```

OneToOne 자체를 의심해봐야한다 보통 OneToMany 가 되는게 정상적이다. 특정 컬럼만 빈번하게 변경된다는 것 때문에 이렇게 하면 원하는대로 동작하지 않을 수 있다.
아예 FK의 위치를 변경하는 방법도 있긴한데 그냥 어지간해서는 쓰지말자~ 