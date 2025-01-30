package com.example.item_service.repository.jpa;

import com.example.item_service.domain.Item;
import com.example.item_service.repository.ItemRepository;
import com.example.item_service.repository.ItemSearchCond;
import com.example.item_service.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {
    private final SpringDataJpaItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        itemRepository.save(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = findById(itemId).orElseThrow();
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity()); // dirty checking!!!
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        Integer maxPrice = cond.getMaxPrice();
        String itemName = cond.getItemName();

        if (StringUtils.hasText(itemName) && maxPrice != null) {
            return itemRepository.findByItemNameContainingAndPriceLessThanEqual(itemName, maxPrice);
        } else if (StringUtils.hasText(itemName) && maxPrice == null) {
            return itemRepository.findByItemNameContaining(itemName);
        } else if (maxPrice != null) {
            return itemRepository.findByPriceLessThanEqual(maxPrice);
        } else {
            return itemRepository.findAll();
        }

    }
}
