package com.javabom.bomjpa.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleComments {
    private final String articleContents;
    private final List<String> commentContents;

    @QueryProjection
    public ArticleComments(final String articleContents, final List<String> commentContents) {
        this.articleContents = articleContents;
        this.commentContents = commentContents;
    }
}
