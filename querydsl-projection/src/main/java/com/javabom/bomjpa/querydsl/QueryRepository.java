package com.javabom.bomjpa.querydsl;

import com.javabom.bomjpa.dto.ArticleAndComment;
import com.javabom.bomjpa.dto.QArticleAndComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.javabom.bomjpa.model.QArticle.article;
import static com.javabom.bomjpa.model.QComment.comment;

@Repository
@RequiredArgsConstructor
public class QueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ArticleAndComment> find() {
        return queryFactory.select(new QArticleAndComment(article.contents, comment.contents))
                .from(article)
                .innerJoin(article.comments, comment)
                .limit(3)
                .orderBy(comment.id.desc())
                .fetch();
    }
}