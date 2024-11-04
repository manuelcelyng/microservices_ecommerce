package com.celyng.ecommerce.payment;

import com.celyng.ecommerce.notification.NotificationProducer;
import com.celyng.ecommerce.notification.PaymentNotificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer producer;

    // TODO -> tERMINAR EL CREATE pAYMENT
    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));

        producer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );

        return payment.getId();
    }
}
