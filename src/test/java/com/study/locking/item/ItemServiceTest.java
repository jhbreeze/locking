package com.study.locking.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemRepository itemRepository;

	@Test
	public void testPessimisticLocking() throws InterruptedException {
		Item item = new Item();
		item.setName("item1");
		item.setQuantity(10);
		itemRepository.save(item);

		Thread thread1 = new Thread(() -> {
			log.info("스레드1: 수량 업데이트 시도 ");
			itemService.updateItemQuantity(item.getId(), 20);
			System.out.println("스레드1: 수량 업데이트 완료 ");
		});

		Thread thread2 = new Thread(() -> {
			log.info("스레드2: 수량 업데이트 시도 ");
			itemService.updateItemQuantity(item.getId(), 30);
			System.out.println("스레드2: 수량 업데이트 완료 ");
		});

		thread2.start();
		thread1.start();

		thread1.join();
		thread2.join();

		Item updatedItem = itemService.findItemById(item.getId());
		log.info("최종 아이템 수량 : {}", updatedItem.getQuantity());
	}


}
