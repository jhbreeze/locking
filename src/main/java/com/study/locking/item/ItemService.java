package com.study.locking.item;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public void updateItemQuantity(Long itemId, Integer newQuantity) {
		Item item = itemRepository.findByIdWithLock(itemId);
		item.setQuantity(newQuantity);
		itemRepository.save(item);
	}

	@Transactional
	public Item findItemById(Long itemId) {
		// 비관적 락 없이 데이터를 조회합니다.
		return itemRepository.findById(itemId).orElse(null);
	}
}
