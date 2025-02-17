package com.example.item_service.config;

import com.example.item_service.repository.ItemRepository;
import com.example.item_service.repository.jpa.JpaItemRepositoryV1;
import com.example.item_service.service.ItemService;
import com.example.item_service.service.ItemServiceV1;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaConfigV1 {
    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV1(em);
    }
}
