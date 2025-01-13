package com.example.item_service.service;

import com.example.item_service.domain.Item;
import com.example.item_service.repository.ItemSearchCond;
import com.example.item_service.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface  ItemService {
    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
