package com.example.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Transactional
    public void joinV1(String userName) {
        Member member = new Member(userName);
        Log logMessage = new Log(userName);

        log.info("=== memberRepository 호출 시작 ===");
        memberRepository.save(member);
        log.info("=== memberRepository 호출 종료 ===");
        log.info("=== logRepository 호출 시작 ===");
        logRepository.save(logMessage);
        log.info("=== logRepository 호출 종료 ===");
    }

    @Transactional
    public void joinV2(String userName) {
        Member member = new Member(userName);
        Log logMessage = new Log(userName);

        log.info("=== memberRepository 호출 시작 ===");
        memberRepository.save(member);
        log.info("=== memberRepository 호출 종료 ===");

        log.info("=== logRepository 호출 시작 ===");
        try {
            logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.info("log 저장에 실패했습니다. logMessage={}", logMessage);
            log.info("정상 흐름 반환");
        }
        log.info("=== logRepository 호출 종료 ===");
    }

    @Transactional
    public void joinV3(String userName) {
        Member member = new Member(userName);
        Log logMessage = new Log(userName);

        log.info("=== memberRepository 호출 시작 ===");
        memberRepository.save(member);
        log.info("=== memberRepository 호출 종료 ===");

        log.info("=== logRepository 호출 시작 ===");
        try {
            logRepository.saveNew(logMessage);
        } catch (RuntimeException e) {
            log.info("log 저장에 실패했습니다. logMessage={}", logMessage);
            log.info("정상 흐름 반환");
        }
        log.info("=== logRepository 호출 종료 ===");
    }
}
