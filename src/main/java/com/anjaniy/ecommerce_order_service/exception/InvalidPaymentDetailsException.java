package com.anjaniy.ecommerce_order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPaymentDetailsException extends RuntimeException {
    public InvalidPaymentDetailsException(String message) {
        super(message);
    }
}
