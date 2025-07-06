package com.anjaniy.ecommerce_order_service.service;

import com.anjaniy.ecommerce_order_service.exception.BadRequestException;
import com.anjaniy.ecommerce_order_service.exception.ResourceNotFoundException;
import com.anjaniy.ecommerce_order_service.model.dto.ProductDto;
import com.anjaniy.ecommerce_order_service.model.entity.Product;
import com.anjaniy.ecommerce_order_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(product -> ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build()).toList();
    }

    public ProductDto getProduct(long id) {
        if (id <= 0) {
            throw new BadRequestException("Product ID must be a positive number!");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id + "!"));

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
