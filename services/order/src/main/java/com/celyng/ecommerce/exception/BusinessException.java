package com.celyng.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@EqualsAndHashCode
@Data
public class BusinessException extends RuntimeException {
    private final String msg;
}
