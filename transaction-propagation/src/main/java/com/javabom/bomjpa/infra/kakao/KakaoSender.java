package com.javabom.bomjpa.infra.kakao;

import com.javabom.bomjpa.infra.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoSender implements Sender {

    private final KakaoHistoryRepository kakaoHistoryRepository;

    @Override
    public void send(String name) {
        kakaoHistoryRepository.save(new KakaoHistory(name));
    }
}
