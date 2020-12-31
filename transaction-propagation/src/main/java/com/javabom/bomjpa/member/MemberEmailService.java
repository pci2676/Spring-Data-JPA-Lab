package com.javabom.bomjpa.member;

import com.javabom.bomjpa.infra.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberEmailService {
    private final MemberService memberService;
    private final EmailSender emailSender;

    @Transactional
    public Long saveMember(String name) {
        Member member = memberService.save(name);
        emailSender.send(member.getName());
        return member.getId();
    }
}
