package com.example.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * memberService        @Transactional:off
     * memberRepository     @Transactional:on
     * logRepository        @Transactional:on
     */
    @Test
    void outerTxOff_success() {
        //given
        String userName = "outerTxOff_success";

        //when
        memberService.joinV1(userName);

        //then
        Assertions.assertThat(memberRepository.find(userName).isPresent()).isTrue();
        Assertions.assertThat(logRepository.find(userName).isPresent()).isTrue();
    }
}