package com.celyng.ecommerce.customer;

public record CustomerRequest(
        String id,
        String fistname,
        String lastname,
        String email,
        Address address
) {
}
