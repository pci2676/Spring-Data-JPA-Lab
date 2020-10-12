package com.javabom.bomjpa.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ArticleAndComment {
    private final String articleContent;
    private final String commentContent;

    @QueryProjection
    public ArticleAndComment(final String articleContent, final String commentContent) {
        this.articleContent = articleContent;
        this.commentContent = commentContent;
    }
}
