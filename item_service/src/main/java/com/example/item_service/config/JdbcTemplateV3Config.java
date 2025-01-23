package com.example.item_service.config;

import com.example.item_service.repository.ItemRepository;
import com.example.item_service.repository.jdbctemplate.JdbcTemplateItemRepositoryV3;
import com.example.item_service.service.ItemService;
import com.example.item_service.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV3Config {
    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV3(dataSource);
    }
}
