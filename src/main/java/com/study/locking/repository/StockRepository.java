package com.study.locking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.locking.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
