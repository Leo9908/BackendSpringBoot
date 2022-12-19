package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.entitys.Order;

public interface OrdersRepository extends JpaRepository<Order, Long> {
}