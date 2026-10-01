package com.sadguru.orderinvoice.repository;

import com.sadguru.orderinvoice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}