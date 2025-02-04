package com.example.springtx.exception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService rollbackService;

    @Test
    void runtimeException() {
        assertThatThrownBy(() -> rollbackService.runtimeExcpeiton())
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void checkedException() {
        assertThatThrownBy(() -> rollbackService.checkedException())
            .isInstanceOf(Exception.class);
    }

    @Test
    void rollbackFor() {
        assertThatThrownBy(() -> rollbackService.rollbackFor())
            .isInstanceOf(Exception.class);
    }

    @TestConfiguration
    static class RollbackTestConfig {

        @Bean
        RollbackService rollbackService() {
            return new RollbackService();
        }
    }

    static class RollbackService {

        //런타임 예외 발생 : 롤백
        @Transactional
        public void runtimeExcpeiton() {
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        //체크 예외 발생 : 커밋
        @Transactional
        public void checkedException() throws Exception {
            log.info("call checkedException");
            throw new Exception();
        }

        //체크 예외 : roolbackfor 사용 롤백
        @Transactional(rollbackFor = Exception.class)
        public void rollbackFor() throws Exception {
            log.info("call checkedException");
            throw new Exception();
        }
    }
}
