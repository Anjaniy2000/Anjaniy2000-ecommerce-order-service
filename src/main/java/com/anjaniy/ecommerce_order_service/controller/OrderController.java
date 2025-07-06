package com.anjaniy.ecommerce_order_service.controller;

import com.anjaniy.ecommerce_order_service.model.dto.ApiResponse;
import com.anjaniy.ecommerce_order_service.model.dto.OrderDto;
import com.anjaniy.ecommerce_order_service.model.dto.OrderRequest;
import com.anjaniy.ecommerce_order_service.model.dto.OrderResponse;
import com.anjaniy.ecommerce_order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrders() {
        return ResponseEntity.ok(ApiResponse.<List<OrderDto>>builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK.value())
                        .message("Orders fetched successfully!")
                        .data(orderService.getOrders())
                        .build()
        );
    }


    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<OrderResponse>builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED.value())
                        .message("Order placed successfully!")
                        .data(orderService.placeOrder(request))
                        .build()
                );

    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<OrderResponse>builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK.value())
                        .message("Order cancelled successfully")
                        .data(orderService.cancelOrder(orderId))
                        .build()
                );
    }

}
