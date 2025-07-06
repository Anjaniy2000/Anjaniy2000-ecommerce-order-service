package com.anjaniy.ecommerce_order_service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name must not be blank")
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private String description;

    @NotNull(message = "Price must not be null")
    @Min(value = 1, message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10)
    private Double price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 1, message = "Stock must be greater than 0")
    @Column(nullable = false)
    private Integer stock;

    @Version
    private int version;
}
