package com.celyng.ecommerce.order;


import com.celyng.ecommerce.customer.CustomerClient;
import com.celyng.ecommerce.exception.BusinessException;
import com.celyng.ecommerce.orderline.OrderLineRequest;
import com.celyng.ecommerce.orderline.OrderLineService;
import com.celyng.ecommerce.product.ProductClient;
import com.celyng.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderMapper mapper;
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    private final OrderLineService orderLineService;

    public Integer createdOrder(OrderRequest request) {
        // Check the customer ---> OpenFeign

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provider ID"));


        //Purchase the product  ---> Product microservice (RestTemplate) -> is not the same as open feign
        this.productClient.purchaseProducts(request.products());

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




        // Payment Process


        // Send the order confirmation  ---> notification-ms  (Kafka)

        return null;
    }
}
