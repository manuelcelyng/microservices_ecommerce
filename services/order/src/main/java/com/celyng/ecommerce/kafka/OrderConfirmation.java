package com.celyng.ecommerce.kafka;

import com.celyng.ecommerce.customer.CustomerResponse;
import com.celyng.ecommerce.order.PaymentMethod;
import com.celyng.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
