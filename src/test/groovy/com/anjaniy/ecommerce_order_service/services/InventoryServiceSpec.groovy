package com.anjaniy.ecommerce_order_service.services

import com.anjaniy.ecommerce_order_service.BaseIntegrationSpec
import com.anjaniy.ecommerce_order_service.exception.ConflictException
import com.anjaniy.ecommerce_order_service.exception.ResourceNotFoundException
import com.anjaniy.ecommerce_order_service.repository.ProductRepository
import com.anjaniy.ecommerce_order_service.service.InventoryService
import org.springframework.beans.factory.annotation.Autowired

class InventoryServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private InventoryService inventoryService

    @Autowired
    private ProductRepository productRepository

    def 'Should check if product is in stock' () {
        given:
        def productId = 2
        def quantity = 3
        when:
        def isInStock = inventoryService.isInStock(productId, quantity)
        then:
        assert isInStock
    }

    def 'Should deduct stock when product is available'() {
        given:
        def productId = 1
        def initialQuantity = productRepository.findById(1).get().stock
        def deductQuantity = 3
        when:
        inventoryService.deductStock(productId, deductQuantity)
        then:
        productRepository.findById(1).get().stock == initialQuantity - deductQuantity
    }

    def 'Should throw ConflictException when stock is insufficient'() {
        given:
        def productId = 3
        def deductQuantity = 50
        when:
        inventoryService.deductStock(productId, deductQuantity)
        then:
        def e = thrown(ConflictException)
        e.message == 'Insufficient stock!'
    }

    def 'Should throw ResourceNotFoundException for invalid productId'() {
        given:
        def invalidProductId = 3001
        def deductQuantity = 1
        when:
        inventoryService.deductStock(invalidProductId, deductQuantity)
        then:
        def e = thrown(ResourceNotFoundException)
        e.message == 'Product not found!'
    }

    def 'Should restore stock when product exists'() {
        given:
        def productId = 5
        def initialQuantity = productRepository.findById(5).get().stock
        def restoreQuantity = 3
        when:
        inventoryService.restoreStock(productId, restoreQuantity)
        then:
        productRepository.findById(productId).get().stock == initialQuantity + restoreQuantity
    }

    def 'Should throw ResourceNotFoundException when restoring stock for invalid product'() {
        given:
        def invalidProductId = 4001
        def restoreQuantity = 5
        when:
        inventoryService.restoreStock(invalidProductId, restoreQuantity)
        then:
        def e = thrown(ResourceNotFoundException)
        e.message == 'Product not found!'
    }

}
