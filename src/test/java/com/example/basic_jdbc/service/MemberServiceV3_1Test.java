package com.example.basic_jdbc.service;

import com.example.basic_jdbc.domain.Member;
import com.example.basic_jdbc.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static com.example.basic_jdbc.util.ConnectionUtil.*;

/**
 * 기본 동작, 트랙잭션이 없어서 문제 발생
 */
class MemberServiceV3_1Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_1 memberService;

    @BeforeEach
    void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV3(dataSource);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberService = new MemberServiceV3_1(transactionManager, memberRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10_000);
        Member memberB = new Member(MEMBER_B, 10_000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2_000);

        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8_000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12_000);
    }

    @Test
    @DisplayName("정상 이체 예외 발생")
    void accountTransfer_Exception() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10_000);
        Member memberEx = new Member(MEMBER_EX, 10_000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        //when
        ThrowableAssert.ThrowingCallable callable = () -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2_000);

        //then
        Assertions.assertThatThrownBy(callable)
                .isInstanceOf(IllegalStateException.class);

        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberEx.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(10_000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(10_000);
    }
}