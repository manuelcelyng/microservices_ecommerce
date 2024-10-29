package com.celyng.ecommerce.handler;

import com.celyng.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(
            ProductPurchaseException exp
    ) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exp.getMessage());
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(
            EntityNotFoundException exp
    ){
        return ResponseEntity
                .status(NOT_FOUND)
                .body(exp.getMessage());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(
            MethodArgumentNotValidException exp
    ) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            var fieldName = ((FieldError) error).getField();
                            var errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
