package com.javabom.bomjpa.model;

import com.javabom.bomjpa.dto.ArticleComments;
import com.javabom.bomjpa.dto.QArticleComments;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.javabom.bomjpa.model.QArticle.article;
import static com.javabom.bomjpa.model.QComment.comment;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
@Repository
public class QueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Article> findArticleByIdLimit5Lazy(Long id) {
        return queryFactory.selectFrom(article)
                .innerJoin(article.comments, comment)
                .where(article.id.eq(id))
                .limit(5)
                .fetch();
    }

    public List<Article> findArticleByIdLimit5Fetch(Long id) {
        return queryFactory.selectFrom(article)
                .innerJoin(article.comments, comment).fetchJoin()
                .where(article.id.eq(id))
                .limit(5)
                .fetch();
    }

    public List<Article> findArticle() {
        return queryFactory.selectFrom(article)
                .innerJoin(article.comments, comment).fetchJoin()
                .offset(0)
                .limit(5)
                .fetch();
    }

    public List<Comment> findCommentByArticleIdLimit5(Long id) {
        return queryFactory.selectFrom(comment)
                .innerJoin(comment.article, article).fetchJoin()
                .where(article.id.eq(id))
                .limit(5)
                .fetch();
    }

    public ArticleComments findArticleWithTop5Comments(Long articleId) {
        return queryFactory.from(comment)
                .innerJoin(comment.article, article)
                .where(article.id.eq(articleId))
                .limit(5)
                .transform(
                        groupBy(comment.article.id)
                                .list(new QArticleComments(article.contents, list(comment.contents)))
                ).get(0);
    }


}