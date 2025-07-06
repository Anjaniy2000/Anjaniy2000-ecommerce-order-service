package com.anjaniy.ecommerce_order_service.service;

import com.anjaniy.ecommerce_order_service.exception.BadRequestException;
import com.anjaniy.ecommerce_order_service.exception.ConflictException;
import com.anjaniy.ecommerce_order_service.exception.ResourceNotFoundException;
import com.anjaniy.ecommerce_order_service.model.dto.OrderDto;
import com.anjaniy.ecommerce_order_service.model.dto.OrderRequest;
import com.anjaniy.ecommerce_order_service.model.dto.OrderResponse;
import com.anjaniy.ecommerce_order_service.model.entity.Order;
import com.anjaniy.ecommerce_order_service.model.entity.Product;
import com.anjaniy.ecommerce_order_service.model.entity.User;
import com.anjaniy.ecommerce_order_service.model.enums.OrderStatus;
import com.anjaniy.ecommerce_order_service.repository.OrderRepository;
import com.anjaniy.ecommerce_order_service.repository.ProductRepository;
import com.anjaniy.ecommerce_order_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final PaymentService paymentService;

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream().map(order -> OrderDto.builder()
                .id(order.getId())
                .user(order.getUser().getFirstName() + " " + order.getUser().getLastName())
                .productName(order.getProduct().getName())
                .status(order.getStatus())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .build()).toList();
    }

    public OrderDto getOrder(long id) {
        if(id <= 0) {
            throw new BadRequestException("Order ID must be a positive number!");
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id + "!"));

        return OrderDto.builder()
                .id(order.getId())
                .user(order.getUser().getFirstName() + " " + order.getUser().getLastName())
                .productName(order.getProduct().getName())
                .status(order.getStatus())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .build();
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        if(!inventoryService.isInStock(request.getProductId(), request.getQuantity())) {
            throw new ConflictException("Insufficient stock!");
        }

        inventoryService.deductStock(request.getProductId(), request.getQuantity());

        Order order = Order.builder()
                .user(user)
                .product(product)
                .quantity(request.getQuantity())
                .totalAmount(request.getQuantity() * product.getPrice())
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        boolean isPaid = paymentService.processPayment(request.getUserId(), (request.getQuantity() * product.getPrice()), request.getPaymentInfoDto());

        if(isPaid) {
           savedOrder.setStatus(OrderStatus.SUCCESS);
        } else {
            savedOrder.setStatus(OrderStatus.FAILED);
            inventoryService.restoreStock(request.getProductId(), request.getQuantity());
        }

        orderRepository.save(savedOrder);

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .status(savedOrder.getStatus().name())
                .message(isPaid ? "Order placed successfully!" : "Order failed due to payment error!")
                .build();
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ConflictException("Order is already cancelled!");
        }

        if (order.getStatus() == OrderStatus.FAILED) {
            throw new ConflictException("Cannot cancel a failed order!");
        }

        inventoryService.restoreStock(order.getProduct().getId(), order.getQuantity());

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .message("Order cancelled and stock restored!")
                .build();
    }
}
