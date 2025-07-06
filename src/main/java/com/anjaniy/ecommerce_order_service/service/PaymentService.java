package com.anjaniy.ecommerce_order_service.service;

import com.anjaniy.ecommerce_order_service.exception.InvalidPaymentDetailsException;
import com.anjaniy.ecommerce_order_service.model.dto.PaymentInfoDto;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    public boolean processPayment(Long userId, Double amount, PaymentInfoDto paymentInfoDto) {
        simulateLatency();
        switch (paymentInfoDto.getType()) {
            case CREDIT_CARD, DEBIT_CARD -> {
                if (paymentInfoDto.getCardNumber().isBlank() || paymentInfoDto.getCvv().isBlank() || paymentInfoDto.getExpiry().isBlank()) {
                    throw new InvalidPaymentDetailsException("Missing card details!");
                }
                return ThreadLocalRandom.current().nextDouble() < 0.85;
            }
            case UPI -> {
                if (paymentInfoDto.getUpiId().isBlank()) {
                    throw new InvalidPaymentDetailsException("Missing UPI ID!");
                }
                return ThreadLocalRandom.current().nextDouble() < 0.85;
            }
            case CASH_ON_DELIVERY -> {
                return true;
            }
            default -> throw new InvalidPaymentDetailsException("Unsupported payment type: " + paymentInfoDto.getType() + "!");
        }
    }

    private void simulateLatency() {
        try {
            int delay = 1000 + (ThreadLocalRandom.current().nextInt(1000));
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
