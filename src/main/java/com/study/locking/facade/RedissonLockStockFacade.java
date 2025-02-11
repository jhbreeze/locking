package com.study.locking.facade;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.study.locking.service.StockService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedissonLockStockFacade {

	private final RedissonClient redissonClient;

	private final StockService stockService;

	public void decrease(Long id, Long quantity) {
		RLock lock = redissonClient.getLock(id.toString());

		try {
			boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);

			if (!available) {
				System.err.println("lock 획득 실패 ");
				return;
			}

			stockService.decrease(id, quantity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

}

