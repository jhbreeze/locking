package com.study.locking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.study.locking.domain.Stock;

public interface LockRepository extends JpaRepository<Stock, Long> {

	@Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
	void getLock(String key);

	@Query(value = "select release_lock(:key)", nativeQuery = true)
	void releaseLock(String key);

}
