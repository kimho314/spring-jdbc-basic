package com.example.item_service.repository.jpa;

import com.example.item_service.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemNameContaining(String itemName);

    List<Item> findByPriceLessThanEqual(Integer price);

    List<Item> findByItemNameContainingAndPriceLessThanEqual(String itemName, Integer price);
}
