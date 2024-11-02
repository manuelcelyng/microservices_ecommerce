package com.celyng.ecommerce.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    // TODO -> tERMINAR EL CREATE pAYMENT
    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));
    }
}
