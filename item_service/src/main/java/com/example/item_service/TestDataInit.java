package com.example.item_service;

import com.example.item_service.domain.Item;
import com.example.item_service.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData(){
        log.info("test data init");
        itemRepository.save(new Item("itemA", 10_000, 10));
        itemRepository.save(new Item("itemB", 20_000, 20));
    }
}
