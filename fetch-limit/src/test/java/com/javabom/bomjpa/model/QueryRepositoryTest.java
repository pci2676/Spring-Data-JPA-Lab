package com.javabom.bomjpa.model;

import com.javabom.bomjpa.FetchLimitApplication;
import com.javabom.bomjpa.dto.ArticleComments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FetchLimitApplication.class)
class QueryRepositoryTest {
    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    private Article savedArticle;

    @BeforeEach
    void setUp() {
        Article article = Article.builder()
                .contents("글")
                .build();

        for (int i = 0; i < 6; i++) {
            Comment comment = new Comment("댓글" + i);
            article.addComment(comment);
        }

        savedArticle = articleRepository.save(article);
    }

    @DisplayName("Lazy 로드로 limit 할 때 내가 원하는 만큼 comment를 limit 하지 않는다.")
    @Transactional
    @Test
    void fetchPaging() {
        List<Article> findArticle = queryRepository.findArticleByIdLimit5Lazy(savedArticle.getId());

        //then
        assertThat(findArticle).hasSize(5);
        assertThat(findArticle.get(0).getComments()).hasSize(6);
    }

    @DisplayName("fetch join으로 limit 할 때 내가 원하는 만큼 comment를 limit 하지 않는다. 어플리케이션 레벨에서 article만 limit한 개수만큼 가져온다")
    @Test
    void fetchPaging2() {
        List<Article> findArticle = queryRepository.findArticleByIdLimit5Fetch(savedArticle.getId());

        //then
        assertThat(findArticle).hasSize(1);
        assertThat(findArticle.get(0).getComments()).hasSize(6);
    }

    @DisplayName("특정 갯수의 comment를 가져오고 싶다면 comment 중심으로 조회를 해야한다.")
    @Test
    void fetchPaging3() {
        List<Comment> findComments = queryRepository.findCommentByArticleIdLimit5(savedArticle.getId());

        //then
        assertThat(findComments).hasSize(5);
    }

    @DisplayName("단순히 게시글 1 댓글 5개의 조회를 원한다면 DTO로 조회성 쿼리를 받아오자")
    @Test
    void fetchPaging4() {
        ArticleComments articleComments = queryRepository.findArticleWithTop5Comments(savedArticle.getId());

        //then
        assertThat(articleComments.getCommentContents()).hasSize(5);
    }
}