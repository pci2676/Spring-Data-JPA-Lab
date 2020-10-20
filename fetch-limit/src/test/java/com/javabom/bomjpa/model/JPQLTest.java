package com.javabom.bomjpa.model;

import com.javabom.bomjpa.FetchLimitApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = FetchLimitApplication.class)
public class JPQLTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Article article = Article.builder()
                .contents("글")
                .build();

        for (int i = 0; i < 6; i++) {
            Comment comment = new Comment("댓글" + i);
            article.addComment(comment);
        }

        articleRepository.save(article);
    }

    @Test
    void jpqlTest0() {
        assertThatThrownBy(() -> entityManager.createQuery("SELECT a FROM Article a INNER JOIN FETCH a.comments c LIMIT 3", Article.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unexpected token: LIMIT");
    }

    @Test
    void jpqlTest() {
        TypedQuery<Article> query = entityManager.createQuery("SELECT a FROM Article a INNER JOIN FETCH a.comments c", Article.class);
        query.setFirstResult(0);
        query.setMaxResults(3);

        List<Article> resultList = query.getResultList();
        for (Article article : resultList) {
            System.out.println(article.getId());
        }
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
    }
}
