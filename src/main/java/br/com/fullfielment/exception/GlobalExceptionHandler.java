package br.com.fullfielment.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public Mono<ErrorResponse> handleCustomException(CustomException e) {
        return Mono.just(new ErrorResponse("ERROR", e.getMessage()))
                .defaultIfEmpty(new ErrorResponse("ERROR", "Unknown error"));
    }

    static class ErrorResponse {
        private String code;
        private String message;
        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    }
}
