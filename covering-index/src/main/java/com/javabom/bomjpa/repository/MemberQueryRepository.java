package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.MemberDto;
import com.javabom.bomjpa.model.QMemberDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.javabom.bomjpa.model.QMember.member;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<MemberDto> findByEmailExt(String emailExt) {
        List<Long> ids = queryFactory.select(member.id)
                .from(member)
                .where(member.email.like("@" + emailExt + "%"))
                .orderBy(member.id.desc())
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory.select(
                new QMemberDto(
                        member.id,
                        member.email,
                        member.name,
                        member.phone))
                .from(member)
                .where(member.id.in(ids))
                .fetch();
    }
}
