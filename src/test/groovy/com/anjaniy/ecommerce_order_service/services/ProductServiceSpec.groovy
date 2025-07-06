package com.anjaniy.ecommerce_order_service.services

import com.anjaniy.ecommerce_order_service.BaseIntegrationSpec
import com.anjaniy.ecommerce_order_service.service.ProductService
import org.springframework.beans.factory.annotation.Autowired

class ProductServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private ProductService productService

    def 'Get Products' () {
        given:
        when:
        def products = productService.getProducts()
        then:
        assert products.size() == 10
    }

    def 'Get Product with ID' () {
        given:
        def productId = 5
        when:
        def product = productService.getProduct(productId)
        then:
        assert product.name == 'Noise Cancelling Headphones'
        assert product.description == 'Over-ear wireless headphones'
        assert product.price == 4999.0
        assert product.stock == 15
    }
}
