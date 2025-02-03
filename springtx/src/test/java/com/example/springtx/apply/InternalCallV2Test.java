package com.example.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {

    @Autowired
    CallService callService;
    @Autowired
    InternalService internalService;

    @Test
    void printProxy() {
        log.info("callService class={}", callService.getClass());
    }

    @Test
    void internalCall() {
        internalService.internal();
    }

    @Test
    void externalCall() {
        // internal() 메소드가 다른 클래스에 정의되어 있어서 proxy가 만들어 지므고 트랜잭션이 동작한다.
        callService.external();
    }

    @TestConfiguration
    static class InternalCallV1TstConfig {

        @Bean(name = "internalService")
        InternalService internalService() {
            return new InternalService();
        }

        @Bean
        CallService callService(@Qualifier(value = "internalService") InternalService internalService) {
            return new CallService(internalService);
        }
    }

    static class CallService {

        private final InternalService internalService;

        public CallService(InternalService internalService) {
            this.internalService = internalService;
        }

        public void external() {
            log.info("call external");
            printTxInfo();
            internalService.internal();
        }
    }

    static class InternalService {

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }
    }

    private static void printTxInfo() {
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        log.info("tx active={}", isActive);
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        log.info("tx readOnly={}", isReadOnly);
    }
}
