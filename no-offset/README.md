# No Offset

페이징 할때 `offset` 조건이 들어간 채로 검색을 하면 성능이 떨어진다.

왜냐하면 `offset`을 이용해서 검색한 결과가 있는데 이 부분은 버려지기 때문이다.

따라서 `WHERE` 절을 작성할 때 `<`, `>` 을 이용해서 아예 검색결과를 줄이는 방식으로 페이지네이션을 하면 된다.

AS-IS
```mysql
SELECT *
FROM PRODUCT
WHERE PRODUCT.NAME LIKE "상품명%"
ORDER BY PRODUCT.ID
OFFSET 1000
LIMIT 1000
```

TO-BE
```mysql
SELECT *
FROM PRODUCT
WHERE PRODUCT.ID < ? AND PRODUCT.NAME LIKE "상품명%"
ORDER BY PRODUCT.ID
LIMIT 1000
```