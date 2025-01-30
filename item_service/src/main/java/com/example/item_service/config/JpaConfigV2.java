package com.example.item_service.config;

import com.example.item_service.repository.ItemRepository;
import com.example.item_service.repository.jpa.JpaItemRepositoryV2;
import com.example.item_service.repository.jpa.SpringDataJpaItemRepository;
import com.example.item_service.service.ItemService;
import com.example.item_service.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaConfigV2 {
    private final SpringDataJpaItemRepository itemRepository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(itemRepository);
    }
}
