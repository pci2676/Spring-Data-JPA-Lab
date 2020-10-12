package com.javabom.bomjpa.querydsl;

import com.javabom.bomjpa.QueryDslProjectionApplication;
import com.javabom.bomjpa.dto.ArticleAndComment;
import com.javabom.bomjpa.model.Article;
import com.javabom.bomjpa.model.ArticleRepository;
import com.javabom.bomjpa.model.Comment;
import com.javabom.bomjpa.model.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest(classes = QueryDslProjectionApplication.class)
class QueryRepositoryTest {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    private Article savedArticle1;

    @BeforeEach
    void setUp() {
        Article article1 = new Article("글1");
        Article article2 = new Article("글2");
        Article article3 = new Article("글3");

        for (int i = 0; i < 10000; i++) {
            Comment comment1 = new Comment("댓글" + i);
            Comment comment2 = new Comment("댓글" + i);
            Comment comment3 = new Comment("댓글" + i);
            article1.addComment(comment1);
            article2.addComment(comment2);
            article3.addComment(comment3);
        }

        savedArticle1 = articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
    }


    @Transactional
    @Test
    void findArticleByIdLimit5Lazy() {
        List<ArticleAndComment> articleComments = queryRepository.find();
        System.out.println();
    }

    private void line() {
        System.err.println("=============================");
    }
}