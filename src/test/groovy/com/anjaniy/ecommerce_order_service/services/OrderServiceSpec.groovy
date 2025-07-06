package com.anjaniy.ecommerce_order_service.services

import com.anjaniy.ecommerce_order_service.BaseIntegrationSpec
import com.anjaniy.ecommerce_order_service.service.OrderService
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
    def 'Get Orders' () {
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
    def 'Get Order with ID' () {
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

}
