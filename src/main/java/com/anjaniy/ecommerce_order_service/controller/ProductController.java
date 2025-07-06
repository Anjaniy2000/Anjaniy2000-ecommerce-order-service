package com.anjaniy.ecommerce_order_service.controller;

import com.anjaniy.ecommerce_order_service.model.dto.ApiResponse;
import com.anjaniy.ecommerce_order_service.model.dto.OrderDto;
import com.anjaniy.ecommerce_order_service.model.dto.ProductDto;
import com.anjaniy.ecommerce_order_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse> getProducts() {
        return ResponseEntity.ok(ApiResponse.<List<ProductDto>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Products fetched successfully!")
                .data(productService.getProducts())
                .build()
        );

    }

    @GetMapping("/{id}/get")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable("id") long id) {
        return ResponseEntity.ok(ApiResponse.<ProductDto>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Product fetched successfully!")
                .data(productService.getProduct(id))
                .build()
        );
    }
}
