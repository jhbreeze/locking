package com.study.locking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.study.locking.domain.Stock;
import com.study.locking.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	// synchronized : 1개의 스레드에만 접근 가능
	/*
		synchronized 키워드가 우리가 작성한 StockService.decrease() 메서드에 적용되어 있습니다.
		@Transactional 어노테이션에 의해 생성된 프록시 클래스의 TransactionStockService.decrease() 메서드에는 적용되어 있지 않습니다.
		따라서 프록시 클래스의 decrease() 메서드 안에서 endTransaction() 메서드가 종료되기 전에 다른 스레드가 StockService.decrease() 메서드에 접근하여 동일한 상태의 자원을 바라볼 수 있다고 이해했습니다.
	 	자바의 synchronized 하나의 프로세스 안에서만 보장이 된다.
	 */
	// //@Transactional
	// public synchronized void decrease(Long stockId, Long quantity) {
	// 	Stock stock = stockRepository.findById(stockId).orElseThrow();
	// 	stock.decreaseQuantity(quantity);
	// 	stockRepository.saveAndFlush(stock);
	// }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void decrease(Long stockId, Long quantity) {
		Stock stock = stockRepository.findById(stockId).orElseThrow();
		stock.decreaseQuantity(quantity);
		stockRepository.saveAndFlush(stock);
	}

}
