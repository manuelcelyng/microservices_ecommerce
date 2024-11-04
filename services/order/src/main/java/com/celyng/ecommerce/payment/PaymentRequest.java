package com.celyng.ecommerce.payment;

import com.celyng.ecommerce.customer.CustomerResponse;
import com.celyng.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
