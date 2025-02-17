package com.example.item_service.config;

import com.example.item_service.repository.ItemRepository;
import com.example.item_service.repository.memory.MemoryItemRepository;
import com.example.item_service.service.ItemService;
import com.example.item_service.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {
    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }
}
