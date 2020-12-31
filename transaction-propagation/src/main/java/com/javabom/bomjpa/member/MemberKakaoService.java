package com.javabom.bomjpa.member;

import com.javabom.bomjpa.infra.kakao.KakaoSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberKakaoService {
    private final MemberService memberService;
    private final KakaoSender kakaoSender;

    @Transactional
    public Long save(String name) {
        Member member = memberService.save(name);
        kakaoSender.send(member.getName());
        return member.getId();
    }
}
