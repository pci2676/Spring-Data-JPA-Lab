package com.javabom.bomjpa.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    /* JPQL은 LIMIT을 지원하지 않는다.
    @Query("SELECT a FROM Article a INNER JOIN FETCH a.comments LIMIT 1")
    List<Article> findAllLimit3Fetch();
    */
}
