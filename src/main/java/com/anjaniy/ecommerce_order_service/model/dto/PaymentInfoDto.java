package com.anjaniy.ecommerce_order_service.model.dto;

import com.anjaniy.ecommerce_order_service.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInfoDto {
    private PaymentMethod type;
    private String cardNumber;
    private String expiry;
    private String cvv;
    private String upiId;
}
