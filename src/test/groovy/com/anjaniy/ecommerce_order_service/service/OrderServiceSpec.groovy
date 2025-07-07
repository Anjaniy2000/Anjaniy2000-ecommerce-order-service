package com.anjaniy.ecommerce_order_service.service

import com.anjaniy.ecommerce_order_service.BaseIntegrationSpec
import com.anjaniy.ecommerce_order_service.exception.ConflictException
import com.anjaniy.ecommerce_order_service.exception.ResourceNotFoundException
import com.anjaniy.ecommerce_order_service.model.dto.OrderRequest
import com.anjaniy.ecommerce_order_service.model.dto.PaymentInfoDto
import com.anjaniy.ecommerce_order_service.model.enums.OrderStatus
import com.anjaniy.ecommerce_order_service.model.enums.PaymentMethod
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

class OrderServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private OrderService orderService


    @Sql(statements = [
            "INSERT INTO orders (id, user_id, product_id, quantity, status, created_at, total_amount) VALUES (1001, 1, 10, 1, 'SUCCESS', CURRENT_TIMESTAMP, 499.99)",
            "INSERT INTO orders (id, user_id, product_id, quantity, status, created_at, total_amount) VALUES (1002, 2, 9, 2, 'FAILED', CURRENT_TIMESTAMP, 1999.98)",
    ])
    @Sql(statements = [
            "DELETE FROM orders WHERE id IN (1001, 1002)",
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'Should get orders' () {
        given:
        when:
        def orders = orderService.getOrders()
        then:
        assert orders.size() == 2

        assert orders.get(0).id == 1001
        assert orders.get(0).status.toString() == 'SUCCESS'
        assert orders.get(0).user == 'Amit Sharma'
        assert orders.get(0).productName == 'Wireless Charger'
        assert orders.get(0).quantity == 1
        assert orders.get(0).totalAmount == 499.99

        assert orders.get(1).id == 1002
        assert orders.get(1).status.toString() == 'FAILED'
        assert orders.get(1).user == 'Neha Verma'
        assert orders.get(1).productName == 'Portable Monitor'
        assert orders.get(1).quantity == 2
        assert orders.get(1).totalAmount == 1999.98
    }

    @Sql(statements = [
            "INSERT INTO orders (id, user_id, product_id, quantity, status, created_at, total_amount) VALUES (2001, 1, 10, 1, 'SUCCESS', CURRENT_TIMESTAMP, 499.99)",
    ])
    @Sql(statements = [
            "DELETE FROM orders WHERE id = 2001",
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'Should get order with ID' () {
        given:
        def orderId = 2001
        when:
        def order = orderService.getOrder(orderId)
        then:
        assert order.user == 'Amit Sharma'
        assert order.productName == 'Wireless Charger'
        assert order.status.toString() == 'SUCCESS'
        assert order.quantity == 1
        assert order.totalAmount == 499.99
    }

    def 'Should place an order [Both success + fail scenario]' () {
        given:
        def paymentInfoDto = new PaymentInfoDto(PaymentMethod.CREDIT_CARD, "4111111111111111", "123", "12/26", null)
        def request = OrderRequest.builder().userId(1).productId(1).quantity(2).paymentInfoDto(paymentInfoDto).build()
        when:
        def response = orderService.placeOrder(request)
        then:
        assert response.orderId == 1
        assert response.status in [OrderStatus.SUCCESS.toString(), OrderStatus.CANCELLED.toString()]
        assert response.message in ['Order placed successfully!', 'Order failed due to payment error!']
    }

    def 'Should throw ResourceNotFoundException when user does not exist' () {
        given:
        def request = OrderRequest.builder().userId(1000).productId(1).quantity(2).paymentInfoDto(null).build()
        when:
        orderService.placeOrder(request)
        then:
        def e = thrown(ResourceNotFoundException)
        e.message == "User not found!"
    }

    def 'Should throw ResourceNotFoundException when product does not exist' () {
        given:
        def request = OrderRequest.builder().userId(3).productId(1000).quantity(2).paymentInfoDto(null).build()
        when:
        orderService.placeOrder(request)
        then:
        def e = thrown(ResourceNotFoundException)
        e.message == "Product not found!"
    }

    def 'Should throw ConflictException when stock is insufficient' () {
        given:
        def request = OrderRequest.builder().userId(1).productId(8).quantity(20).paymentInfoDto(null).build()
        when:
        orderService.placeOrder(request)
        then:
        def e = thrown(ConflictException)
        e.message == "Insufficient stock!"
    }

    @Sql(statements = [
            "INSERT INTO orders (id, user_id, product_id, quantity, status, created_at, total_amount) VALUES (2002, 2, 6, 5, 'SUCCESS', CURRENT_TIMESTAMP, 499.99)",
    ])
    @Sql(statements = [
            "DELETE FROM orders WHERE id = 2002",
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'Should cancel an order' () {
        given:
        def orderId = 2002
        when:
        def response = orderService.cancelOrder(orderId)
        then:
        assert response.orderId == 2002
        assert response.status == OrderStatus.CANCELLED.toString()
        assert response.message == 'Order cancelled and stock restored!'
    }

    def 'Should throw ResourceNotFoundException when order not found' () {
        given:
        def orderId = 9999
        when:
        orderService.cancelOrder(orderId)
        then:
        def e = thrown(ResourceNotFoundException)
        e.message == "Order not found!"
    }

    @Sql(statements = [
            "INSERT INTO orders (id, user_id, product_id, quantity, status, created_at, total_amount) VALUES (2003, 3, 9, 7, 'CANCELLED', CURRENT_TIMESTAMP, 499.99)",
    ])
    @Sql(statements = [
            "DELETE FROM orders WHERE id = 2003",
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'Should throw ConflictException when order is already cancelled' () {
        given:
        def orderId = 2003
        when:
        def response = orderService.cancelOrder(orderId)
        then:
        def e = thrown(ConflictException)
        e.message == "Order is already cancelled!"
    }

    @Sql(statements = [
            "INSERT INTO orders (id, user_id, product_id, quantity, status, created_at, total_amount) VALUES (2004, 4, 5, 5, 'FAILED', CURRENT_TIMESTAMP, 499.99)",
    ])
    @Sql(statements = [
            "DELETE FROM orders WHERE id = 2004",
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'Should throw ConflictException when order is failed' () {
        given:
        def orderId = 2004
        when:
        def response = orderService.cancelOrder(orderId)
        then:
        def e = thrown(ConflictException)
        e.message == "Cannot cancel a failed order!"
    }

}
