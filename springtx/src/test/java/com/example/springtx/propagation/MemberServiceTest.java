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

    /**
     * memberService        @Transactional:off
     * memberRepository     @Transactional:on
     * logRepository        @Transactional:on Exception
     */
    @Test
    void outerTxOff_fail() {
        //given
        String userName = "로그예외";

        //when
        Assertions.assertThatThrownBy(() -> memberService.joinV1(userName))
            .isInstanceOf(RuntimeException.class);

        //then
        Assertions.assertThat(memberRepository.find(userName).isPresent()).isTrue();
        Assertions.assertThat(logRepository.find(userName).isPresent()).isFalse();
    }

    /**
     * memberService        @Transactional:off
     */
    @Test
    void singleTx() {
        //given
        String userName = "singleTx";

        //when
        memberService.joinV1(userName);

        //then
        Assertions.assertThat(memberRepository.find(userName).isPresent()).isTrue();
        Assertions.assertThat(logRepository.find(userName).isPresent()).isTrue();
    }

    /**
     * memberService        @Transactional:off
     */
    @Test
    void outerTxOn_success() {
        //given
        String userName = "outerTxOn_success";

        //when
        memberService.joinV1(userName);

        //then
        Assertions.assertThat(memberRepository.find(userName).isPresent()).isTrue();
        Assertions.assertThat(logRepository.find(userName).isPresent()).isTrue();
    }

    /**
     * memberService        @Transactional:on
     * memberRepository     @Transactional:on
     * logRepository        @Transactional:on Exception
     */
    @Test
    void outerTxOn_fail() {
        //given
        String userName = "로그예외_outerTxOn_fail";

        //when
        Assertions.assertThatThrownBy(() -> memberService.joinV1(userName))
            .isInstanceOf(RuntimeException.class);

        //then
        // 모든 데이터가 롤백 된다.
        Assertions.assertThat(memberRepository.find(userName).isPresent()).isFalse();
        Assertions.assertThat(logRepository.find(userName).isPresent()).isFalse();
    }
}