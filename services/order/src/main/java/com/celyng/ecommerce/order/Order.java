package com.celyng.ecommerce.order;


import java.math.BigDecimal;

public class Order {
    private Integer id;
    private String reference;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
}
