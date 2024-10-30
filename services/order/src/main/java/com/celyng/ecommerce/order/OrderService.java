package com.celyng.ecommerce.order;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    public Integer createdOrder(OrderRequest request) {
        // Check the customer ---> OpenFeign


        //Purchase the product  ---> Product microservice



        // persist order



        // persist order lines


        // Payment Process


        // Send the order confirmation  ---> notification-ms  (Kafka)

        return null;
    }
}
