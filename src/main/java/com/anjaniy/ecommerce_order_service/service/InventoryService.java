package com.anjaniy.ecommerce_order_service.service;

import com.anjaniy.ecommerce_order_service.exception.ConflictException;
import com.anjaniy.ecommerce_order_service.exception.OptimisticLockingFailureException;
import com.anjaniy.ecommerce_order_service.exception.ResourceNotFoundException;
import com.anjaniy.ecommerce_order_service.model.entity.Product;
import com.anjaniy.ecommerce_order_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final ProductRepository productRepository;

    public boolean isInStock(Long productId, int quantity) {
        return productRepository.findById(productId)
                .map(product -> product.getStock() >= quantity)
                .orElse(false);
    }

    @Transactional
    public void deductStock(Long productId, int quantity) {
        retryOptimistic(() -> {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

            if(product.getStock() < quantity) {
                throw new ConflictException("Insufficient stock!");
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        });

    }

    @Transactional
    public void restoreStock(Long productId, int quantity) {
        retryOptimistic(() -> {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
        });
    }

    private void retryOptimistic(Runnable action) {
        int attempts = 3;
        while (attempts-- > 0) {
            try {
                action.run();
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                if (attempts == 0) {
                    throw new OptimisticLockingFailureException("Stock update failed after retries!", e);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new OptimisticLockingFailureException("Thread interrupted during retry backoff!", ex);
                }
            }
        }
    }
}
