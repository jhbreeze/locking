package com.study.locking.product;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.extern.java.Log;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
