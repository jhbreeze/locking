package com.study.locking.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.study.locking.repository.LockRepository;
import com.study.locking.service.StockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

	private final LockRepository lockRepository;
	private final StockService stockService;

	@Transactional
	public void decrease(Long id, Long quantity) {
		try {
			lockRepository.getLock(id.toString());
			stockService.decrease(id, quantity);
		} finally {
			lockRepository.releaseLock(id.toString());
		}
	}
}
