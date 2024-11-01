package com.celyng.ecommerce.exception;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final String msg;
}
