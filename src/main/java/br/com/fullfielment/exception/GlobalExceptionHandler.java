package br.com.fullfielment.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public Mono<ErrorResponse> handleCustomException(CustomException e) {
        return Mono.just(new ErrorResponse("ERROR", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleGenericException(Exception e) {
        return Mono.just(new ErrorResponse("ERROR", "Unexpected error: " + e.getMessage()));
    }

    record ErrorResponse(String code, String message){}
}
