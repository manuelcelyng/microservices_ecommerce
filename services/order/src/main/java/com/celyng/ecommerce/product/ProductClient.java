package com.celyng.ecommerce.product;

import com.celyng.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Service
@RequiredArgsConstructor
public class ProductClient{
    // CUANDO USAR REST TEMPLATE Y CUANDO USAR FEIGN?
    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(
            List<PurchaseRequest> requestBody){

        // 1. Validación de entrada
        if (requestBody == null || requestBody.isEmpty()) {
            throw new IllegalArgumentException("La lista de productos no puede estar vacía");
        }

        // 2. Configurar headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE,APPLICATION_JSON_VALUE);

        // 3. Crear entidad de solicitud
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 4. Definir tipo de respuesta
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        // 5. Realizar la solicitud HTTP
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                requestEntity,
                responseType
        );

        // 6. Validar y retornar respuesta
        if(responseEntity.getStatusCode().isError()){
            throw new BusinessException("Ah error occurred while processing the products purchase: " + responseEntity.getStatusCode());

        }
        return responseEntity.getBody();

    };
}
