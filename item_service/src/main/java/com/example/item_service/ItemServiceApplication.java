package com.example.item_service;

import com.example.item_service.config.JdbcTemplateV3Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages = "com.example.item_service.web")
public class ItemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }

//    @Bean
//    @Profile("local")
//    public TestDataInit testDataInit(ItemRepository itemRepository) {
//        return new TestDataInit(itemRepository);
//    }
}
