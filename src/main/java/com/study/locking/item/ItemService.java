package com.study.locking.item;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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

	@Transactional(timeout = 1, isolation = Isolation.SERIALIZABLE)
	public void updateItemsQuantity(Long itemId1, Long itemId2) {
		Item item1 = itemRepository.findByIdWithLock(itemId1);
		item1.setQuantity(item1.getQuantity() + 10);
		itemRepository.save(item1);

		try {
			Thread.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Item item2 = itemRepository.findByIdWithLock(itemId2);
		item2.setQuantity(item2.getQuantity() + 20);
		itemRepository.save(item2);
	}
}
