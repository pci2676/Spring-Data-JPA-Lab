package com.javabom.bomjpa.infra.email;

import com.javabom.bomjpa.infra.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailSender implements Sender {
    private final EmailHistoryRepository emailHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void send(String name) {
        emailHistoryRepository.save(new EmailHistory(name));
        throw new IllegalArgumentException();
    }
}
