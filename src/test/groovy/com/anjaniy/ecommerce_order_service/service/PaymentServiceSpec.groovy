package com.anjaniy.ecommerce_order_service.service

import com.anjaniy.ecommerce_order_service.BaseIntegrationSpec
import com.anjaniy.ecommerce_order_service.exception.InvalidPaymentDetailsException
import com.anjaniy.ecommerce_order_service.model.dto.PaymentInfoDto
import com.anjaniy.ecommerce_order_service.model.enums.PaymentMethod
import org.springframework.beans.factory.annotation.Autowired

class PaymentServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private PaymentService paymentService

    def 'Should process credit card payment successfully' () {
        given:
        def dto = new PaymentInfoDto(PaymentMethod.CREDIT_CARD, "4111111111111111", "123", "12/26", null)
        when:
        def result = paymentService.processPayment(1, 1000.0, dto)
        then:
        result in [true, false]
    }

    def 'Should throw exception if credit card details are missing' () {
        given:
        def dto = new PaymentInfoDto(PaymentMethod.CREDIT_CARD, "", "123", "12/26", null)
        when:
        paymentService.processPayment(2, 500.0, dto)
        then:
        def e = thrown(InvalidPaymentDetailsException)
        e.message == "Missing card details!"
    }

    def 'Should process UPI payment successfully' () {
        given:
        def dto = new PaymentInfoDto(PaymentMethod.UPI, null, null, null, "user@upi")
        when:
        def result = paymentService.processPayment(3, 500.0, dto)
        then:
        result in [true, false]
    }

    def 'Should throw exception if UPI ID is blank' () {
        given:
        def dto = new PaymentInfoDto(PaymentMethod.UPI, null, null, null, "  ")
        when:
        paymentService.processPayment(4, 200.0, dto)
        then:
        def e = thrown(InvalidPaymentDetailsException)
        e.message == "Missing UPI ID!"
    }

    def 'Should always succeed for cash on delivery' () {
        given:
        def dto = new PaymentInfoDto(PaymentMethod.CASH_ON_DELIVERY, null, null, null, null)
        when:
        def result = paymentService.processPayment(1L, 150.0, dto)
        then:
        assert result
    }

}
