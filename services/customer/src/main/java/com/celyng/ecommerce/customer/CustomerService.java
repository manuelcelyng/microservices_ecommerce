package com.celyng.ecommerce.customer;


import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    public String createCustomer(@Valid CustomerRequest request) {
        return null;
    }
}
