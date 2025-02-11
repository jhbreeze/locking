package com.study.locking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.locking.domain.Stock;
import com.study.locking.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(Long stockId, Long quantity) {
		Stock stock = stockRepository.findByIdWithPessimisticLock(stockId);
		stock.decreaseQuantity(quantity);
	}
}
