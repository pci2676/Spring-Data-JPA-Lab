# 첫 페이지 조회 결과 캐시하기

동욱님의 블로그 게시글을 따라치며 실습해보았다.

첫 페이지 count 쿼리 결과를 프론트에서 캐싱해둔 다음 전달하는 방식으로 처리하면서 성능 향상을 꾀할 수 있다.  

그러나 이 방식은 첫 페이지 조회만 필요로 하는 페이지네이션의 경우 큰 효과를 얻을 수 없다. 그리고 실시간 데이터 수정을 필요로 하는경우에도 영 좋지 못하다.