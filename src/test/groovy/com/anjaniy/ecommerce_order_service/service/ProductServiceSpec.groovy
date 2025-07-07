package com.anjaniy.ecommerce_order_service.service

import com.anjaniy.ecommerce_order_service.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class ProductServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private ProductService productService

    def 'Should get products' () {
        given:
        when:
        def products = productService.getProducts()
        then:
        assert products.size() == 10
    }

    def 'Should get product with ID' () {
        given:
        def productId = 10
        when:
        def product = productService.getProduct(productId)
        then:
        assert product.name == 'Wireless Charger'
        assert product.description == 'Fast charging pad for smartphones'
        assert product.price == 699.00
        assert product.stock == 35
    }
}
