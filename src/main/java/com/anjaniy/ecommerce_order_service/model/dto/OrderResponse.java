package com.anjaniy.ecommerce_order_service.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private String status;
    private String message;
}
