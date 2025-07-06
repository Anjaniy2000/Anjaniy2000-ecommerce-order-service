package com.anjaniy.ecommerce_order_service.model.dto;

import com.anjaniy.ecommerce_order_service.model.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String user;
    private String productName;
    private OrderStatus status;
    private Integer quantity;
    private Double totalAmount;
    private LocalDateTime createdAt;
}
