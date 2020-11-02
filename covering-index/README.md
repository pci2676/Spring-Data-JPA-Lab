# 커버링 인덱스

커버링 인덱스란?

쿼리를 충족시키는데 필요한 모든 데이터를 가지고 있는 인덱스를 의미한다.  
쿼리를 충족시킨다는 것은 각 절(ex. select where..) 에 인덱스 컬럼이 들어가야 한다는 것을 의미한다.

이때 select 까지 인덱스인 컬럼이 들어가게 되는 경우 인덱스 자체의 크기가 커야한다는 생각을 할 수 있는데  
이를 피하기 위해서 쿼리를 두번으로 나눠서 실행하도록 한다.
만약 조회하고자 하는 것이 (id, mail, name, phone) 인 상황에서 다음과 같이 쿼리를 작성한다면 쿼리의 조건문에 해당하는 mail, id 만으로 인덱스를 만들어 사용하고 싶지만
select 절에 name, phone 을 같이 조회하기 때문에 커버링 인덱스의 조건에 부합하지 않는다.
```sql
SELECT id, mail, name, phone
FROM MEMBER
WHERE mail LIKE '%@gmail.com'
ORDER BY id DESC
```

따라서 인덱스를 (id, mail) 로 만들고 사용하고 싶다면 쿼리를 나누도록 하자
```sql
SELECT id, mail, name, phone
FROM MEMBER as m
INNER JOIN (SELECT id
            FROM MEMBER
            WHERE mail LIKE '%@gmail.com'
            ORDER BY id DESC) AS temp ON m.id = temp.id  
```
조인절에 사용되는 서브 쿼리는 쿼리를 충족시키는 모든 절에 인덱스 컬럼이 들어가있다.
위와 같이 조인절에 사용되는 내부 쿼리에서 커버링 인덱스를 사용해서 성능을 개선하면 된다.

그런데 JPQL을 사용하는 입장에서 FROM 절에 서브 쿼리를 작성할 수 없다.  
서브 쿼리를 작성하지 말고 하나의 쿼리를 두방에 나눠서 작성하고 `IN` 절을 이용해서 서브쿼리의 결과를 사용하면 된다.

## 단점이 있을 수도 있다~ 이말이야

인덱스 자체가 커지면서 성능 저하가 발생할 수 있다는 것을 명심해야한다!  
어쨋든 모든 쿼리에 인덱스 컬럼이 들어가야하기 때문에 보통 인덱스로 묶이는 크기 자체가 크게 될 수 있음을 명심하도록 하자! 