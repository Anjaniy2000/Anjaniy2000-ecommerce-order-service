package com.anjaniy.ecommerce_order_service.repository;

import com.anjaniy.ecommerce_order_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
