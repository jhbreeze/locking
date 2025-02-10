package com.study.locking.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface ItemRepository extends JpaRepository<Item, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)  // 비관적 락 적용
	@Query("select i from Item i where i.id = :id")
	Item findByIdWithLock(Long id);

}