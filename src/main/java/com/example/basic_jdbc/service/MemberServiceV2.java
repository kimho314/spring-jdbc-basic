package com.example.basic_jdbc.service;

import com.example.basic_jdbc.domain.Member;
import com.example.basic_jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);

            bizLogic(fromId, toId, money, connection);

            connection.commit();
        }
        catch (Exception e) {
            connection.rollback();
            throw new IllegalStateException();
        }
        finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                catch (Exception e) {
                    log.info("error", e);
                }
            }
        }
    }

    private void bizLogic(String fromId, String toId, int money, Connection connection) throws SQLException {
        Member fromMember = memberRepository.findById(connection, fromId);
        Member toMember = memberRepository.findById(connection, toId);

        memberRepository.update(connection, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(connection, toId, toMember.getMoney() + money);
    }

    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 에러 발생");
        }
    }

}
