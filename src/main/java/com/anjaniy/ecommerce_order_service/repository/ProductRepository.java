package com.anjaniy.ecommerce_order_service.repository;

import com.anjaniy.ecommerce_order_service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
