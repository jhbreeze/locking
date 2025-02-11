package com.study.locking.facade;

import org.springframework.stereotype.Component;

import com.study.locking.service.OptimisticLockStockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

	private final OptimisticLockStockService optimisticLockStockService;

	public void decrease(Long stockId, Long quantity) throws InterruptedException {
		while (true) {
			try {
				optimisticLockStockService.decrease(stockId, quantity);
				break;
			} catch (Exception e) {
				System.err.println("충돌 발생 ㅎㅎㅎ");
				Thread.sleep(50);
			}
		}
	}

}