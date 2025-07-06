package com.anjaniy.ecommerce_order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OptimisticLockingFailureException extends RuntimeException {
    public OptimisticLockingFailureException(String exceptionMessage, Throwable t) {
        super(exceptionMessage, t);
    }
}
