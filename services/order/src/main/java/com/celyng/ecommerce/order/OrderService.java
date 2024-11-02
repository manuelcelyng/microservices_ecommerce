package com.celyng.ecommerce.order;


import com.celyng.ecommerce.customer.CustomerClient;
import com.celyng.ecommerce.exception.BusinessException;
import com.celyng.ecommerce.kafka.OrderConfirmation;
import com.celyng.ecommerce.kafka.OrderProducer;
import com.celyng.ecommerce.orderline.OrderLineRequest;
import com.celyng.ecommerce.orderline.OrderLineService;
import com.celyng.ecommerce.product.ProductClient;
import com.celyng.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderMapper mapper;
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    private final OrderProducer orderProducer;


    private final OrderLineService orderLineService;

    public Integer createdOrder(OrderRequest request) {
        // Check the customer ---> OpenFeign

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provider ID"));


        //Purchase the product  ---> Product microservice (RestTemplate) -> is not the same as open feign
        var purchasedProducts =  this.productClient.purchaseProducts(request.products());

        // persist order
        var order = this.repository.save(mapper.toOrder(request));
        // persist order lines
        for(PurchaseRequest purchaseRequest : request.products() ){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        //TODO Payment Process


        //Send the order confirmation  ---> notification-ms  (Kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                        )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }


    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with ID %s not found", orderId)
                        )
                );
    }
}
