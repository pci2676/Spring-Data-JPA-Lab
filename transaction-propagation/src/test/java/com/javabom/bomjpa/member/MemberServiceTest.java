package com.javabom.bomjpa.member;

import com.javabom.bomjpa.infra.email.EmailHistory;
import com.javabom.bomjpa.infra.email.EmailHistoryRepository;
import com.javabom.bomjpa.infra.kakao.KakaoHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberEmailService memberEmailService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    @Autowired
    private KakaoHistoryRepository kakaoHistoryRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
        emailHistoryRepository.deleteAll();
        kakaoHistoryRepository.deleteAll();
    }

    @DisplayName("propagation = Propagation.REQUIRES_NEW 라서 롤백의 영향을 받지 않는다.")
    @Test
    void save() {
        //given
        String memberName = "name";

        //when
        try {
            memberEmailService.saveMember(memberName);
        } catch (Exception e) {

        }

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);

        List<EmailHistory> emails = emailHistoryRepository.findAll();
        assertThat(emails).hasSize(0);
    }
}