# `@Transactional` propagation 설정에 대해

## `Propagation.REQUIRES_NEW`
새로운 트래잭션을 실행하는 옵션인데

아래와 같은 경우 MemberService 에서 저장된 member 는 롤백되지 않는다.

```java
@RequiredArgsConstructor
@Service
public class MemberEmailService {
    private final MemberService memberService;
    private final EmailSender emailSender;

    @Transactional
    public Long saveMember(String name) {
        Member member = memberService.save(name); // <- 트랜잭션을 Propagation.REQUIRES_NEW 로 실행 
        emailSender.send(member.getName()); // <- throw RuntimeException !!!
        return member.getId();
    }
}
```

새로운 트랜잭션을 사용하고 기존에 있던 트랜잭션을 이어서 사용하지 않기 때문에 독립적으로 실행되어있음을 알 수 있다.

