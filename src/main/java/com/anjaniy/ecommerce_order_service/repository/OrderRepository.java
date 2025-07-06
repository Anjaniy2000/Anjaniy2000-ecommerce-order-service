package com.anjaniy.ecommerce_order_service.repository;

import com.anjaniy.ecommerce_order_service.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
