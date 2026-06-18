package com.example.home_stock_api.common.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException exception
    ) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse response = new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.name(),
                errorCode.getMessage()

        );

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
){
ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
ErrorResponse response = new ErrorResponse(
        errorCode.getStatus().value(),
        errorCode.name(),
        errorCode.getMessage()
        );
    return ResponseEntity.status(errorCode.getStatus()).body(response);
}



}
