package com.celyng.ecommerce.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> erros
) {
}
