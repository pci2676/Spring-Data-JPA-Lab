package com.javabom.bomjpa.member;

import com.javabom.bomjpa.infra.email.EmailHistoryRepository;
import com.javabom.bomjpa.infra.email.EmailSender;
import com.javabom.bomjpa.infra.kakao.KakaoHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RollbackTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @DisplayName("try 구문안에 있으면 롤백되지 않음.")
    @Test
    void rollbackTest() {
        try{
            memberService.save("1");
            emailSender.send("1");
        }catch (IllegalArgumentException e){

        }

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }
}
