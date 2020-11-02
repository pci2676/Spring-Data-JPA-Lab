package com.javabom.bomjpa.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberDto {
    private final Long id;
    private final String email;
    private final String name;
    private final String phone;

    @QueryProjection
    public MemberDto(final Long id, final String email, final String name, final String phone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
