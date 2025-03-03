package com.study.locking.facade;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.locking.domain.Stock;
import com.study.locking.repository.StockRepository;

@SpringBootTest
class LettuceLockStockFacadeTest {

	@Autowired
	private LettuceLockStockFacade lettuceLockStockFacade;

	@Autowired
	private StockRepository stockRepository;

	@BeforeEach
	public void before() {
		stockRepository.saveAndFlush(new Stock(1L, 100L));
	}

	@AfterEach
	public void after() {
		stockRepository.deleteAll();
	}

	@Test
	public void 동시에_100개_요청() throws InterruptedException {
		int threadCnt = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCnt);

		for (int i = 0; i < threadCnt; i++) {
			executorService.submit(() -> {
				try {
					lettuceLockStockFacade.decrease(1L, 1L);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Stock stock = stockRepository.findById(1L).orElseThrow();
		assertEquals(0, stock.getQuantity());

	}

}